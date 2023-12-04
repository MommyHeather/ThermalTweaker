package co.uk.mommyheather.thermaltweaker.crafttweaker.machines;

import cofh.thermalexpansion.util.managers.machine.EnchanterManager;
import cofh.thermalexpansion.util.managers.machine.ThermalTweakerEnchanterRecipe;

import com.blamejared.ModTweaker;
import com.blamejared.mtlib.helpers.*;
import com.blamejared.mtlib.utils.BaseAction;

import co.uk.mommyheather.thermaltweaker.util.EnchanterRecipes;
import crafttweaker.annotations.*;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.thermaltweaker.Enchanter")
@ModOnly("thermalexpansion")
@ZenRegister
public class Enchanter {
    
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, IIngredient secondInput, int energy, int experience, boolean empowered, @Optional boolean ignorePrimaryNbt, @Optional boolean ignoreSecondaryNbt, @Optional boolean ignorePrimaryMeta, @Optional boolean ignoreSecondaryMeta) {
        ModTweaker.LATE_ADDITIONS.add(new Add(InputHelper.toStack(output), input, secondInput, energy, experience, 
            empowered ? EnchanterManager.Type.EMPOWERED : EnchanterManager.Type.STANDARD, 
            ignorePrimaryNbt, ignoreSecondaryNbt, ignorePrimaryMeta, ignoreSecondaryMeta));
    }
    
    private static class Add extends BaseAction {
        
        private ItemStack output;
        private IIngredient primaryInput, secondaryInput;
        private int energy, experience;
        private EnchanterManager.Type type;
        
        private boolean ignorePrimaryNbt;
        private boolean ignoreSecondaryNbt;
        private boolean ignorePrimaryMeta;
        private boolean ignoreSecondaryMeta;
        
        public Add(ItemStack output, IIngredient primaryInput, IIngredient secondaryInput, int energy, int experience, EnchanterManager.Type type, 
        boolean ignorePrimaryNbt, boolean ignoreSecondaryNbt, boolean ignorePrimaryMeta, boolean ignoreSecondaryMeta) {
            super("Enchanter");
            this.output = output;
            this.primaryInput = primaryInput;
            this.secondaryInput = secondaryInput;
            this.energy = energy;
            this.experience = experience;
            this.type = type;
            
            this.ignorePrimaryNbt = ignorePrimaryNbt;
            this.ignoreSecondaryNbt = ignoreSecondaryNbt;
            this.ignorePrimaryMeta = ignorePrimaryMeta;
            this.ignoreSecondaryMeta = ignoreSecondaryMeta;
        }
        
        @Override
        public void apply() {
            for (IItemStack stack1 : primaryInput.getItems()) {
                for (IItemStack stack2 : secondaryInput.getItems()) {
                    ThermalTweakerEnchanterRecipe recipe = new ThermalTweakerEnchanterRecipe(InputHelper.toStack(stack1), InputHelper.toStack(stack2), output, experience, 
                        energy, type, ignorePrimaryNbt, ignoreSecondaryNbt, ignorePrimaryMeta, ignoreSecondaryMeta);    
                        
                    EnchanterRecipes.addRecipe(recipe);
                }
            }
        }
        
        @Override
        protected String getRecipeInfo() {
            return LogHelper.getStackDescription(primaryInput) + " and " + LogHelper.getStackDescription(secondaryInput);
        }
    }
}