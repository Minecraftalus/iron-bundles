package io.github.minecraftalus.ironbundles;

import io.github.minecraftalus.ironbundles.item.IronBundlesItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IronBundles implements ModInitializer {
    public static final String MOD_ID = "iron-bundles";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        IronBundlesComponents.bootstrap();
        IronBundlesItems.bootstrap();
        IronBundlesRecipes.bootstrap();

        LOGGER.info("Hello Fabric world!");
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
