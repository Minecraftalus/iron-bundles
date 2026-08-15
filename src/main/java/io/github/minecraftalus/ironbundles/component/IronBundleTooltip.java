package io.github.minecraftalus.ironbundles.component;

import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record IronBundleTooltip(IronBundlesContents contents) implements TooltipComponent {
}
