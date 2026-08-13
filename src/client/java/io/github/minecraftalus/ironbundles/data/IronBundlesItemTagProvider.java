package io.github.minecraftalus.ironbundles.data;

import io.github.minecraftalus.ironbundles.IronBundlesTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class IronBundlesItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public IronBundlesItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(IronBundlesTags.IRON_BUNDLES);
        getOrCreateTagBuilder(ItemTags.BUNDLES);
    }
}
