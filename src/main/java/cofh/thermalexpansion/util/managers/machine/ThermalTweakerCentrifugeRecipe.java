package cofh.thermalexpansion.util.managers.machine;

import java.util.List;

import javax.annotation.Nullable;

import cofh.thermalexpansion.util.managers.machine.CentrifugeManager.CentrifugeRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


/*
 * The super constructor here is package private. I cannot subclass SmelterRecipe without being in the same package.
 * That's why this is in the thermalexpansion package. Fuck me.
 */

public class ThermalTweakerCentrifugeRecipe extends CentrifugeRecipe {

    private final boolean ignoreNbt;
    private final boolean ignoreMeta;

    public ThermalTweakerCentrifugeRecipe(ItemStack input, @Nullable List<ItemStack> output, @Nullable List<Integer> chance, @Nullable FluidStack fluid, int energy,
            boolean ignoreNbt, boolean ignoreMeta) {

        
        super(input, output, chance, fluid, energy);
        
        this.ignoreNbt = ignoreNbt;
        this.ignoreMeta = ignoreMeta;
    }



    public boolean matches(ItemStack primary) {

        boolean primaryMatches = (
            primary.getItem() == input.getItem()  && 
                (primary.getMetadata() == input.getMetadata() || ignoreMeta ) &&
                (primary.getTagCompound() == null ? input.getTagCompound() == null : input.getTagCompound() != null && primary.getTagCompound().equals(input.getTagCompound())
                || ignoreNbt)
        );

        return primaryMatches;

    }
    
}