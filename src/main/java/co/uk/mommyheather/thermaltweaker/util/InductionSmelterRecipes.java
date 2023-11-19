package co.uk.mommyheather.thermaltweaker.util;

import java.util.ArrayList;

import cofh.thermalexpansion.util.managers.machine.SmelterManager.SmelterRecipe;
import cofh.thermalexpansion.util.managers.machine.ThermalTweakerSmelterRecipe;
import net.minecraft.item.ItemStack;

public class InductionSmelterRecipes {
    
    private static ArrayList<ThermalTweakerSmelterRecipe> recipes = new ArrayList<>();
    
    public static SmelterRecipe getRecipe(SmelterRecipe recipe, ItemStack primary, ItemStack secondary) {
        
        if (recipe == null) {
            //don't try to match if thermal already succeeded
            for (ThermalTweakerSmelterRecipe thermalTweakerSmelterRecipe : recipes) {
                if (thermalTweakerSmelterRecipe.matches(primary, secondary)) {
                    recipe = thermalTweakerSmelterRecipe;
                    break;
                }
            }
        }
        
        return recipe;
    }
    
    
    public static SmelterRecipe[] getRecipeList(SmelterRecipe[] in) {
        
        int i = 0;
        
        SmelterRecipe[] ret = new SmelterRecipe[in.length + recipes.size()];
        
        for (SmelterRecipe smelterRecipe : in) {
            ret[i] = smelterRecipe;
            i++;
        }
        
        for (ThermalTweakerSmelterRecipe recipe : recipes) {
            ret[i] = recipe;
            i++;
        }
        
        return ret;
    }
    
    public static boolean isItemValid(boolean check, ItemStack item) {
        if (check) return true; //don't perform any additional checks if the machine was already going to accept the item
        
        for (ThermalTweakerSmelterRecipe recipe : recipes) {
            if (recipe.itemMatches(item)) return true;
        }
        return false;
    }
    
    public static void addRecipe(ThermalTweakerSmelterRecipe recipe) {
        recipes.add(recipe);
    }
    
    
    
}
