package io.github.minecraftalus.ironbundles.data;

import io.github.minecraftalus.ironbundles.IronBundlesTags;
import io.github.minecraftalus.ironbundles.item.IronBundlesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IronBundlesLangProvider extends FabricLanguageProvider {
    protected IronBundlesLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(IronBundlesItems.IRON_BUNDLES_TAB_KEY, "Iron Bundles");

        translationBuilder.add(IronBundlesTags.COPPER_BUNDLES, "Copper Bundles");
        translationBuilder.add(IronBundlesTags.IRON_BUNDLES, "Iron Bundles");
        translationBuilder.add(IronBundlesTags.GOLD_BUNDLES, "Gold Bundles");
        translationBuilder.add(IronBundlesTags.EMERALD_BUNDLES, "Emerald Bundles");
        translationBuilder.add(IronBundlesTags.DIAMOND_BUNDLES, "Diamond Bundles");
        translationBuilder.add(IronBundlesTags.NETHERITE_BUNDLES, "Netherite Bundles");
        translationBuilder.add(IronBundlesTags.IRON_BUNDLES_MOD_BUNDLES, "Iron Bundles Mod Bundles");

        translationBuilder.add(IronBundlesItems.COPPER_UPGRADE_ITEM, "Copper Bundle Upgrade");
        translationBuilder.add(IronBundlesItems.IRON_UPGRADE_ITEM, "Iron Bundle Upgrade");
        translationBuilder.add(IronBundlesItems.GOLD_UPGRADE_ITEM, "Gold Bundle Upgrade");
        translationBuilder.add(IronBundlesItems.EMERALD_UPGRADE_ITEM, "Emerald Bundle Upgrade");
        translationBuilder.add(IronBundlesItems.DIAMOND_UPGRADE_ITEM, "Diamond Bundle Upgrade");
        translationBuilder.add(IronBundlesItems.NETHERITE_UPGRADE_ITEM, "Netherite Bundle Upgrade");

        List<Item> items = IronBundlesItems.getAllBundleItems();
        List<ResourceKey<Item>> keys = IronBundlesItems.getAllBundleKeys();
        for (int i = 0; i < keys.size(); i++) {
            translationBuilder.add(items.get(i), getBundleTranslation(keys.get(i)));
        }
    }

    private static String getBundleTranslation(ResourceKey<Item> key) {
        String keyPath = key.location().getPath();

        String spacedKey = keyPath.replace('_', ' ');

        return toTitleCase(spacedKey);
    }

    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }
            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
