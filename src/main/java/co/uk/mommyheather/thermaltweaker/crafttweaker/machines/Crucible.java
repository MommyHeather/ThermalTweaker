package co.uk.mommyheather.thermaltweaker.crafttweaker.machines;

import com.blamejared.ModTweaker;
import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseAction;

import co.uk.mommyheather.thermaltweaker.util.CrucibleRecipes;
import cofh.thermalexpansion.util.managers.machine.ThermalTweakerCrucibleRecipe;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.thermaltweaker.Crucible")
@ModOnly("thermalexpansion")
@ZenRegister
public class Crucible {
    
    @ZenMethod
    public static void addRecipe(ILiquidStack output, IIngredient input, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta) {
        ModTweaker.LATE_ADDITIONS.add(new Add(InputHelper.toFluid(output), input, energy, ignoreNbt, ignoreMeta));
    }
    
    private static class Add extends BaseAction {
        
        private FluidStack output;
        private IIngredient input;
        private int energy;
        private boolean ignoreNbt;
        private boolean ignoreMeta;
        
        public Add(FluidStack output, IIngredient input, int energy, boolean ignoreNbt, boolean ignoreMeta) {
            super("Crucible");
            this.output = output;
            this.input = input;
            this.energy = energy;
            this.ignoreNbt = ignoreNbt;
            this.ignoreMeta = ignoreMeta;
        }
        
        @Override
        public void apply() {

            for (IItemStack stack : input.getItemArray()) {
                ThermalTweakerCrucibleRecipe recipe = new ThermalTweakerCrucibleRecipe(InputHelper.toStack(stack), output, energy, ignoreNbt, ignoreMeta);
                CrucibleRecipes.addRecipe(recipe);
            }
            
        }
        
        @Override
        protected String getRecipeInfo() {
            return LogHelper.getStackDescription(output);
        }
    }
    
}