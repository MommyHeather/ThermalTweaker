package co.uk.mommyheather.thermaltweaker.util;

import java.util.ArrayList;

import cofh.thermalexpansion.util.managers.machine.ThermalTweakerCompactorRecipe;
import cofh.thermalexpansion.util.managers.machine.CompactorManager.CompactorRecipe;
import cofh.thermalexpansion.util.managers.machine.CompactorManager.Mode;
import net.minecraft.item.ItemStack;

public class CompactorRecipes {
    
    private static ArrayList<ThermalTweakerCompactorRecipe> recipesAll = new ArrayList<>();
    private static ArrayList<ThermalTweakerCompactorRecipe> recipesPlate = new ArrayList<>();
    private static ArrayList<ThermalTweakerCompactorRecipe> recipesCoin = new ArrayList<>();
    private static ArrayList<ThermalTweakerCompactorRecipe> recipesGear = new ArrayList<>();
    
    public static CompactorRecipe getRecipe(CompactorRecipe recipe, ItemStack primary, Mode mode) {
        
        if (recipe == null) {
            //don't try to match if thermal already succeeded
            ArrayList<ThermalTweakerCompactorRecipe> recipes;
            switch (mode) {
                case PLATE: {
                    recipes = recipesPlate;
                    break;
                }
                case COIN: {
                    recipes = recipesCoin;
                    break;
                }
                case GEAR: {
                    recipes = recipesGear;
                    break;
                }
                default: {
                    recipes = recipesAll;
                    break;
                }
                
            }
            for (ThermalTweakerCompactorRecipe thermalTweakerCompactorRecipe : recipes) {
                if (thermalTweakerCompactorRecipe.matches(primary)) {
                    recipe = thermalTweakerCompactorRecipe;
                    break;
                }
            }
        }
        
        return recipe;
    }
    
    
    public static CompactorRecipe[] getRecipeList(CompactorRecipe[] in, Mode mode) {

        ArrayList<ThermalTweakerCompactorRecipe> recipes;
        switch (mode) {
            case PLATE: {
                recipes = recipesPlate;
                break;
            }
            case COIN: {
                recipes = recipesCoin;
                break;
            }
            case GEAR: {
                recipes = recipesGear;
                break;
            }
            default: {
                recipes = recipesAll;
                break;
            }
            
        }
        
        int i = 0;
        
        CompactorRecipe[] ret = new CompactorRecipe[in.length + recipes.size()];
        
        for (CompactorRecipe compactorRecipe : in) {
            ret[i] = compactorRecipe;
            i++;
        }
        
        for (ThermalTweakerCompactorRecipe recipe : recipes) {
            ret[i] = recipe;
            i++;
        }
        
        return ret;
    }

    public static boolean isItemValid(boolean check, ItemStack stack) {

        if (check) return true;
        
        for (ThermalTweakerCompactorRecipe recipe : recipesAll) {
            if (recipe.matches(stack)) {
                return true;
            }
        }
        for (ThermalTweakerCompactorRecipe recipe : recipesCoin) {
            if (recipe.matches(stack)) {
                return true;
            }
        }
        for (ThermalTweakerCompactorRecipe recipe : recipesGear) {
            if (recipe.matches(stack)) {
                return true;
            }
        }
        for (ThermalTweakerCompactorRecipe recipe : recipesPlate) {
            if (recipe.matches(stack)) {
                return true;
            }
        }

        return false;
    }
    
    public static void addRecipe(ThermalTweakerCompactorRecipe recipe, Mode mode) {
        switch (mode) {
            case PLATE: {
                recipesPlate.add(recipe);
                return;
            }
            case COIN: {
                recipesCoin.add(recipe);
                return;
            }
            case GEAR: {
                recipesGear.add(recipe);
                return;
            }
            default: {
                recipesAll.add(recipe);
                return;
            }
            
        }
    }
    
    
    
}
