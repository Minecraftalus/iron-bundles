package io.github.minecraftalus.ironbundles;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class IronBundlesResourceKeys {
    public static final ResourceKey<Item> IRON_BUNDLE_KEY = create("iron_bundle");
    public static final ResourceKey<Item> GOLD_BUNDLE_KEY = create("gold_bundle");

    public static ResourceKey<Item> create(String name) {
        return ResourceKey.create(Registries.ITEM, IronBundles.id(name));
    }
}
