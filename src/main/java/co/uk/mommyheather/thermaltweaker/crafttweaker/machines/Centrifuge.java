package co.uk.mommyheather.thermaltweaker.crafttweaker.machines;

import java.util.Arrays;

import com.blamejared.ModTweaker;
import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseAction;

import co.uk.mommyheather.thermaltweaker.util.CentrifugeRecipes;
import cofh.thermalexpansion.util.managers.machine.ThermalTweakerCentrifugeRecipe;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.thermaltweaker.Centrifuge")
@ModOnly("thermalexpansion")
@ZenRegister
public class Centrifuge {
    
    @ZenMethod
    public static void addRecipe(WeightedItemStack[] outputs, IIngredient input, ILiquidStack fluid, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta) {
        IItemStack[] items = new IItemStack[outputs.length];
        Integer[] chances = new Integer[outputs.length];
        for(int i = 0; i < outputs.length; i++) {
            items[i] = outputs[i].getStack();
            chances[i] = (int) outputs[i].getPercent();
        }
        ModTweaker.LATE_ADDITIONS.add(new Add(InputHelper.toStacks(items), chances, input, energy, InputHelper.toFluid(fluid), ignoreNbt, ignoreMeta));
    }
    
    private static class Add extends BaseAction {
        
        private ItemStack[] outputs;
        private Integer[] chances;
        private IIngredient input;
        private int energy;
        private FluidStack fluid;
        private boolean ignoreNbt;
        private boolean ignoreMeta;
        
        public Add(ItemStack[] outputs, Integer[] chances, IIngredient input, int energy, FluidStack fluid, boolean ignoreNbt, boolean ignoreMeta) {
            super("Centrifuge");
            this.outputs = outputs;
            this.chances = chances;
            this.input = input;
            this.energy = energy;
            this.fluid = fluid;
            this.ignoreNbt = ignoreNbt;
            this.ignoreMeta = ignoreMeta;
        }
        
        @Override
        public void apply() {
            for (IItemStack stack : input.getItems()) {
                
                ThermalTweakerCentrifugeRecipe recipe = new ThermalTweakerCentrifugeRecipe(InputHelper.toStack(stack), Arrays.asList(outputs), Arrays.asList(chances), fluid, energy, ignoreNbt, ignoreMeta);

                CentrifugeRecipes.addRecipe(recipe);
                
            }
        }
        
        @Override
        protected String getRecipeInfo() {
            return LogHelper.getStackDescription(input);
        }
    }
    
}
