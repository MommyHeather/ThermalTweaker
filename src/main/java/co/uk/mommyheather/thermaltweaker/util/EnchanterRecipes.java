package co.uk.mommyheather.thermaltweaker.util;

import java.util.ArrayList;

import cofh.thermalexpansion.util.managers.machine.ThermalTweakerEnchanterRecipe;
import cofh.thermalexpansion.util.managers.machine.EnchanterManager.EnchanterRecipe;
import net.minecraft.item.ItemStack;
public class EnchanterRecipes {
    
    private static ArrayList<ThermalTweakerEnchanterRecipe> recipes = new ArrayList<>();
    
    public static EnchanterRecipe getRecipe(EnchanterRecipe recipe, ItemStack primary, ItemStack secondary) {
        
        if (recipe == null) {
            //don't try to match if thermal already succeeded
            for (ThermalTweakerEnchanterRecipe thermalTweakerEnchanterRecipe : recipes) {
                if (thermalTweakerEnchanterRecipe.matches(primary, secondary)) {
                    recipe = thermalTweakerEnchanterRecipe;
                    break;
                }
            }
        }
        
        return recipe;
    }
    
    
    public static EnchanterRecipe[] getRecipeList(EnchanterRecipe[] in) {
        
        int i = 0;
        
        EnchanterRecipe[] ret = new EnchanterRecipe[in.length + recipes.size()];
        
        for (EnchanterRecipe EnchanterRecipe : in) {
            ret[i] = EnchanterRecipe;
            i++;
        }
        
        for (ThermalTweakerEnchanterRecipe recipe : recipes) {
            ret[i] = recipe;
            i++;
        }
        
        return ret;
    }
    
    public static boolean isItemValid(boolean check, ItemStack stack) {
        
        if (check) return true;
        
        for (ThermalTweakerEnchanterRecipe recipe : recipes) {
            if (recipe.itemMatches(stack)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static void addRecipe(ThermalTweakerEnchanterRecipe recipe) {
        recipes.add(recipe);

    }
    
    
    
}
