package io.github.minecraftalus.ironbundles.component;

import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.apache.commons.lang3.math.Fraction;

public record IronBundleTooltip(IronBundlesContents contents, Fraction maxWeight) implements TooltipComponent {}
