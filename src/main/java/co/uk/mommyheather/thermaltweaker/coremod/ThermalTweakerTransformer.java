package co.uk.mommyheather.thermaltweaker.coremod;

import static org.objectweb.asm.Opcodes.ASM4;

import java.util.HashMap;
import java.util.function.BiFunction;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import net.minecraft.launchwrapper.IClassTransformer;


public class ThermalTweakerTransformer implements IClassTransformer {
    
    private static final HashMap<String, BiFunction<Integer, ClassVisitor, ClassVisitor>> TRANSFORMERS = new HashMap<>();
    
    static {
        TRANSFORMERS.put("cofh.thermalexpansion.util.managers.machine.SmelterManager", SmelterManagerTransformer::new);
        TRANSFORMERS.put("cofh.thermalexpansion.util.managers.machine.CentrifugeManager", CentrifugeManagerTransformer::new);
        TRANSFORMERS.put("cofh.thermalexpansion.util.managers.machine.CompactorManager", CompactorManagerTransformer::new);
        TRANSFORMERS.put("cofh.thermalexpansion.util.managers.machine.CrucibleManager", CrucibleManagerTransformer::new);
        TRANSFORMERS.put("cofh.thermalexpansion.util.managers.machine.EnchanterManager", EnchanterManagerTransformer::new);
    }
    
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (TRANSFORMERS.containsKey(name)) {
            System.out.println("Transforming class : " + name);
            ClassReader reader = new ClassReader(bytes);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            reader.accept(TRANSFORMERS.get(name).apply(ASM4, writer), 0);
            
            return writer.toByteArray();
        }
        return bytes;
    }
    
    
    /*
    * Transformer - adjust getRecipe to call a method of my own before returning.
    * This lets us return a custom recipe if thermal failed to find one in its own list.
    * Transformer - adjust getRecipeList to call a method of my own before returning.
    * This lets us add some custom recipes to the returned list, allowing our recipes to properly show in jei.
    * Transformer - call our own isItemValid method before returning.
    * This lets the machine accept items in our custom recipe list.
    */
    
    public static class SmelterManagerTransformer extends ClassVisitor{
        
        public SmelterManagerTransformer(int api, ClassVisitor cv) {
            super(api, cv);
        }
        
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("getRecipe")) return new GetRecipeMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
            if (name.equals("getRecipeList")) return new GetRecipeListMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
            if (name.equals("isItemValid")) return new IsItemValidMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
        
        private static class GetRecipeMethodVisitor extends MethodVisitor {
            
            public GetRecipeMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method getRecipe");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.ARETURN) {
                    //Before returning, call our own method. This will consume the recipe to be returned off the stack, but return a recipe anyway.
                    
                    //Load primary and secondary inputs onto the stack.
                    //Recipe is already loaded, so the stack will now be the recipe, then primary input, then secondary input. Those are the arguments to the below function call.
                    visitVarInsn(Opcodes.ALOAD, 0);                    
                    visitVarInsn(Opcodes.ALOAD, 1);
                    
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/InductionSmelterRecipes", "getRecipe", 
                    "(Lcofh/thermalexpansion/util/managers/machine/SmelterManager$SmelterRecipe;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Lcofh/thermalexpansion/util/managers/machine/SmelterManager$SmelterRecipe;", false);
                }
                super.visitInsn(opcode);
            }
            
        }
        
        private static class GetRecipeListMethodVisitor extends MethodVisitor {
            
            
            public GetRecipeListMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method getRecipeList");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.ARETURN) {
                    //Before returning, call our own method. This will consume the array to be returned off the stack, but return an array anyway.
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/InductionSmelterRecipes", "getRecipeList", 
                    "([Lcofh/thermalexpansion/util/managers/machine/SmelterManager$SmelterRecipe;)[Lcofh/thermalexpansion/util/managers/machine/SmelterManager$SmelterRecipe;", false);
                }
                super.visitInsn(opcode);
            }
            
        }
        
        private static class IsItemValidMethodVisitor extends MethodVisitor {
            
            
            public IsItemValidMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method isItemValid");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.IRETURN) {
                    //Before returning, call our own method. This consumes the int on the stack but that's fine as we're returning one.
                    
                    visitVarInsn(Opcodes.ALOAD, 0);
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/InductionSmelterRecipes", "isItemValid", 
                    "(ZLnet/minecraft/item/ItemStack;)Z", false);
                }
                super.visitInsn(opcode);
            }
            
        }
    }
    
    
    /*
    * Transformer - adjust getRecipe to call a method of my own before returning.
    * This lets us return a custom recipe if thermal failed to find one in its own list.
    * Transformer - adjust getRecipeList to call a method of my own before returning.
    * This lets us add some custom recipes to the returned list, allowing our recipes to properly show in jei.
    */
    
    public static class CentrifugeManagerTransformer extends ClassVisitor{
        
        public CentrifugeManagerTransformer(int api, ClassVisitor cv) {
            super(api, cv);
        }
        
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("getRecipe")) return new GetRecipeMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
            if (name.equals("getRecipeList")) return new GetRecipeListMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));    
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
        
        private static class GetRecipeMethodVisitor extends MethodVisitor {
            
            public GetRecipeMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method getRecipe");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.ARETURN) {
                    //Before returning, call our own method. This will consume the recipe to be returned off the stack, but return a recipe anyway.
                    
                    //Load input onto the stack.
                    //Recipe is already loaded, so the stack will now be the recipe, then input. Those are the arguments to the below function call.
                    visitVarInsn(Opcodes.ALOAD, 0);
                    
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/CentrifugeRecipes", "getRecipe", 
                    "(Lcofh/thermalexpansion/util/managers/machine/CentrifugeManager$CentrifugeRecipe;Lnet/minecraft/item/ItemStack;)Lcofh/thermalexpansion/util/managers/machine/CentrifugeManager$CentrifugeRecipe;", false);
                }
                super.visitInsn(opcode);
            }
            
        }
        
        private static class GetRecipeListMethodVisitor extends MethodVisitor {
            
            
            public GetRecipeListMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method getRecipeList");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.ARETURN) {
                    //Before returning, call our own method. This will consume the array to be returned off the stack, but return an array anyway.
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/CentrifugeRecipes", "getRecipeList", 
                    "([Lcofh/thermalexpansion/util/managers/machine/CentrifugeManager$CentrifugeRecipe;)[Lcofh/thermalexpansion/util/managers/machine/CentrifugeManager$CentrifugeRecipe;", false);
                }
                super.visitInsn(opcode);
            }
            
        }
    }


    /*
    * Transformer - adjust getRecipe to call a method of my own before returning.
    * This lets us return a custom recipe if thermal failed to find one in its own list.
    * Transformer - adjust getRecipeList to call a method of my own before returning.
    * This lets us add some custom recipes to the returned list, allowing our recipes to properly show in jei.
    * Transformer - call our own isItemValid method before returning.
    * This lets the machine accept items in our custom recipe list.
    */
    
    public static class CompactorManagerTransformer extends ClassVisitor{
        
        public CompactorManagerTransformer(int api, ClassVisitor cv) {
            super(api, cv);
        }
        
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("getRecipe")) return new GetRecipeMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
            if (name.equals("getRecipeList")) return new GetRecipeListMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions)); 
            if (name.equals("isItemValid")) return new IsItemValidMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));    
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
        
        private static class GetRecipeMethodVisitor extends MethodVisitor {
            
            public GetRecipeMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method getRecipe");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.ARETURN) {
                    //Before returning, call our own method. This will consume the recipe to be returned off the stack, but return a recipe anyway.
                    
                    //Load input onto the stack.
                    //Recipe is already loaded, so the stack will now be the recipe, then input, then mode. Those are the arguments to the below function call.
                    visitVarInsn(Opcodes.ALOAD, 0);
                    visitVarInsn(Opcodes.ALOAD, 1);
                    
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/CompactorRecipes", "getRecipe", 
                    "(Lcofh/thermalexpansion/util/managers/machine/CompactorManager$CompactorRecipe;Lnet/minecraft/item/ItemStack;Lcofh/thermalexpansion/util/managers/machine/CompactorManager$Mode;)Lcofh/thermalexpansion/util/managers/machine/CompactorManager$CompactorRecipe;", false);
                }
                super.visitInsn(opcode);
            }
            
        }
        
        private static class GetRecipeListMethodVisitor extends MethodVisitor {
            
            
            public GetRecipeListMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method getRecipeList");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.ARETURN) {
                    //Before returning, call our own method. This will consume the array to be returned off the stack, but return an array anyway.

                    visitVarInsn(Opcodes.ALOAD, 0);
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/CompactorRecipes", "getRecipeList", 
                    "([Lcofh/thermalexpansion/util/managers/machine/CompactorManager$CompactorRecipe;Lcofh/thermalexpansion/util/managers/machine/CompactorManager$Mode;)[Lcofh/thermalexpansion/util/managers/machine/CompactorManager$CompactorRecipe;", false);
                }
                super.visitInsn(opcode);
            }
            
        }
        
        private static class IsItemValidMethodVisitor extends MethodVisitor {
            
            
            public IsItemValidMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method isItemValid");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.IRETURN) {
                    //Before returning, call our own method. This consumes the int on the stack but that's fine as we're returning one.
                    
                    visitVarInsn(Opcodes.ALOAD, 0);
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/CompactorRecipes", "isItemValid", 
                    "(ZLnet/minecraft/item/ItemStack;)Z", false);
                }
                super.visitInsn(opcode);
            }
            
        }
    }


    /*
    * Transformer - adjust getRecipe to call a method of my own before returning.
    * This lets us return a custom recipe if thermal failed to find one in its own list.
    * Transformer - adjust getRecipeList to call a method of my own before returning.
    * This lets us add some custom recipes to the returned list, allowing our recipes to properly show in jei.
    * Transformer - call our own isItemValid method before returning.
    * This lets the machine accept items in our custom recipe list.
    * Transformer - call our own isLava method before returning.
    * This lets the machine accept items in our custom recipe list when in it has the Pyroconvective Loop augment.
    */
    
    public static class CrucibleManagerTransformer extends ClassVisitor{
        
        public CrucibleManagerTransformer(int api, ClassVisitor cv) {
            super(api, cv);
        }
        
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("getRecipe")) return new GetRecipeMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
            if (name.equals("getRecipeList")) return new GetRecipeListMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions)); 
            if (name.equals("isItemValid")) return new IsItemValidMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));    
            if (name.equals("isLava")) return new IsLavaMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));    
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
        
        private static class GetRecipeMethodVisitor extends MethodVisitor {
            
            public GetRecipeMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method getRecipe");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.ARETURN) {
                    //Before returning, call our own method. This will consume the recipe to be returned off the stack, but return a recipe anyway.
                    
                    //Load input onto the stack.
                    //Recipe is already loaded, so the stack will now be the recipe, then input. Those are the arguments to the below function call.
                    visitVarInsn(Opcodes.ALOAD, 0);
                    
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/CrucibleRecipes", "getRecipe", 
                    "(Lcofh/thermalexpansion/util/managers/machine/CrucibleManager$CrucibleRecipe;Lnet/minecraft/item/ItemStack;)Lcofh/thermalexpansion/util/managers/machine/CrucibleManager$CrucibleRecipe;", false);
                }
                super.visitInsn(opcode);
            }
            
        }
        
        private static class GetRecipeListMethodVisitor extends MethodVisitor {
            
            
            public GetRecipeListMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method getRecipeList");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.ARETURN) {
                    //Before returning, call our own method. This will consume the array to be returned off the stack, but return an array anyway.

                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/CrucibleRecipes", "getRecipeList", 
                    "([Lcofh/thermalexpansion/util/managers/machine/CrucibleManager$CrucibleRecipe;)[Lcofh/thermalexpansion/util/managers/machine/CrucibleManager$CrucibleRecipe;", false);
                }
                super.visitInsn(opcode);
            }
            
        }
        
        private static class IsItemValidMethodVisitor extends MethodVisitor {
            
            
            public IsItemValidMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method isItemValid");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.IRETURN) {
                    //Before returning, call our own method. This consumes the int on the stack but that's fine as we're returning one.
                    
                    visitVarInsn(Opcodes.ALOAD, 0);
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/CrucibleRecipes", "isItemValid", 
                    "(ZLnet/minecraft/item/ItemStack;)Z", false);
                }
                super.visitInsn(opcode);
            }
            
        }
        
        private static class IsLavaMethodVisitor extends MethodVisitor {
            
            
            public IsLavaMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method isLava");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.IRETURN) {
                    //Before returning, call our own method. This consumes the int on the stack but that's fine as we're returning one.
                    
                    visitVarInsn(Opcodes.ALOAD, 0);
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/CrucibleRecipes", "isLava", 
                    "(ZLnet/minecraft/item/ItemStack;)Z", false);
                }
                super.visitInsn(opcode);
            }
            
        }
    }
    
    
    /*
    * Transformer - adjust getRecipe to call a method of my own before returning.
    * This lets us return a custom recipe if thermal failed to find one in its own list.
    * Transformer - adjust getRecipeList to call a method of my own before returning.
    * This lets us add some custom recipes to the returned list, allowing our recipes to properly show in jei.
    * Transformer - call our own isItemValid method before returning.
    * This lets the machine accept items in our custom recipe list.
    */
    
    public static class EnchanterManagerTransformer extends ClassVisitor{
        
        public EnchanterManagerTransformer(int api, ClassVisitor cv) {
            super(api, cv);
        }
        
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("getRecipe")) return new GetRecipeMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
            if (name.equals("getRecipeList")) return new GetRecipeListMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
            if (name.equals("isItemValid")) return new IsItemValidMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
        
        private static class GetRecipeMethodVisitor extends MethodVisitor {
            
            public GetRecipeMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method getRecipe");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.ARETURN) {
                    //Before returning, call our own method. This will consume the recipe to be returned off the stack, but return a recipe anyway.
                    
                    //Load primary and secondary inputs onto the stack.
                    //Recipe is already loaded, so the stack will now be the recipe, then primary input, then secondary input. Those are the arguments to the below function call.
                    visitVarInsn(Opcodes.ALOAD, 0);                    
                    visitVarInsn(Opcodes.ALOAD, 1);
                    
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/EnchanterRecipes", "getRecipe", 
                    "(Lcofh/thermalexpansion/util/managers/machine/EnchanterManager$EnchanterRecipe;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Lcofh/thermalexpansion/util/managers/machine/EnchanterManager$EnchanterRecipe;", false);
                }
                super.visitInsn(opcode);
            }
            
        }
        
        private static class GetRecipeListMethodVisitor extends MethodVisitor {
            
            
            public GetRecipeListMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method getRecipeList");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.ARETURN) {
                    //Before returning, call our own method. This will consume the array to be returned off the stack, but return an array anyway.
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/EnchanterRecipes", "getRecipeList", 
                    "([Lcofh/thermalexpansion/util/managers/machine/EnchanterManager$EnchanterRecipe;)[Lcofh/thermalexpansion/util/managers/machine/EnchanterManager$EnchanterRecipe;", false);
                }
                super.visitInsn(opcode);
            }
            
        }
        
        private static class IsItemValidMethodVisitor extends MethodVisitor {
            
            
            public IsItemValidMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
                System.out.println("Transforming method isItemValid");
                
            }
            
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.IRETURN) {
                    //Before returning, call our own method. This consumes the int on the stack but that's fine as we're returning one.
                    
                    visitVarInsn(Opcodes.ALOAD, 0);
                    visitMethodInsn(Opcodes.INVOKESTATIC, "co/uk/mommyheather/thermaltweaker/util/EnchanterRecipes", "isItemValid", 
                    "(ZLnet/minecraft/item/ItemStack;)Z", false);
                }
                super.visitInsn(opcode);
            }
            
        }
    }
    
}
