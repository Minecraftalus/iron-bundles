package io.github.minecraftalus.ironbundles.data;

import java.util.concurrent.CompletableFuture;

import io.github.minecraftalus.ironbundles.IronBundlesTags;
import io.github.minecraftalus.ironbundles.item.IronBundlesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import org.jetbrains.annotations.NotNull;

public class IronBundlesItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public IronBundlesItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(IronBundlesTags.IRON_BUNDLES)
            .add(IronBundlesItems.IRON_BUNDLE);

//        getOrCreateTagBuilder(ItemTags.BUNDLES)
//            .add(IronBundlesItems.IRON_BUNDLE);
    }

    @Override
    public @NotNull String getName() {
        return "IronBundlesItemTagProvider";
    }
}
