package io.github.minecraftalus.ironbundles;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class IronBundlesRecipes {
    public static RecipeSerializer<BundleUpgradeRecipe> BUNDLE_UPGRADE_RECIPE = register(
        "crafting_bundle_upgrade",
        new BundleUpgradeRecipe.Serializer());

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String string, S recipeSerializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, IronBundles.id(string), recipeSerializer);
    }

    public static RecipeSerializer<?> bootstrap() {
        return BUNDLE_UPGRADE_RECIPE;
    }
}
