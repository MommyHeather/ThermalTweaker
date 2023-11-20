package cofh.thermalexpansion.util.managers.machine;

import cofh.thermalexpansion.util.managers.machine.CrucibleManager.CrucibleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


/*
 * The super constructor here is package private. I cannot subclass SmelterRecipe without being in the same package.
 * That's why this is in the thermalexpansion package. Fuck me.
 */

public class ThermalTweakerCrucibleRecipe extends CrucibleRecipe {

    private final boolean ignoreNbt;
    private final boolean ignoreMeta;

    public ThermalTweakerCrucibleRecipe(ItemStack input, FluidStack output, int energy, boolean ignoreNbt, boolean ignoreMeta) {

        
        super(input, output, energy);
        
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