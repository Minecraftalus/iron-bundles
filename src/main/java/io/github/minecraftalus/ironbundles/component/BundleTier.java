package io.github.minecraftalus.ironbundles.component;

import org.apache.commons.lang3.math.Fraction;

public enum BundleTier {
    COPPER(32 * 3),
    IRON(64 * 2),
    GOLD(64 * 4),
    EMERALD(64 * 6),
    DIAMOND(64 * 10),
    NETHERITE(64 * 16);

    private Fraction weight;

    BundleTier(int itemCount) {
        this.weight = Fraction.getFraction(itemCount, 64);
    }

    public Fraction getWeight() {
        return weight;
    }
}
