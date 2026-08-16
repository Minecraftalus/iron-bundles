package io.github.minecraftalus.ironbundles.client.gui;

import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClientIronBundleTooltip extends ClientBundleTooltip {
    private IronBundlesContents contents;
    private Fraction maxWeight;

    public ClientIronBundleTooltip(IronBundlesContents bundleContents, Fraction maxWeight) {
        super(bundleContents);
        this.contents = bundleContents;
        this.maxWeight = maxWeight;
    }

    @Override
    public int getProgressBarFill() {
        return Mth.clamp(Mth.mulAndTruncate(this.contents.weight().divideBy(maxWeight), 94), 0, 94);
    }

    @Override
    public @NotNull ResourceLocation getProgressBarTexture() {
        return this.contents.weight().compareTo(maxWeight) >= 0 ? PROGRESSBAR_FULL_SPRITE : PROGRESSBAR_FILL_SPRITE;
    }

    @Override
    public @Nullable Component getProgressBarFillText() {
        if (this.contents.isEmpty()) {
            return BUNDLE_EMPTY_TEXT;
        } else {
            return this.contents.weight().compareTo(maxWeight) >= 0 ? BUNDLE_FULL_TEXT : null;
        }
    }
}
