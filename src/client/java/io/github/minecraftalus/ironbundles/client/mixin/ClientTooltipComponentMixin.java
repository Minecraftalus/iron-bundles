package io.github.minecraftalus.ironbundles.client.mixin;

import io.github.minecraftalus.ironbundles.client.gui.ClientIronBundleTooltip;
import io.github.minecraftalus.ironbundles.component.IronBundleTooltip;
import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientTooltipComponent.class)
public interface ClientTooltipComponentMixin {
    @Inject(method = "create(Lnet/minecraft/world/inventory/tooltip/TooltipComponent;)Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipComponent;", at = @At("HEAD"), cancellable = true)
    private static void create(TooltipComponent tooltipComponent, CallbackInfoReturnable<ClientTooltipComponent> cir) {
        if (tooltipComponent instanceof IronBundleTooltip(
            IronBundlesContents contents,
            Fraction maxWeight
        )) {
            cir.setReturnValue(new ClientIronBundleTooltip(contents, maxWeight));
        }
    }
}
