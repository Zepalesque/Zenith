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

<br>

To use this library in your workspace, you will have a few steps to do. 
First of all, enable the GitHub Packages plugin by adding this to your `settings.gradle` file (or insert it in the existing code block if one exists):

<details>
<summary>Plugin Settings</summary>

```
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
```

</details>

and this to your `build.gradle` (or again, add it to the existing code block if one exists):
<details>
<summary>Plugin Buildscript</summary>
    
```
plugins {
    id 'io.github.0ffz.github-packages' version '[1,2)'
}
```

</details>

Next, add this to the `repositories` code block of your `build.gradle` file to use the repository's package:

<details>
<summary>Repositories</summary>
    
```
maven githubPackage.invoke("Zepalesque/Zenith")
```

</details>


and then finally this to the `dependencies` code block (note that you will have to define the ${project.zenith_version} variable in your `gradle.properties` file, which should be formatted as `[MC VERSION]-[ZENITH VERSION]-[MODLOADER]`, for instance `1.20.4-1.0.48-neoforge`):
<details>
<summary>Dependencies</summary>
    
```
implementation "net.zepalesque.zenith:zenith:${project.zenith_version}"
jarJar fg.deobf("net.zepalesque.zenith:zenith:${project.zenith_version}") {
    jarJar.ranged(it, "[${project.zenith_version},)")
    jarJar.pin(it, "${project.zenith_version}")
}
```
</details>
