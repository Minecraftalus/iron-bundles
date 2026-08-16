package io.github.minecraftalus.ironbundles;

import io.github.minecraftalus.ironbundles.item.IronBundleItem;
import io.github.minecraftalus.ironbundles.item.IronBundlesItems;
import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

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
