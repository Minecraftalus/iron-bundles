package io.github.minecraftalus.ironbundles;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class IronBundlesTags {
    public static final TagKey<Item> IRON_BUNDLES = TagKey.create(Registries.ITEM, IronBundles.id("iron_bundles"));
    public static final TagKey<Item> LEATHER_BUNDLES = TagKey.create(Registries.ITEM, IronBundles.id("leather_bundles"));
}
