package io.github.minecraftalus.ironbundles.data;

import io.github.minecraftalus.ironbundles.item.IronBundlesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import org.jetbrains.annotations.NotNull;

public class IronBundlesModelProvider extends FabricModelProvider {
    public IronBundlesModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(IronBundlesItems.IRON_BUNDLE, ModelTemplates.FLAT_ITEM);
    }

    @Override
    public @NotNull String getName() {
        return "IronBundlesModelProvider";
    }

}
