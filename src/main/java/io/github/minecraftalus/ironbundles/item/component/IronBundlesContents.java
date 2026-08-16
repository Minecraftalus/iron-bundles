package io.github.minecraftalus.ironbundles.item.component;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.minecraftalus.ironbundles.component.BundleTier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.NotNull;

public class IronBundlesContents extends BundleContents {
    public static final Codec<IronBundlesContents> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, IronBundlesContents> STREAM_CODEC;

    private final Fraction maxWeight;

    public IronBundlesContents(List<ItemStack> list, Fraction maxWeight) {
        super(list);
        this.maxWeight = maxWeight;
    }

    private IronBundlesContents(List<ItemStack> list, Fraction weight, int i, Fraction maxWeight) {
        super(list, weight, i);
        this.maxWeight = maxWeight;
    }

    public static DataResult<IronBundlesContents> checkAndCreate(List<ItemStack> list, Fraction maxWeight) {
        try {
            Fraction fraction = computeContentWeight(list);
            return DataResult.success(new IronBundlesContents(list, fraction, -1, maxWeight));
        } catch (ArithmeticException var2) {
            return DataResult.error(() -> "Excessive total bundle weight");
        }
    }

    public static IronBundlesContents empty(Fraction maxWeight) {
        return new IronBundlesContents(List.of(), maxWeight);
    }

    public static class Mutable extends BundleContents.Mutable {
        private final Fraction maxWeight;

        public Mutable(IronBundlesContents bundleContents) {
            super(bundleContents);
            this.maxWeight = bundleContents.maxWeight;
        }

        @Override
        protected int getMaxAmountToAdd(ItemStack itemStack) {
            Fraction fraction = maxWeight.subtract(this.weight());
            return Math.max(fraction.divideBy(getWeight(itemStack)).intValue(), 0);
        }

        @Override
        public @NotNull IronBundlesContents toImmutable() {
            return new IronBundlesContents(List.copyOf(this.items), this.weight(), this.selectedItem, this.maxWeight);
        }
    }

    static {
        CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.listOf().fieldOf("items").forGetter(c -> c.items),
            Codec.STRING.xmap(Fraction::getFraction, Fraction::toString).fieldOf("max_weight").forGetter(contents -> contents.maxWeight)
        ).apply(instance, (items, maxWeight) -> checkAndCreate(items, maxWeight).getOrThrow()));

        STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
            c -> c.items,
            ByteBufCodecs.STRING_UTF8.map(Fraction::getFraction, Fraction::toString),
            c -> c.maxWeight,
            (items, maxWeight) -> new IronBundlesContents(items, computeContentWeight(items), -1, maxWeight)
        );
    }
}
