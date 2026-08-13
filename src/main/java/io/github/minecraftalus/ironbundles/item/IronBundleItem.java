package io.github.minecraftalus.ironbundles.item;

import io.github.minecraftalus.ironbundles.IronBundlesComponents;
import io.github.minecraftalus.ironbundles.item.component.IronBundlesContents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import org.apache.commons.lang3.math.Fraction;

import java.util.Optional;

public class IronBundleItem extends BundleItem {
    public IronBundleItem(
        ResourceLocation resourceLocation, ResourceLocation resourceLocation2, Properties properties) {
        super(resourceLocation, resourceLocation2, properties);
    }

    public static float getFullnessDisplay(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(IronBundlesComponents.IRON_BUNDLES_CONTENTS, IronBundlesContents.EMPTY);
        return bundleContents.weight().floatValue();
    }

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

    public boolean overrideOtherStackedOnMe(
        ItemStack itemStack, ItemStack itemStack2, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
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

    public boolean isBarVisible(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(IronBundlesComponents.IRON_BUNDLES_CONTENTS, IronBundlesContents.EMPTY);
        return bundleContents.weight().compareTo(Fraction.ZERO) > 0;
    }

    public int getBarWidth(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(IronBundlesComponents.IRON_BUNDLES_CONTENTS, IronBundlesContents.EMPTY);
        return Math.min(1 + Mth.mulAndTruncate(bundleContents.weight(), 12), 13);
    }

    public int getBarColor(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(IronBundlesComponents.IRON_BUNDLES_CONTENTS, IronBundlesContents.EMPTY);
        return bundleContents.weight().compareTo(Fraction.ONE) >= 0 ? FULL_BAR_COLOR : BAR_COLOR;
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
        IronBundlesContents bundleContents = itemStack.getOrDefault(IronBundlesComponents.IRON_BUNDLES_CONTENTS, IronBundlesContents.EMPTY);
        return bundleContents.getSelectedItem() != -1;
    }

    public static int getSelectedItem(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(IronBundlesComponents.IRON_BUNDLES_CONTENTS, IronBundlesContents.EMPTY);
        return bundleContents.getSelectedItem();
    }

    public static ItemStack getSelectedItemStack(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(IronBundlesComponents.IRON_BUNDLES_CONTENTS,
            IronBundlesContents.EMPTY);
        return bundleContents.getItemUnsafe(bundleContents.getSelectedItem());
    }

    public static int getNumberOfItemsToShow(ItemStack itemStack) {
        IronBundlesContents bundleContents = itemStack.getOrDefault(IronBundlesComponents.IRON_BUNDLES_CONTENTS,
            IronBundlesContents.EMPTY);
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
            itemEntity.getItem().set(IronBundlesComponents.IRON_BUNDLES_CONTENTS, IronBundlesContents.EMPTY);
            ItemUtils.onContainerDestroyed(itemEntity, bundleContents.itemsCopy());
        }
    }
}
