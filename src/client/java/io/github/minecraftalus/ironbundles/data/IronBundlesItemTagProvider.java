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
        getOrCreateTagBuilder(IronBundlesTags.IRON_BUNDLES)
            .add(IronBundlesItems.IRON_BUNDLE)
            .add(IronBundlesItems.GOLD_BUNDLE);

        getOrCreateTagBuilder(ItemTags.BUNDLES)
            .add(IronBundlesItems.IRON_BUNDLE)
            .add(IronBundlesItems.GOLD_BUNDLE);

        getOrCreateTagBuilder(IronBundlesTags.LEATHER_BUNDLES)
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
