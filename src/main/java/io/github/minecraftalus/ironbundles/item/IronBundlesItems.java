package io.github.minecraftalus.ironbundles.item;

import io.github.minecraftalus.ironbundles.IronBundleListUtils;
import io.github.minecraftalus.ironbundles.IronBundles;
import io.github.minecraftalus.ironbundles.IronBundlesComponents;
import io.github.minecraftalus.ironbundles.component.BundleTier;
import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IronBundlesItems {
    public static final List<ResourceKey<Item>> COPPER_RESOURCE_KEYS = new ArrayList<>();
    public static final List<ResourceKey<Item>> IRON_RESOURCE_KEYS = new ArrayList<>();
    public static final List<ResourceKey<Item>> GOLD_RESOURCE_KEYS = new ArrayList<>();
    public static final List<ResourceKey<Item>> EMERALD_RESOURCE_KEYS = new ArrayList<>();
    public static final List<ResourceKey<Item>> DIAMOND_RESOURCE_KEYS = new ArrayList<>();
    public static final List<ResourceKey<Item>> NETHERITE_RESOURCE_KEYS = new ArrayList<>();

    public static final List<Item> COPPER_BUNDLES =
        List.copyOf(generateBundleTier("copper_bundle", BundleTier.COPPER, COPPER_RESOURCE_KEYS));
    public static final List<Item> IRON_BUNDLES =
        List.copyOf(generateBundleTier("iron_bundle", BundleTier.IRON, IRON_RESOURCE_KEYS));
    public static final List<Item> GOLD_BUNDLES =
        List.copyOf(generateBundleTier("gold_bundle", BundleTier.GOLD, GOLD_RESOURCE_KEYS));
    public static final List<Item> EMERALD_BUNDLES =
        List.copyOf(generateBundleTier("emerald_bundle", BundleTier.EMERALD, EMERALD_RESOURCE_KEYS));
    public static final List<Item> DIAMOND_BUNDLES =
        List.copyOf(generateBundleTier("diamond_bundle", BundleTier.DIAMOND, DIAMOND_RESOURCE_KEYS));
    public static final List<Item> NETHERITE_BUNDLES =
        List.copyOf(generateBundleTier("netherite_bundle", BundleTier.NETHERITE, NETHERITE_RESOURCE_KEYS));

    public static List<Item> bootstrap() {
        return IRON_BUNDLES;
    }

    private static List<Item> generateBundleTier(String baseName, BundleTier tier, List<ResourceKey<Item>> resourceKeys) {
        List<Item> items = new ArrayList<>();
        items.add(registerBundleItem(baseName, "", tier, resourceKeys));
        for (Map.Entry<Item, String> dyeName : IronBundleListUtils.dyeNameMappings) {
            items.add(registerBundleItem(baseName, dyeName.getValue(), tier, resourceKeys));
        }
        return items;
    }

    private static Item registerBundleItem(String baseName, String color, BundleTier tier, List<ResourceKey<Item>> resourceKeys) {
        String coloredName = color +
            (color.isEmpty() ? "" : "_")
            + baseName;

        ResourceKey<Item> key = createKey(coloredName);
        resourceKeys.add(key);
        return Items.registerItem(
            key,
            (properties) -> new IronBundleItem(
                IronBundles.id(coloredName + "_open_front"),
                ResourceLocation.withDefaultNamespace(color + "_bundle_open_back"),
                tier.getWeight(),
                properties
                    .stacksTo(1)
                    .component(IronBundlesComponents.IRON_BUNDLES_CONTENTS, IronBundlesContents.empty(tier.getWeight()))));
    }

    public static ResourceKey<Item> createKey(String name) {
        return ResourceKey.create(Registries.ITEM, IronBundles.id(name));
    }

    public static List<Item> getAllBundleItems() {
        List<Item> all = new ArrayList<>();
        all.addAll(COPPER_BUNDLES);
        all.addAll(IRON_BUNDLES);
        all.addAll(GOLD_BUNDLES);
        all.addAll(EMERALD_BUNDLES);
        all.addAll(DIAMOND_BUNDLES);
        all.addAll(NETHERITE_BUNDLES);
        return all;
    }
}
