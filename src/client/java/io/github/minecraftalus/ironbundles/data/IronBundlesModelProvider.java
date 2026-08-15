package io.github.minecraftalus.ironbundles.data;

import com.mojang.blaze3d.platform.TextureUtil;
import io.github.minecraftalus.ironbundles.IronBundles;
import io.github.minecraftalus.ironbundles.IronBundlesResourceKeys;
import io.github.minecraftalus.ironbundles.R;
import io.github.minecraftalus.ironbundles.item.IronBundlesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.TextureHelper;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.resources.model.ItemModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import net.minecraft.data.models.model.ModelLocationUtils;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class IronBundlesModelProvider extends FabricModelProvider {
    public IronBundlesModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        // createBundle("iron_bundle", "item/bundle", itemModelGenerator);
        // itemModelGenerator.generateLayeredItem(IronBundles.id("item/iron_bundle"), ResourceLocation.withDefaultNamespace("item/red_bundle"), ResourceLocation.withDefaultNamespace("item/torch"));

        itemModelGenerator.generateLayeredItem(
            IronBundles.id("item/iron_bundle"),
            ResourceLocation.withDefaultNamespace("item/red_bundle"),
            ResourceLocation.withDefaultNamespace("item/torch")
        );
    }

    private static void singleTextureItem(String name, String texturePath, ItemModelGenerators generators) {
        ResourceLocation texture = ResourceLocation.withDefaultNamespace(texturePath);

        ModelTemplates.FLAT_ITEM.create(
            IronBundles.id("item/" + name),
            TextureMapping.layer0(texture),
            generators.output
        );
    }

    @Override
    public @NotNull String getName() {
        return "IronBundlesModelProvider";
    }

}
