package co.uk.mommyheather.thermaltweaker.crafttweaker.machines;

import com.blamejared.ModTweaker;
import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseAction;

import co.uk.mommyheather.thermaltweaker.util.CompactorRecipes;
import cofh.thermalexpansion.util.managers.machine.CompactorManager.Mode;
import cofh.thermalexpansion.util.managers.machine.ThermalTweakerCompactorRecipe;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.thermaltweaker.Compactor")
@ModOnly("thermalexpansion")
@ZenRegister
public class Compactor {

    
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta) {
        ModTweaker.LATE_ADDITIONS.add(new Add(InputHelper.toStack(output), input, energy, Mode.ALL, ignoreNbt, ignoreMeta));
    }
    
    @ZenMethod
    public static void addCoinRecipe(IItemStack output, IIngredient input, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta) {
        ModTweaker.LATE_ADDITIONS.add(new Add(InputHelper.toStack(output), input, energy, Mode.COIN, ignoreNbt, ignoreMeta));
    }  
    
    @ZenMethod
    public static void addPlateRecipe(IItemStack output, IIngredient input, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta) {
        ModTweaker.LATE_ADDITIONS.add(new Add(InputHelper.toStack(output), input, energy, Mode.PLATE, ignoreNbt, ignoreMeta));
    }
    @ZenMethod
    public static void addGearRecipe(IItemStack output, IIngredient input, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta) {
        ModTweaker.LATE_ADDITIONS.add(new Add(InputHelper.toStack(output), input, energy, Mode.GEAR, ignoreNbt, ignoreMeta));
    }
    
    
    private static class Add extends BaseAction {
        
        private ItemStack output;
        private IIngredient input;
        private int energy;
        private Mode mode;
        private boolean ignoreNbt;
        private boolean ignoreMeta;
        
        public Add(ItemStack output, IIngredient input, int energy, Mode mode, boolean ignoreNbt, boolean ignoreMeta) {
            super("Compactor");
            this.output = output;
            this.input = input;
            this.energy = energy;
            this.mode = mode;
            this.ignoreNbt = ignoreNbt;
            this.ignoreMeta = ignoreMeta;
        }
        
        @Override
        public void apply() {
            for (IItemStack stack : input.getItemArray()) {
                ThermalTweakerCompactorRecipe recipe = new ThermalTweakerCompactorRecipe(InputHelper.toStack(stack), output, energy, ignoreNbt, ignoreMeta);

                CompactorRecipes.addRecipe(recipe, mode);
            }
        }
        
        @Override
        protected String getRecipeInfo() {
            return LogHelper.getStackDescription(output) + " in mode: " + mode;
        }
    }
}