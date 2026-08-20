package io.github.minecraftalus.ironbundles.item;

import io.github.minecraftalus.ironbundles.IronBundleListUtils;
import io.github.minecraftalus.ironbundles.IronBundles;
import io.github.minecraftalus.ironbundles.IronBundlesComponents;
import io.github.minecraftalus.ironbundles.component.BundleTier;
import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

    public static final Item COPPER_UPGRADE_ITEM = Items.registerItem(createKey("copper_upgrade_item"), Item::new);
    public static final Item IRON_UPGRADE_ITEM = Items.registerItem(createKey("iron_upgrade_item"), Item::new);
    public static final Item GOLD_UPGRADE_ITEM = Items.registerItem(createKey("gold_upgrade_item"), Item::new);
    public static final Item EMERALD_UPGRADE_ITEM = Items.registerItem(createKey("emerald_upgrade_item"), Item::new);
    public static final Item DIAMOND_UPGRADE_ITEM = Items.registerItem(createKey("diamond_upgrade_item"), Item::new);
    public static final Item NETHERITE_UPGRADE_ITEM = Items.registerItem(createKey("netherite_upgrade_item"), Item::new);

    public static final ResourceKey<CreativeModeTab> IRON_BUNDLES_TAB_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), IronBundles.id("item_group"));
    public static final CreativeModeTab BUNDLES_ITEM_GROUP = FabricItemGroup.builder()
        .icon(() -> new ItemStack(IRON_BUNDLES.getFirst()))
        .title(Component.translatable("itemGroup.iron-bundles.bundles"))
        .build();

    public static List<Item> bootstrap() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, IRON_BUNDLES_TAB_KEY, BUNDLES_ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(IRON_BUNDLES_TAB_KEY).register(itemGroup -> {
            itemGroup.accept(COPPER_UPGRADE_ITEM);
            itemGroup.accept(IRON_UPGRADE_ITEM);
            itemGroup.accept(GOLD_UPGRADE_ITEM);
            itemGroup.accept(EMERALD_UPGRADE_ITEM);
            itemGroup.accept(DIAMOND_UPGRADE_ITEM);
            itemGroup.accept(NETHERITE_UPGRADE_ITEM);
            itemGroup.acceptAll(IronBundleListUtils.itemsToItemStacks(getAllBundleItems()));
        });

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
                ResourceLocation.withDefaultNamespace(color + (color.isEmpty() ? "" : "_") + "bundle_open_back"),
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

    public static List<ResourceKey<Item>> getAllBundleKeys() {
        List<ResourceKey<Item>> all = new ArrayList<>();
        all.addAll(COPPER_RESOURCE_KEYS);
        all.addAll(IRON_RESOURCE_KEYS);
        all.addAll(GOLD_RESOURCE_KEYS);
        all.addAll(EMERALD_RESOURCE_KEYS);
        all.addAll(DIAMOND_RESOURCE_KEYS);
        all.addAll(NETHERITE_RESOURCE_KEYS);
        return all;
    }
}
