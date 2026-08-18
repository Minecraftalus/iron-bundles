package io.github.minecraftalus.ironbundles.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import io.github.minecraftalus.ironbundles.BundleUpgradeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BundleUpgradeRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Holder<Item> result;
    private final Ingredient input;
    private final Ingredient material;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    private BundleUpgradeRecipeBuilder(RecipeCategory recipeCategory, Holder<Item> holder, Ingredient ingredient, Ingredient ingredient2) {
        this.category = recipeCategory;
        this.result = holder;
        this.input = ingredient;
        this.material = ingredient2;
    }

    public static BundleUpgradeRecipeBuilder upgrade(RecipeCategory recipeCategory, Ingredient ingredient, Ingredient ingredient2, Item item) {
        return new BundleUpgradeRecipeBuilder(recipeCategory, item.builtInRegistryHolder(), ingredient, ingredient2);
    }

    public @NotNull BundleUpgradeRecipeBuilder unlockedBy(String string, Criterion<?> criterion) {
        this.criteria.put(string, criterion);
        return this;
    }

    public @NotNull BundleUpgradeRecipeBuilder group(@Nullable String string) {
        this.group = string;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result.value();
    }

    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
        this.ensureValid(resourceKey);
        Advancement.Builder builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey)).rewards(Builder.recipe(resourceKey)).requirements(Strategy.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::addCriterion);
        BundleUpgradeRecipe upgradeRecipe = new BundleUpgradeRecipe(Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), this.input, this.material, this.result);
        recipeOutput.accept(resourceKey, upgradeRecipe, builder.build(resourceKey.location().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> resourceKey) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceKey.location());
        }
    }
}
