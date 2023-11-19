package co.uk.mommyheather.thermaltweaker.machines;

import com.blamejared.ModTweaker;
import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseAction;

import co.uk.mommyheather.thermaltweaker.util.InductionSmelterRecipes;
import cofh.thermalexpansion.util.managers.machine.ThermalTweakerSmelterRecipe;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.thermaltweaker.InductionSmelter")
@ModOnly("thermalexpansion")
@ZenRegister
public class InductionSmelter {
    
    @ZenMethod
    public static void addRecipe(IItemStack primaryOutput, IIngredient primaryInput, IIngredient secondaryInput, int energy, @Optional boolean ignorePrimaryNbt, @Optional boolean ignoreSecondaryNbt, @Optional boolean ignorePrimaryMeta, @Optional boolean ignoreSecondaryMeta) {
        
        ModTweaker.LATE_ADDITIONS.add(new Add(InputHelper.toStack(primaryOutput), primaryInput, secondaryInput, energy, ItemStack.EMPTY, 
        0, ignorePrimaryNbt, ignoreSecondaryNbt, ignorePrimaryMeta, ignoreSecondaryMeta));
        
    }
    
    @ZenMethod
    public static void addRecipeWithSecondaryOutput(IItemStack primaryOutput, IIngredient primaryInput, IIngredient secondaryInput, int energy, IItemStack secondaryOutput, int secondaryChance, @Optional boolean ignorePrimaryNbt, @Optional boolean ignoreSecondaryNbt, @Optional boolean ignorePrimaryMeta, @Optional boolean ignoreSecondaryMeta) {
        
        ModTweaker.LATE_ADDITIONS.add(new Add(InputHelper.toStack(primaryOutput), primaryInput, secondaryInput, energy, InputHelper.toStack(secondaryOutput), 
        secondaryChance, ignorePrimaryNbt, ignoreSecondaryNbt, ignorePrimaryMeta, ignoreSecondaryMeta));
        
    }
    
    private static class Add extends BaseAction {
        
        private ItemStack primaryOutput, secondaryOutput;
        private IIngredient primaryInput, secondaryInput;
        private int energy, secondaryChance;
        private boolean ignorePrimaryNbt;
        private boolean ignoreSecondaryNbt;
        private boolean ignorePrimaryMeta;
        private boolean ignoreSecondaryMeta;
        
        public Add(ItemStack primaryOutput, IIngredient primaryInput, IIngredient secondaryInput, int energy, ItemStack secondaryOutput, int secondaryChance,
        boolean ignorePrimaryNbt, boolean ignoreSecondaryNbt, boolean ignorePrimaryMeta, boolean ignoreSecondaryMeta) {
            super("InductionSmelter");
            this.primaryOutput = primaryOutput;
            this.primaryInput = primaryInput;
            this.secondaryInput = secondaryInput;
            this.secondaryOutput = secondaryOutput;
            this.energy = energy;
            this.secondaryChance = secondaryChance;
            if(!secondaryOutput.isEmpty() && secondaryChance <= 0) {
                this.secondaryChance = 100;
            }
            
            this.ignorePrimaryNbt = ignorePrimaryNbt;
            this.ignoreSecondaryNbt = ignoreSecondaryNbt;
            this.ignorePrimaryMeta = ignorePrimaryMeta;
            this.ignoreSecondaryMeta = ignoreSecondaryMeta;
            
        }
        
        @Override
        public void apply() {
            for (IItemStack stack1 : primaryInput.getItems()) {
                for (IItemStack stack2 : secondaryInput.getItems()) {
                    
                    ThermalTweakerSmelterRecipe recipe = new ThermalTweakerSmelterRecipe(InputHelper.toStack(stack1), InputHelper.toStack(stack2), primaryOutput, secondaryOutput, secondaryChance, energy, 
                    ignorePrimaryNbt, ignoreSecondaryNbt, ignorePrimaryMeta, ignoreSecondaryMeta);
                    
                    InductionSmelterRecipes.addRecipe(recipe);
                    
                }
            }
        }
        
        @Override
        protected String getRecipeInfo() {
            return LogHelper.getStackDescription(primaryOutput);
        }
    }
    
}
