<p align="center">
<img src="https://github.com/Zepalesque/Zenith/blob/1.20.4/src/main/resources/zenith.png" width="300">
</p>

<p align="center">
<img src="https://github.com/Zepalesque/Zenith/blob/1.20.4/assets/zenith_title.png" width="500">
</p>

---


### Zenith, a minecraft modding library

<br>

**Current Features:**

- System for easy condition-based datapack logic (such as only enabling a certain worldgen feature with a specified config, or disabling recipes for a certain item when a specified mod is installed)
- System for automation of creating synchronized block tints based on the biome, by using data maps
- Extendable system for creating all blocks for things such as wood types or stone types at a time, plus hooks that can be used for data generation
- System for recipes that use item stacking mechanics, similar to how bundles work (for a specific example, see Ambrosium Shard infusion recipes in the Aether: Redux)
- A variety of datagen helper methods
- Helper methods for animation easing (I will very likely add a way to use these with vanilla's keyframe system in the future)
- Helper methods for creating serializable configuration files
- A couple of minor improvements, such as an improved version of the `SharedSuggestionProvider`, inspired by the [Suggestion Provider Fix](https://www.curseforge.com/minecraft/mc-mods/suggestion-provider-fix) mod (which, as of writing this, has not yet been updated past 1.20.1)
- A variety of useful `BiomeModifier`s and a few useful `StructureModifier`s
- A few useful world generation classes


To use this library in your workspace, simply add this to the `repositories` code block of your `build.gradle` file:
```
    maven githubPackage.invoke("Zepalesque/Zenith")
```

and this to the `dependencies` code block:
```
    implementation "net.zepalesque.zenith:zenith:[MC VERSION]-[ZENITH VERSION]-neoforge"
```
