package io.github.minecraftalus.ironbundles.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.github.minecraftalus.ironbundles.IronBundlesComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IronBundlesContents extends BundleContents {
    public static final Codec<IronBundlesContents> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, IronBundlesContents> STREAM_CODEC;
    public static final IronBundlesContents EMPTY = new IronBundlesContents(List.of());

    public IronBundlesContents(List<ItemStack> list) {
        super(list);
    }

    private IronBundlesContents(List<ItemStack> list, Fraction fraction, int i) {
        super(list, fraction, i);
    }

    public static DataResult<IronBundlesContents> checkAndCreate(List<ItemStack> list) {
        try {
            Fraction fraction = computeContentWeight(list);
            return DataResult.success(new IronBundlesContents(list, fraction, -1));
        } catch (ArithmeticException var2) {
            return DataResult.error(() -> "Excessive total bundle weight");
        }
    }

    public static class Mutable extends BundleContents.Mutable {
        public Mutable(IronBundlesContents bundleContents) {
            super(bundleContents);
        }

        @Override
        public @NotNull IronBundlesContents toImmutable() {
            return new IronBundlesContents(List.copyOf(this.items), this.weight(), this.selectedItem);
        }
    }

    static {
        CODEC = ItemStack.CODEC.listOf().flatXmap(IronBundlesContents::checkAndCreate, (ironBundleContents) -> DataResult.success(ironBundleContents.items));
        STREAM_CODEC = ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()).map(IronBundlesContents::new, (ironBundleContents) -> ironBundleContents.items);
    }
}
