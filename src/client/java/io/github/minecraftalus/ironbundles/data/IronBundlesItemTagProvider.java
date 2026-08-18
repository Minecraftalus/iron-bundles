package io.github.minecraftalus.ironbundles.data;

import java.util.concurrent.CompletableFuture;

import io.github.minecraftalus.ironbundles.IronBundlesTags;
import io.github.minecraftalus.ironbundles.item.IronBundlesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class IronBundlesItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public IronBundlesItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(IronBundlesTags.COPPER_BUNDLES)
            .addAll(IronBundlesItems.COPPER_RESOURCE_KEYS);
        getOrCreateTagBuilder(IronBundlesTags.IRON_BUNDLES)
            .addAll(IronBundlesItems.IRON_RESOURCE_KEYS);
        getOrCreateTagBuilder(IronBundlesTags.GOLD_BUNDLES)
            .addAll(IronBundlesItems.GOLD_RESOURCE_KEYS);
        getOrCreateTagBuilder(IronBundlesTags.EMERALD_BUNDLES)
            .addAll(IronBundlesItems.EMERALD_RESOURCE_KEYS);
        getOrCreateTagBuilder(IronBundlesTags.DIAMOND_BUNDLES)
            .addAll(IronBundlesItems.DIAMOND_RESOURCE_KEYS);
        getOrCreateTagBuilder(IronBundlesTags.NETHERITE_BUNDLES)
            .addAll(IronBundlesItems.NETHERITE_RESOURCE_KEYS);

        getOrCreateTagBuilder(IronBundlesTags.IRON_BUNDLES_MOD_BUNDLES)
            .addAll(IronBundlesItems.COPPER_RESOURCE_KEYS)
            .addAll(IronBundlesItems.IRON_RESOURCE_KEYS)
            .addAll(IronBundlesItems.GOLD_RESOURCE_KEYS)
            .addAll(IronBundlesItems.EMERALD_RESOURCE_KEYS)
            .addAll(IronBundlesItems.DIAMOND_RESOURCE_KEYS)
            .addAll(IronBundlesItems.NETHERITE_RESOURCE_KEYS);

        getOrCreateTagBuilder(ItemTags.BUNDLES)
            .addTag(IronBundlesTags.IRON_BUNDLES_MOD_BUNDLES);

        getOrCreateTagBuilder(IronBundlesTags.VANILLA_BUNDLES)
            .add(Items.BUNDLE)
            .add(Items.WHITE_BUNDLE)
            .add(Items.ORANGE_BUNDLE)
            .add(Items.MAGENTA_BUNDLE)
            .add(Items.LIGHT_BLUE_BUNDLE)
            .add(Items.YELLOW_BUNDLE)
            .add(Items.LIME_BUNDLE)
            .add(Items.PINK_BUNDLE)
            .add(Items.GRAY_BUNDLE)
            .add(Items.LIGHT_GRAY_BUNDLE)
            .add(Items.CYAN_BUNDLE)
            .add(Items.PURPLE_BUNDLE)
            .add(Items.BLUE_BUNDLE)
            .add(Items.BROWN_BUNDLE)
            .add(Items.GREEN_BUNDLE)
            .add(Items.RED_BUNDLE)
            .add(Items.BLACK_BUNDLE);
    }

    @Override
    public @NotNull String getName() {
        return "IronBundlesItemTagProvider";
    }
}
