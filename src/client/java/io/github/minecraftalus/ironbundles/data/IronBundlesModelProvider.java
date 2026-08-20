package io.github.minecraftalus.ironbundles.data;

import io.github.minecraftalus.ironbundles.item.IronBundleItem;
import io.github.minecraftalus.ironbundles.item.IronBundlesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
        itemModelGenerator.generateFlatItem(IronBundlesItems.COPPER_UPGRADE_ITEM, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(IronBundlesItems.IRON_UPGRADE_ITEM, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(IronBundlesItems.GOLD_UPGRADE_ITEM, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(IronBundlesItems.EMERALD_UPGRADE_ITEM, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(IronBundlesItems.DIAMOND_UPGRADE_ITEM, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(IronBundlesItems.NETHERITE_UPGRADE_ITEM, ModelTemplates.FLAT_ITEM);

        for (Item bundle : IronBundlesItems.getAllBundleItems()) {
            itemModelGenerator.generateFlatItem(bundle, ModelTemplates.FLAT_ITEM);

            ResourceLocation openLocation = ModelLocationUtils.getModelLocation(bundle).withSuffix("_open_front");
            ModelTemplates.FLAT_ITEM.create(openLocation, TextureMapping.layer0(openLocation), itemModelGenerator.output);
        }
    }

    @Override
    public @NotNull String getName() {
        return "IronBundlesModelProvider";
    }

}
