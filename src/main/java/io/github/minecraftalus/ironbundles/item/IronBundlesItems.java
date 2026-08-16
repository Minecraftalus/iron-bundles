package io.github.minecraftalus.ironbundles.item;

import static io.github.minecraftalus.ironbundles.IronBundlesResourceKeys.*;

import io.github.minecraftalus.ironbundles.IronBundlesComponents;
import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class IronBundlesItems {

    public static final Item IRON_BUNDLE = Items.registerItem(
        IRON_BUNDLE_KEY,
        (properties) -> new IronBundleItem(
            ResourceLocation.withDefaultNamespace("red_bundle_open_front"),
            ResourceLocation.withDefaultNamespace("red_bundle_open_back"),
            properties
                .stacksTo(1)
                .component(IronBundlesComponents.IRON_BUNDLES_CONTENTS, IronBundlesContents.EMPTY)));

    public static Item bootstrap() {
        return IRON_BUNDLE;
    }
}
