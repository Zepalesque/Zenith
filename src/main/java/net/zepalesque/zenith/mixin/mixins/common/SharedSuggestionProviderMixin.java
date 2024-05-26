package net.zepalesque.zenith.mixin.mixins.common;

import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.zenith.config.ZConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.Consumer;
import java.util.function.Function;

// Based on the idea of https://github.com/Harleyoc1/SuggestionProviderFix/blob/1.20/src/main/java/com/harleyoconnor/suggestionproviderfix/mixin/SharedSuggestionProviderMixin.java
@Mixin(SharedSuggestionProvider.class)
public interface SharedSuggestionProviderMixin {

    /**
     * @author Zepalesque
     * @reason Because typing out the modid over and over drives you near to the brink of insanity, and I know this from experience.
     * @param <T> The type of the object
     * @param resources The resources avaliable to be displayed
     * @param input The inputted string the player has typed
     * @param function Converts an object of type {@code T} to a resource location
     * @param consumer Accepts values that match
     */
    @Overwrite
    static <T> void filterResources(Iterable<T> resources, String input, Function<T, ResourceLocation> function, Consumer<T> consumer) {
        boolean hasColon = input.indexOf(':') > -1;

        for(T t : resources) {
            ResourceLocation resourcelocation = function.apply(t);
            // If the input contains a colon, match exactly to the location as this implies a namespace
            if (hasColon) {
                String s = resourcelocation.toString();
                if (SharedSuggestionProvider.matchesSubStr(input, s)) {
                    consumer.accept(t);
                }
            // Otherwise, check if the string contains the substring
            } else if (SharedSuggestionProvider.matchesSubStr(input, resourcelocation.getNamespace())
                    ||
                 // resourcelocation.getNamespace().equals("minecraft") &&
                    (resourcelocation.getNamespace().equals("minecraft") || ZConfig.COMMON.allow_nonminecraft_autocomplete.get()) &&
                    SharedSuggestionProvider.matchesSubStr(input, resourcelocation.getPath())) {
                consumer.accept(t);
            // Zenith feature, allows for any string containing the input to result. Disabled by default.
            } else if (ZConfig.COMMON.search_as_containing.get() && resourcelocation.getPath().contains(input)) {
                consumer.accept(t);
            }

        }
    }

}