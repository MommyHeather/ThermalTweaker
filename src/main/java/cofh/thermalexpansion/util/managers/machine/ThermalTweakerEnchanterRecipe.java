package cofh.thermalexpansion.util.managers.machine;

import cofh.thermalexpansion.util.managers.machine.EnchanterManager.EnchanterRecipe;
import cofh.thermalexpansion.util.managers.machine.EnchanterManager.Type;
import net.minecraft.item.ItemStack;

/*
 * The super constructor here is package private. I cannot subclass EnchanterRecipe without being in the same package.
 * That's why this is in the thermalexpansion package. Fuck me.
 */

public class ThermalTweakerEnchanterRecipe extends EnchanterRecipe {


    
    private final boolean ignorePrimaryMeta;
    private final boolean ignorePrimaryNbt;
    private final boolean ignoreSecondaryMeta;
    private final boolean ignoreSecondaryNbt;


    public ThermalTweakerEnchanterRecipe(ItemStack primaryInput, ItemStack secondaryInput, ItemStack output, int experience,
            int energy, Type type, boolean ignorePrimaryMeta, boolean ignorePrimaryNbt, boolean ignoreSecondaryMeta, boolean ignoreSecondaryNbt) {

        super(primaryInput, secondaryInput, output, experience, energy, type);

        this.ignorePrimaryMeta = ignorePrimaryMeta;
        this.ignorePrimaryNbt = ignorePrimaryNbt;
        this.ignoreSecondaryMeta = ignoreSecondaryMeta;
        this.ignoreSecondaryNbt = ignoreSecondaryNbt;
    }


    public boolean matches(ItemStack primary, ItemStack secondary) {
         
        return _matches(primary, secondary) || _matches(secondary, primary);

    }

    private boolean _matches(ItemStack primary, ItemStack secondary) {

        boolean primaryMatches = (
            primary.getItem() == primaryInput.getItem()  && 
                (primary.getMetadata() == primaryInput.getMetadata() || ignorePrimaryMeta ) &&
                (primary.getTagCompound() == null ? primaryInput.getTagCompound() == null : primaryInput.getTagCompound() != null && primary.getTagCompound().equals(primaryInput.getTagCompound())
                || ignorePrimaryNbt)
        );

        boolean secondaryMatches = (
            secondary.getItem() == secondaryInput.getItem()  && 
                (secondary.getMetadata() == secondaryInput.getMetadata() || ignoreSecondaryMeta ) &&
                (secondary.getTagCompound() == null ? secondaryInput.getTagCompound() == null : secondaryInput.getTagCompound() != null && secondary.getTagCompound().equals(secondaryInput.getTagCompound())
                || ignoreSecondaryNbt)
        );

        return primaryMatches && secondaryMatches;

    }


    public boolean itemMatches(ItemStack stack) {
        boolean primaryMatches = (
            stack.getItem() == primaryInput.getItem()  && 
                (stack.getMetadata() == primaryInput.getMetadata() || ignorePrimaryMeta ) &&
                (stack.getTagCompound() == null ? primaryInput.getTagCompound() == null : primaryInput.getTagCompound() != null && stack.getTagCompound().equals(primaryInput.getTagCompound())
                || ignorePrimaryNbt)
        );

        boolean secondaryMatches = (
            stack.getItem() == secondaryInput.getItem()  && 
                (stack.getMetadata() == secondaryInput.getMetadata() || ignoreSecondaryMeta ) &&
                (stack.getTagCompound() == null ? secondaryInput.getTagCompound() == null : secondaryInput.getTagCompound() != null && stack.getTagCompound().equals(secondaryInput.getTagCompound())
                || ignoreSecondaryNbt)
        );

        return primaryMatches || secondaryMatches;

    }

    
}