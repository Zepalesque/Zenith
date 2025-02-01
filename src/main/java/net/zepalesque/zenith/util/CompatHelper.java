package net.zepalesque.zenith.util;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;

public class CompatHelper {

    /**
     * Traditional check for whether a mod is loaded or not
     * @param modid The mod's modid
     * @return Whether the mod is loaded
     */
    public static boolean loaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    /**
     * Traditional check for whether a mod is loaded or not
     * @param modid The mod's id
     * @return Whether the mod is loaded
     */
    public static boolean compat(String modid) {
        return ModList.get() == null ? exists(modid) : loaded(modid);
    }

    /**
     * Early-loading check for whether a mod is installed
     * @param modid The mod's modid
     * @return Whether the mod file for the associated modid exists
     */
    public static boolean exists(String modid) {
        return LoadingModList.get().getModFileById(modid) != null;
    }
}
