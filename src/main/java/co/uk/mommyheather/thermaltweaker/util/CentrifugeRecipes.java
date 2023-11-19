package co.uk.mommyheather.thermaltweaker.util;

import java.util.ArrayList;

import cofh.thermalexpansion.util.managers.machine.ThermalTweakerCentrifugeRecipe;
import cofh.thermalexpansion.util.managers.machine.CentrifugeManager.CentrifugeRecipe;
import net.minecraft.item.ItemStack;

public class CentrifugeRecipes {
    
    private static ArrayList<ThermalTweakerCentrifugeRecipe> recipes = new ArrayList<>();
    
    public static CentrifugeRecipe getRecipe(CentrifugeRecipe recipe, ItemStack primary) {
        
        if (recipe == null) {
            //don't try to match if thermal already succeeded
            for (ThermalTweakerCentrifugeRecipe thermalTweakerCentrifugeRecipe : recipes) {
                if (thermalTweakerCentrifugeRecipe.matches(primary)) {
                    recipe = thermalTweakerCentrifugeRecipe;
                    break;
                }
            }
        }
        
        return recipe;
    }
    
    
    public static CentrifugeRecipe[] getRecipeList(CentrifugeRecipe[] in) {
        
        int i = 0;
        
        CentrifugeRecipe[] ret = new CentrifugeRecipe[in.length + recipes.size()];
        
        for (CentrifugeRecipe centrifugeRecipe : in) {
            ret[i] = centrifugeRecipe;
            i++;
        }
        
        for (ThermalTweakerCentrifugeRecipe recipe : recipes) {
            ret[i] = recipe;
            i++;
        }
        
        return ret;
    }
    
    public static void addRecipe(ThermalTweakerCentrifugeRecipe recipe) {
        recipes.add(recipe);
    }
    
    
    
}
