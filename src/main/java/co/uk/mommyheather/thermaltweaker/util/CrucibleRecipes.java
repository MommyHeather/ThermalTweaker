package co.uk.mommyheather.thermaltweaker.util;

import java.util.ArrayList;

import cofh.thermalexpansion.util.managers.machine.ThermalTweakerCrucibleRecipe;
import cofh.thermalexpansion.util.managers.machine.CrucibleManager.CrucibleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class CrucibleRecipes {
    
    private static ArrayList<ThermalTweakerCrucibleRecipe> recipes = new ArrayList<>();
    private static ArrayList<ThermalTweakerCrucibleRecipe> lava = new ArrayList<>();
    
    public static CrucibleRecipe getRecipe(CrucibleRecipe recipe, ItemStack primary) {
        System.out.println(recipe);
        
        if (recipe == null) {
            //don't try to match if thermal already succeeded
            for (ThermalTweakerCrucibleRecipe thermalTweakerCrucibleRecipe : recipes) {
                if (thermalTweakerCrucibleRecipe.matches(primary)) {
                    recipe = thermalTweakerCrucibleRecipe;
                    break;
                }
            }
        }
        
        return recipe;
    }
    
    
    public static CrucibleRecipe[] getRecipeList(CrucibleRecipe[] in) {
        
        int i = 0;
        
        CrucibleRecipe[] ret = new CrucibleRecipe[in.length + recipes.size()];
        
        for (CrucibleRecipe CrucibleRecipe : in) {
            ret[i] = CrucibleRecipe;
            i++;
        }
        
        for (ThermalTweakerCrucibleRecipe recipe : recipes) {
            ret[i] = recipe;
            i++;
        }
        
        return ret;
    }
    
    public static boolean isItemValid(boolean check, ItemStack stack) {
        
        if (check) return true;
        
        for (ThermalTweakerCrucibleRecipe recipe : recipes) {
            if (recipe.matches(stack)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean isLava(boolean check, ItemStack stack) {
        
        if (check) return true;
        
        for (ThermalTweakerCrucibleRecipe recipe : lava) {
            if (recipe.matches(stack)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static void addRecipe(ThermalTweakerCrucibleRecipe recipe) {
        recipes.add(recipe);

        if (FluidRegistry.LAVA.equals(recipe.getOutput().getFluid())) {
			lava.add(recipe);
		}
    }
    
    
    
}
