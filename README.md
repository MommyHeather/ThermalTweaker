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

`mods.thermaltweaker.Centrifuge.addRecipe([(<minecraft:diamond> * 5) % 10, <minecraft:redstone> % 50], <minecraft:diamond_pickaxe>, <liquid:lava>, 2000, true, true);`
  

</details>
