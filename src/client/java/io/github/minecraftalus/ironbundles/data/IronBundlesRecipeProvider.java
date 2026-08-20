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
import net.minecraft.tags.TagKey;
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

                upgradeItemRecipe(IronBundlesItems.COPPER_UPGRADE_ITEM, Items.COPPER_INGOT, Items.STRING, this, exporter);
                upgradeItemRecipe(IronBundlesItems.IRON_UPGRADE_ITEM, Items.IRON_INGOT, IronBundlesItems.COPPER_UPGRADE_ITEM, this, exporter);
                upgradeItemRecipe(IronBundlesItems.GOLD_UPGRADE_ITEM, Items.GOLD_INGOT, IronBundlesItems.IRON_UPGRADE_ITEM, this, exporter);
                upgradeItemRecipe(IronBundlesItems.EMERALD_UPGRADE_ITEM, Items.EMERALD, IronBundlesItems.GOLD_UPGRADE_ITEM, this, exporter);
                upgradeItemRecipe(IronBundlesItems.DIAMOND_UPGRADE_ITEM, Items.DIAMOND, IronBundlesItems.EMERALD_UPGRADE_ITEM, this, exporter);
                netheriteSmithing(IronBundlesItems.DIAMOND_UPGRADE_ITEM, RecipeCategory.MISC, IronBundlesItems.NETHERITE_UPGRADE_ITEM);

                bundleColorRecipes(IronBundlesItems.COPPER_BUNDLES, IronBundlesTags.COPPER_BUNDLES, this, itemLookup, exporter);
                bundleColorRecipes(IronBundlesItems.IRON_BUNDLES, IronBundlesTags.IRON_BUNDLES, this, itemLookup, exporter);
                bundleColorRecipes(IronBundlesItems.GOLD_BUNDLES, IronBundlesTags.GOLD_BUNDLES, this, itemLookup, exporter);
                bundleColorRecipes(IronBundlesItems.EMERALD_BUNDLES, IronBundlesTags.EMERALD_BUNDLES, this, itemLookup, exporter);
                bundleColorRecipes(IronBundlesItems.DIAMOND_BUNDLES, IronBundlesTags.DIAMOND_BUNDLES, this, itemLookup, exporter);
                bundleColorRecipes(IronBundlesItems.NETHERITE_BUNDLES, IronBundlesTags.NETHERITE_BUNDLES, this, itemLookup, exporter);

                bundleUpgradeRecipes(
                    IronBundleListUtils.vanillaBundles, IronBundlesItems.COPPER_BUNDLES,
                    IronBundlesItems.COPPER_UPGRADE_ITEM, this, exporter);
                bundleUpgradeRecipes(
                    IronBundlesItems.COPPER_BUNDLES, IronBundlesItems.IRON_BUNDLES,
                    IronBundlesItems.IRON_UPGRADE_ITEM, this, exporter);
                bundleUpgradeRecipes(
                    IronBundlesItems.IRON_BUNDLES, IronBundlesItems.GOLD_BUNDLES,
                    IronBundlesItems.GOLD_UPGRADE_ITEM, this, exporter);
                bundleUpgradeRecipes(
                    IronBundlesItems.GOLD_BUNDLES, IronBundlesItems.EMERALD_BUNDLES,
                    IronBundlesItems.EMERALD_UPGRADE_ITEM, this, exporter);
                bundleUpgradeRecipes(
                    IronBundlesItems.EMERALD_BUNDLES, IronBundlesItems.DIAMOND_BUNDLES,
                    IronBundlesItems.DIAMOND_UPGRADE_ITEM, this, exporter);
                bundleUpgradeRecipes(
                    IronBundlesItems.DIAMOND_BUNDLES, IronBundlesItems.NETHERITE_BUNDLES,
                    IronBundlesItems.NETHERITE_UPGRADE_ITEM, this, exporter);

                overrideVanillaColors(this, itemLookup, exporter);
            }
        };
    }

    private void upgradeItemRecipe(Item result, Item material, Item previous, RecipeProvider provider, RecipeOutput exporter) {
      provider.shaped(RecipeCategory.MISC, result)
          .pattern(" p ")
          .pattern("psp")
          .pattern(" p ")
          .define('p', material)
          .define('s', previous)
          .unlockedBy(RecipeProvider.getHasName(material), provider.has(material))
          .unlockedBy(RecipeProvider.getHasName(previous), provider.has(previous))
          .save(exporter);
    }

    private void bundleUpgradeRecipes(List<Item> lowerTierItems, List<Item> higherTierItems, Item upgradeItem, RecipeProvider provider, RecipeOutput exporter) {
        for (int i = 0; i < lowerTierItems.size(); i++) {
            BundleUpgradeRecipeBuilder.upgrade(
                    RecipeCategory.TOOLS,
                    Ingredient.of(lowerTierItems.get(i)),
                    Ingredient.of(upgradeItem),
                    higherTierItems.get(i))
                .unlockedBy(RecipeProvider.getHasName(upgradeItem), provider.has(upgradeItem))
                .save(exporter, upgradeLocation(higherTierItems.get(i)));
        }
    }

    private void bundleColorRecipes(List<Item> tierItems, TagKey<Item> tierKey, RecipeProvider provider, HolderLookup.RegistryLookup<Item> lookup, RecipeOutput exporter) {
        List<Map.Entry<Item, Item>> dyeColors = IronBundleListUtils.dyeBundleMappings;
        for (int i = 1; i < tierItems.size(); i++) {
            TransmuteRecipeBuilder.transmute(
                    RecipeCategory.TOOLS,
                    Ingredient.of(lookup.getOrThrow(tierKey)),
                    Ingredient.of(dyeColors.get(i - 1).getKey()),
                    tierItems.get(i))
                .unlockedBy(RecipeProvider.getHasName(dyeColors.get(i - 1).getKey()), provider.has(dyeColors.get(i - 1).getKey()))
                .save(exporter, colorLocation(tierItems.get(i)));
        }
    }

    private void overrideVanillaColors(RecipeProvider provider, HolderLookup.RegistryLookup<Item> lookup, RecipeOutput exporter) {
        for (Map.Entry<Item, Item> pair : IronBundleListUtils.dyeBundleMappings) {
            TransmuteRecipeBuilder.transmute(
                    RecipeCategory.TOOLS,
                    Ingredient.of(lookup.getOrThrow(IronBundlesTags.VANILLA_BUNDLES)),
                    Ingredient.of(pair.getKey()),
                    pair.getValue())
                .unlockedBy(RecipeProvider.getHasName(pair.getKey()), provider.has(pair.getKey()))
                .save(exporter);
        }

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
