package io.github.minecraftalus.ironbundles.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.minecraftalus.ironbundles.item.IronBundleItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(method = "renderBundleItem", at = @At("HEAD"))
    private void renderBundleItem(
        ItemStack itemStack,
        ItemDisplayContext itemDisplayContext,
        boolean bl,
        PoseStack poseStack,
        MultiBufferSource multiBufferSource,
        int i,
        int j,
        BakedModel bakedModel,
        Level level,
        LivingEntity livingEntity,
        int k,
        CallbackInfo ci
    ) {
        ItemRenderer thiz = (ItemRenderer) (Object) this;
        Item item = itemStack.getItem();
        if (item instanceof IronBundleItem bundleItem) {

            if (IronBundleItem.hasSelectedItem(itemStack)) {
                boolean bl2 = ItemRenderer.shouldRenderItemFlat(itemDisplayContext);
                BakedModel bakedModel2 = thiz.resolveModelOverride(
                    thiz.itemModelShaper.getItemModel(bundleItem.openBackModel()),
                    itemStack,
                    level,
                    livingEntity,
                    k);
                thiz.renderItemModelRaw(itemStack, itemDisplayContext, bl, poseStack, multiBufferSource, i, j, bakedModel2, bl2, -1.5F);
                ItemStack itemStack2 = IronBundleItem.getSelectedItemStack(itemStack);
                BakedModel bakedModel3 = thiz.getModel(itemStack2, level, livingEntity, k);
                thiz.renderSimpleItemModel(itemStack2, itemDisplayContext, bl, poseStack, multiBufferSource, i, j, bakedModel3, bl2);
                BakedModel bakedModel4 = thiz
                    .resolveModelOverride(thiz.itemModelShaper.getItemModel(bundleItem.openFrontModel()), itemStack, level, livingEntity, k);
                thiz.renderItemModelRaw(itemStack, itemDisplayContext, bl, poseStack, multiBufferSource, i, j, bakedModel4, bl2, 0.5F);
            } else {
                thiz.render(itemStack, itemDisplayContext, bl, poseStack, multiBufferSource, i, j, bakedModel);
            }
        }
    }
}
