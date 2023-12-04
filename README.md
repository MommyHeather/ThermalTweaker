# ThermalTweaker

ThermalTweaker is designed to *extend* ModTweaker, not replace it.
As such, it provides no methods for things like removing recipes. It is solely for adding new recipes with support for ignoring nbt and / or metadata.


## Documentation

All methods follow similar design to those used in ModTweaker. See its [documentation](https://docs.blamejared.com/1.12/en/Mods/Modtweaker/Modtweaker) for more info.

`IIngredient` is one of the main differences here. This is unsupported in ModTweaker's Thermal Expansion integration. It can accept the following types:
- `IItemStack` (such as `<thermalexpansion:glass:0>`)
- `IOreDictEntry` (such as `<ore:blockGlassHardened>`)
- Wildcard metadata (such as `<thermalexpansion:glass:*>`

To use one optional argument, you must also pass the optional argument before it (if there are any.) For that reason, the defaults are listed below:
- Boolean : `false`

Below, each machine will list all of its additional methods, along with one example usage.


### Centrifugal Separator:

<details>
  <summary>Expand/Collapse</summary>

```addRecipe(WeightedItemStack[] outputs, IIngredient input, ILiquidStack fluid, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta)```

This adds a recipe, optionally ignoring nbt. The fluid is an output.

`mods.thermaltweaker.Centrifuge.addRecipe([(<minecraft:diamond> * 5) % 10, <minecraft:redstone> % 50], <minecraft:diamond_pickaxe>, <liquid:lava>, 2000, true, true);`
  

</details>


### Compactor:

<details>
  <summary>Expand/Collapse</summary>

```addRecipe(IItemStack output, IIngredient input, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta)```

This adds a recipe, using the `ALL` mode. **WARNING : THIS MODE IS NOT USED BY EITHER THE COMPACTOR OR THE JEI PLUGIN!** It is supported purely for parity with ModTweaker.

`mods.thermaltweaker.Compactor.addRecipe(<minecraft:sand>, <ore:blockGlassHardened>, 1500);`

---

```addCoinRecipe(IItemStack output, IIngredient input, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta```

This adds a recipe, using the `COIN` mode. This mode requires the Numismatic Press Augment.

`mods.thermaltweaker.Compactor.addCoinRecipe(<minecraft:diamond>, <minecraft:diamond_pickaxe>, 1500, true, true);`
  
---

```addPlateRecipe(IItemStack output, IIngredient input, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta```

This adds a recipe, using the `PLATE` mode. This mode does not require an augment.

`mods.thermaltweaker.Compactor.addPlateRecipe(<minecraft:diamond>, <minecraft:golden_pickaxe>, 1500, true, true);`
  
---

```addGearRecipe(IItemStack output, IIngredient input, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta```

This adds a recipe, using the `GEAR` mode. This mode requires the Gearworking Die Augment.

`mods.thermaltweaker.Compactor.addGearRecipe(<minecraft:diamond>, <minecraft:stone_pickaxe>, 1500, true, true);`
  

</details>


### Magma Crucible:

<details>
  <summary>Expand/Collapse</summary>

```addRecipe(ILiquidStack output, IIngredient input, int energy, @Optional boolean ignoreNbt, @Optional boolean ignoreMeta)```

This adds a recipe to the Magma Crucible. If the fluid output is Lava, it'll also be supported by a crucible using the Pyroconvective Loop augment.

`mods.thermaltweaker.Crucible.addRecipe(<liquid:lava>, <minecraft:stone_pickaxe>, 1500, true, true);`
  

</details>


### Arcane Ensorcellator

<details>
  <summary>Expand/Collapse</summary>

```addRecipe(IItemStack output, IIngredient input, IIngredient secondInput, int energy, int experience, boolean empowered, @Optional boolean ignorePrimaryNbt, @Optional boolean ignoreSecondaryNbt, @Optional boolean ignorePrimaryMeta, @Optional boolean ignoreSecondaryMeta)```

This adds a recipe to the Arcane Ensorcellator, using the EMPOWERED recipe type if the boolean is enabled. **WARNING : THIS MODE IS NOT USED BY EITHER THE ENCHANTER OR THE JEI PLUGIN!** It is supported purely for parity with ModTweaker. (registration of the relevant augment is disabled, but otherwise it looks fully implemented)

`mods.thermaltweaker.Enchanter.addRecipe(<minecraft:diamond_pickaxe>, <minecraft:diamond>, <minecraft:golden_pickaxe>, 12000, 3000, false, true, true, true, true);`
  

</details>
