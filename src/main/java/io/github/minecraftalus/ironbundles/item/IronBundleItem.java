package io.github.minecraftalus.ironbundles.item;

import java.util.Optional;

import io.github.minecraftalus.ironbundles.IronBundlesComponents;
import io.github.minecraftalus.ironbundles.component.IronBundleTooltip;
import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.NotNull;

public class IronBundleItem extends BundleItem {
    private final Fraction maxWeight;

    public IronBundleItem(
        ResourceLocation front, ResourceLocation back, Fraction maxWeight, Properties properties
    ) {
        super(front, back, properties);
        this.maxWeight = maxWeight;
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
        return !itemStack.has(DataComponents.HIDE_TOOLTIP) && !itemStack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP)
            ? Optional.ofNullable(itemStack.get(IronBundlesComponents.IRON_BUNDLES_CONTENTS)).map(
                (contents) -> new IronBundleTooltip(contents, maxWeight))
            : Optional.empty();
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack itemStack, Slot slot, ClickAction clickAction, Player player) {
        IronBundlesContents bundleContents = itemStack.get(IronBundlesComponents.IRON_BUNDLES_CONTENTS);
        if (bundleContents == null) {
            return false;
        } else {
            ItemStack itemStack2 = slot.getItem();
            IronBundlesContents.Mutable mutable = new IronBundlesContents.Mutable(bundleContents);
            if (clickAction == ClickAction.PRIMARY && !itemStack2.isEmpty()) {
                if (mutable.tryTransfer(slot, player) > 0) {
                    playInsertSound(player);
                } else {
                    playInsertFailSound(player);
                }

                itemStack.set(IronBundlesComponents.IRON_BUNDLES_CONTENTS, mutable.toImmutable());
                this.broadcastChangesOnContainerMenu(player);
                return true;
            } else if (clickAction == ClickAction.SECONDARY && itemStack2.isEmpty()) {
                ItemStack itemStack3 = mutable.removeOne();
                if (itemStack3 != null) {
                    ItemStack itemStack4 = slot.safeInsert(itemStack3);
                    if (itemStack4.getCount() > 0) {
                        mutable.tryInsert(itemStack4);
                    } else {
                        playRemoveOneSound(player);
                    }
                }

                itemStack.set(IronBundlesComponents.IRON_BUNDLES_CONTENTS, mutable.toImmutable());
                this.broadcastChangesOnContainerMenu(player);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(
        ItemStack itemStack,
        ItemStack itemStack2,
        Slot slot,
        ClickAction clickAction,
        Player player,
        SlotAccess slotAccess
    ) {
        if (clickAction == ClickAction.PRIMARY && itemStack2.isEmpty()) {
            toggleSelectedItem(itemStack, -1);
            return false;
        } else {
            IronBundlesContents bundleContents = itemStack.get(IronBundlesComponents.IRON_BUNDLES_CONTENTS);
            if (bundleContents == null) {
                return false;
            } else {
                IronBundlesContents.Mutable mutable = new IronBundlesContents.Mutable(bundleContents);
                if (clickAction == ClickAction.PRIMARY && !itemStack2.isEmpty()) {
                    if (slot.allowModification(player) && mutable.tryInsert(itemStack2) > 0) {
                        playInsertSound(player);
                    } else {
                        playInsertFailSound(player);
                    }

                    itemStack.set(IronBundlesComponents.IRON_BUNDLES_CONTENTS, mutable.toImmutable());
                    this.broadcastChangesOnContainerMenu(player);
                    return true;
                } else if (clickAction == ClickAction.SECONDARY && itemStack2.isEmpty()) {
                    if (slot.allowModification(player)) {
                        ItemStack itemStack3 = mutable.removeOne();
                        if (itemStack3 != null) {
                            playRemoveOneSound(player);
                            slotAccess.set(itemStack3);
                        }
                    }

                    itemStack.set(IronBundlesComponents.IRON_BUNDLES_CONTENTS, mutable.toImmutable());
                    this.broadcastChangesOnContainerMenu(player);
                    return true;
                } else {
                    toggleSelectedItem(itemStack, -1);
                    return false;
                }
            }
        }
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(
            IronBundlesComponents.IRON_BUNDLES_CONTENTS,
            IronBundlesContents.empty(maxWeight));
        return bundleContents.weight().compareTo(Fraction.ZERO) > 0;
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(
            IronBundlesComponents.IRON_BUNDLES_CONTENTS,
            IronBundlesContents.empty(maxWeight));
        return Math.min(
            1 + Mth.mulAndTruncate(bundleContents.weight().divideBy(this.maxWeight), 12),
            MAX_BAR_WIDTH);
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(
            IronBundlesComponents.IRON_BUNDLES_CONTENTS,
            IronBundlesContents.empty(maxWeight));
        return bundleContents.weight().compareTo(this.maxWeight) >= 0 ? FULL_BAR_COLOR : BAR_COLOR;
    }

    public static void toggleSelectedItem(ItemStack itemStack, int i) {
        IronBundlesContents bundleContents = itemStack.get(IronBundlesComponents.IRON_BUNDLES_CONTENTS);
        if (bundleContents != null) {
            IronBundlesContents.Mutable mutable = new IronBundlesContents.Mutable(bundleContents);
            mutable.toggleSelectedItem(i);
            itemStack.set(IronBundlesComponents.IRON_BUNDLES_CONTENTS, mutable.toImmutable());
        }
    }

    public static boolean hasSelectedItem(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(
            IronBundlesComponents.IRON_BUNDLES_CONTENTS,
            IronBundlesContents.empty(Fraction.ONE));
        return bundleContents.getSelectedItem() != -1;
    }

    public static int getSelectedItem(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(
            IronBundlesComponents.IRON_BUNDLES_CONTENTS,
            IronBundlesContents.empty(Fraction.ONE));
        return bundleContents.getSelectedItem();
    }

    public static ItemStack getSelectedItemStack(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(
            IronBundlesComponents.IRON_BUNDLES_CONTENTS,
            IronBundlesContents.empty(Fraction.ONE));
        return bundleContents.getItemUnsafe(bundleContents.getSelectedItem());
    }

    public static int getNumberOfItemsToShow(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(
            IronBundlesComponents.IRON_BUNDLES_CONTENTS,
            IronBundlesContents.empty(Fraction.ONE));
        return bundleContents.getNumberOfItemsToShow();
    }

    private boolean dropContent(ItemStack itemStack, Player player) {
        IronBundlesContents bundleContents = itemStack.get(IronBundlesComponents.IRON_BUNDLES_CONTENTS);
        if (bundleContents != null && !bundleContents.isEmpty()) {
            Optional<ItemStack> optional = removeOneItemFromBundle(itemStack, player, bundleContents);
            if (optional.isPresent()) {
                player.drop(optional.get(), true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static Optional<ItemStack> removeOneItemFromBundle(ItemStack itemStack, Player player, IronBundlesContents bundleContents) {
        IronBundlesContents.Mutable mutable = new IronBundlesContents.Mutable(bundleContents);
        ItemStack itemStack2 = mutable.removeOne();
        if (itemStack2 != null) {
            playRemoveOneSound(player);
            itemStack.set(IronBundlesComponents.IRON_BUNDLES_CONTENTS, mutable.toImmutable());
            return Optional.of(itemStack2);
        } else {
            return Optional.empty();
        }
    }

    public void onDestroyed(ItemEntity itemEntity) {
        IronBundlesContents bundleContents = itemEntity.getItem().get(IronBundlesComponents.IRON_BUNDLES_CONTENTS);
        if (bundleContents != null) {
            itemEntity.getItem().set(IronBundlesComponents.IRON_BUNDLES_CONTENTS, IronBundlesContents.empty(maxWeight));
            ItemUtils.onContainerDestroyed(itemEntity, bundleContents.itemsCopy());
        }
    }

    @Override
    public void onCraftedPostProcess(ItemStack itemStack, Level level) {
        IronBundlesContents bundleContents = itemStack.get(IronBundlesComponents.IRON_BUNDLES_CONTENTS);
        if (bundleContents == null)
            return;
        bundleContents.setMaxWeight(this.maxWeight);
    }

    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            int j = this.getUseDuration(itemStack, livingEntity);
            boolean bl = i == j;
            if (bl || i < j - 10 && i % 2 == 0) {
                this.dropContent(level, player, itemStack);
            }
        }
    }

    private void dropContent(Level level, Player player, ItemStack itemStack) {
        if (this.dropContent(itemStack, player)) {
            playDropContentsSound(level, player);
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
