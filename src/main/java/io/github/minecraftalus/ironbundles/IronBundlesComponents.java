package io.github.minecraftalus.ironbundles;

import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.UnaryOperator;

public class IronBundlesComponents {
    public static final DataComponentType<IronBundlesContents> IRON_BUNDLES_CONTENTS = register("iron_bundles_contents",
        (builder) -> builder.persistent(IronBundlesContents.CODEC).networkSynchronized(IronBundlesContents.STREAM_CODEC).cacheEncoding());

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> operator) {
        DataComponentType.Builder<T> builder = operator.apply(DataComponentType.builder());
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, name, builder.build());
    }

    public static DataComponentType<?> bootstrap() {
        return IRON_BUNDLES_CONTENTS;
    }
}
