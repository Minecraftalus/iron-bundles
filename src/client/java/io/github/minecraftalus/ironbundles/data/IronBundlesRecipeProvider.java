package io.github.minecraftalus.ironbundles.data;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import io.github.minecraftalus.ironbundles.IronBundleListUtils;
import io.github.minecraftalus.ironbundles.IronBundlesTags;
import io.github.minecraftalus.ironbundles.item.IronBundlesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

public class IronBundlesRecipeProvider extends FabricRecipeProvider {
    public IronBundlesRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

//                TransmuteRecipeBuilder.transmute(
//                    RecipeCategory.TOOLS,
//                    Ingredient.of(IronBundlesItems.IRON_BUNDLE),
//                    Ingredient.of(Items.GOLD_INGOT),
//                    IronBundlesItems.GOLD_BUNDLE)
//                    .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
//                    .save(exporter);

                overrideVanillaColors(this, itemLookup, exporter);

//                BundleUpgradeRecipeBuilder.upgrade(
//                        RecipeCategory.TOOLS,
//                        Ingredient.of(Items.BUNDLE),
//                        Ingredient.of(Items.IRON_INGOT),
//                        IronBundlesItems.IRON_BUNDLE)
//                    .group("")
//                    .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
//                    .save(output);

            }
        };
    }

    private void overrideVanillaColors(RecipeProvider provider, HolderLookup.RegistryLookup<Item> lookup, RecipeOutput exporter) {
        for (Map.Entry<Item, Item> pair : IronBundleListUtils.dyeBundleMappings) {
            // new recipe
            TransmuteRecipeBuilder.transmute(
                    RecipeCategory.TOOLS,
                    Ingredient.of(lookup.getOrThrow(IronBundlesTags.VANILLA_BUNDLES)),
                    Ingredient.of(pair.getKey()),
                    pair.getValue())
                .unlockedBy(RecipeProvider.getHasName(pair.getKey()), provider.has(pair.getKey()))
                .save(exporter);

            // remove old recipe
//            TransmuteRecipeBuilder.transmute(
//                    RecipeCategory.TOOLS,
//                    Ingredient.of(lookup.getOrThrow(ItemTags.BUNDLES)),
//                    Ingredient.of(pair.getKey()),
//                    Items.AIR)
//                .unlockedBy(RecipeProvider.getHasName(pair.getKey()), provider.has(pair.getKey()))
//                .save(exporter);
        }

    }

    private static Map.Entry<Item, Item> getEntry(Item one, Item two) {
        return new AbstractMap.SimpleEntry<>(one, two);
    }

    private static ResourceKey<Recipe<?>> upgradeLocation(Item result) {
        return ResourceKey.create(Registries.RECIPE, RecipeBuilder.getDefaultRecipeId(result).withPrefix("upgrade/"));
    }

    private static ResourceKey<Recipe<?>> colorLocation(Item result) {
        return ResourceKey.create(Registries.RECIPE, RecipeBuilder.getDefaultRecipeId(result).withPrefix("coloring/"));
    }

    @Override
    public @NotNull String getName() {
        return "IronBundlesRecipeProvider";
    }
}
