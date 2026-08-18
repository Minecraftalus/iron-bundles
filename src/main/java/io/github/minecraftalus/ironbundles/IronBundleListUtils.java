package io.github.minecraftalus.ironbundles;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IronBundleListUtils {
    public static final List<Map.Entry<Item, String>> dyeNameMappings;
    public static final List<Map.Entry<Item, Item>> dyeBundleMappings;

    static {
        List<Map.Entry<Item, String>> dyeNameTemp = new ArrayList<>();
        dyeNameTemp.add(getEntry(Items.WHITE_DYE, "white"));
        dyeNameTemp.add(getEntry(Items.ORANGE_DYE, "orange"));
        dyeNameTemp.add(getEntry(Items.MAGENTA_DYE, "magenta"));
        dyeNameTemp.add(getEntry(Items.LIGHT_BLUE_DYE, "light_blue"));
        dyeNameTemp.add(getEntry(Items.YELLOW_DYE, "yellow"));
        dyeNameTemp.add(getEntry(Items.LIME_DYE, "lime"));
        dyeNameTemp.add(getEntry(Items.PINK_DYE, "pink"));
        dyeNameTemp.add(getEntry(Items.GRAY_DYE, "gray"));
        dyeNameTemp.add(getEntry(Items.LIGHT_GRAY_DYE, "light_gray"));
        dyeNameTemp.add(getEntry(Items.CYAN_DYE, "cyan"));
        dyeNameTemp.add(getEntry(Items.PURPLE_DYE, "purple"));
        dyeNameTemp.add(getEntry(Items.BLUE_DYE, "blue"));
        dyeNameTemp.add(getEntry(Items.BROWN_DYE, "brown"));
        dyeNameTemp.add(getEntry(Items.GREEN_DYE, "green"));
        dyeNameTemp.add(getEntry(Items.RED_DYE, "red"));
        dyeNameTemp.add(getEntry(Items.BLACK_DYE, "black"));
        dyeNameMappings = List.copyOf(dyeNameTemp);

        List<Map.Entry<Item, Item>> dyeBundleTemp = new ArrayList<>();
        dyeBundleTemp.add(getEntry(Items.WHITE_DYE, Items.WHITE_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.ORANGE_DYE, Items.ORANGE_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.MAGENTA_DYE, Items.MAGENTA_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.LIGHT_BLUE_DYE, Items.LIGHT_BLUE_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.YELLOW_DYE, Items.YELLOW_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.LIME_DYE, Items.LIME_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.PINK_DYE, Items.PINK_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.GRAY_DYE, Items.GRAY_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.LIGHT_GRAY_DYE, Items.LIGHT_GRAY_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.CYAN_DYE, Items.CYAN_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.PURPLE_DYE, Items.PURPLE_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.BLUE_DYE, Items.BLUE_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.BROWN_DYE, Items.BROWN_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.GREEN_DYE, Items.GREEN_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.RED_DYE, Items.RED_BUNDLE));
        dyeBundleTemp.add(getEntry(Items.BLACK_DYE, Items.BLACK_BUNDLE));
        dyeBundleMappings = List.copyOf(dyeBundleTemp);
    }

    private static <K, V> Map.Entry<K, V> getEntry(K one, V two) {
        return Map.entry(one, two);
    }

}
