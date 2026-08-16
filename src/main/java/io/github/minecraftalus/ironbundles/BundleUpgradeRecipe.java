//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.github.minecraftalus.ironbundles;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.minecraftalus.ironbundles.item.IronBundleItem;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BundleUpgradeRecipe implements CraftingRecipe {
    final String group;
    final CraftingBookCategory category;
    final Ingredient input;
    final Ingredient material;
    final Holder<Item> result;
    @Nullable
    private PlacementInfo placementInfo;

    public BundleUpgradeRecipe(
        String string, CraftingBookCategory craftingBookCategory, Ingredient ingredient, Ingredient ingredient2, Holder<Item> holder
    ) {
        this.group = string;
        this.category = craftingBookCategory;
        this.input = ingredient;
        this.material = ingredient2;
        this.result = holder;
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        if (craftingInput.ingredientCount() != 2) {
            return false;
        } else {
            boolean stackOneGood = false;
            boolean stackTwoGood = false;

            for (int i = 0; i < craftingInput.size(); ++i) {
                ItemStack itemStack = craftingInput.getItem(i);
                if (!itemStack.isEmpty()) {
                    if (!stackOneGood && this.input.test(itemStack) && itemStack.getItem() != this.result.value()) {
                        stackOneGood = true;
                    } else {
                        if (stackTwoGood || !this.material.test(itemStack)) {
                            return false;
                        }

                        stackTwoGood = true;
                    }
                }
            }

            return stackOneGood && stackTwoGood;
        }
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        ItemStack resultStack = ItemStack.EMPTY;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack currentStack = craftingInput.getItem(i);
            if (!currentStack.isEmpty() && this.input.test(currentStack) && currentStack.getItem() != this.result.value()) {
                resultStack = currentStack;
            }
        }
        return resultStack.transmuteCopy(this.result.value(), 1);
    }

    @Override
    public @NotNull List<RecipeDisplay> display() {
        return List.of(
            new ShapelessCraftingRecipeDisplay(
                List.of(this.input.display(), this.material.display()),
                new SlotDisplay.ItemSlotDisplay(this.result),
                new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)));
    }

    @Override
    public @NotNull RecipeSerializer<BundleUpgradeRecipe> getSerializer() {
        return IronBundlesRecipes.BUNDLE_UPGRADE_RECIPE;
    }

    @Override
    public @NotNull String group() {
        return this.group;
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(List.of(this.input, this.material));
        }

        return this.placementInfo;
    }

    @Override
    public @NotNull CraftingBookCategory category() {
        return this.category;
    }

    public static class Serializer implements RecipeSerializer<BundleUpgradeRecipe> {
        private static final MapCodec<BundleUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter((upgradeRecipe) -> upgradeRecipe.group),
                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.EQUIPMENT)
                    .forGetter((upgradeRecipe) -> upgradeRecipe.category),
                Ingredient.CODEC.fieldOf("input").forGetter((upgradeRecipe) -> upgradeRecipe.input),
                Ingredient.CODEC.fieldOf("material").forGetter((upgradeRecipe) -> upgradeRecipe.material),
                Item.CODEC.fieldOf("result").forGetter((upgradeRecipe) -> upgradeRecipe.result)).apply(instance, BundleUpgradeRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, BundleUpgradeRecipe> STREAM_CODEC;

        public @NotNull MapCodec<BundleUpgradeRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, BundleUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        static {
            STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                (upgradeRecipe) -> upgradeRecipe.group,
                CraftingBookCategory.STREAM_CODEC,
                (upgradeRecipe) -> upgradeRecipe.category,
                Ingredient.CONTENTS_STREAM_CODEC,
                (upgradeRecipe) -> upgradeRecipe.input,
                Ingredient.CONTENTS_STREAM_CODEC,
                (upgradeRecipe) -> upgradeRecipe.material,
                ByteBufCodecs.holderRegistry(Registries.ITEM),
                (upgradeRecipe) -> upgradeRecipe.result,
                BundleUpgradeRecipe::new);
        }
    }
}
