package io.github.minecraftalus.ironbundles.client.mixin;

import io.github.minecraftalus.ironbundles.client.gui.IronBundlesMouseActions;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        AbstractContainerScreen<?> thiz = (AbstractContainerScreen<?>) (Object) this;

        thiz.addItemSlotMouseAction(new IronBundlesMouseActions(thiz.minecraft));
    }
}
