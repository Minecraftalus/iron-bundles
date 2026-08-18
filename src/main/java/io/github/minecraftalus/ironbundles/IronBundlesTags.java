package io.github.minecraftalus.ironbundles;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class IronBundlesTags {
    public static final TagKey<Item> VANILLA_BUNDLES = TagKey.create(Registries.ITEM, IronBundles.id("vanilla_bundles"));
    public static final TagKey<Item> COPPER_BUNDLES = TagKey.create(Registries.ITEM, IronBundles.id("copper_bundles"));
    public static final TagKey<Item> IRON_BUNDLES = TagKey.create(Registries.ITEM, IronBundles.id("iron_bundles"));
    public static final TagKey<Item> GOLD_BUNDLES = TagKey.create(Registries.ITEM, IronBundles.id("gold_bundles"));
    public static final TagKey<Item> EMERALD_BUNDLES = TagKey.create(Registries.ITEM, IronBundles.id("emerald_bundles"));
    public static final TagKey<Item> DIAMOND_BUNDLES = TagKey.create(Registries.ITEM, IronBundles.id("diamond_bundles"));
    public static final TagKey<Item> NETHERITE_BUNDLES = TagKey.create(Registries.ITEM, IronBundles.id("netherite_bundles"));

    public static final TagKey<Item> IRON_BUNDLES_MOD_BUNDLES = TagKey.create(Registries.ITEM, IronBundles.id("iron_bundles_mod_bundles"));

}
