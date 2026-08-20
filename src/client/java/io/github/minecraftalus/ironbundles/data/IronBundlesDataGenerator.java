package io.github.minecraftalus.ironbundles.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class IronBundlesDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(IronBundlesRecipeProvider::new);
        pack.addProvider(IronBundlesItemTagProvider::new);
        pack.addProvider(IronBundlesModelProvider::new);
        pack.addProvider(IronBundlesTextureProvider::new);
        pack.addProvider(IronBundlesLangProvider::new);
    }
}
