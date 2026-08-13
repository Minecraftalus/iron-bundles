package io.github.minecraftalus.ironbundles.client.gui;

import io.github.minecraftalus.ironbundles.item.IronBundleItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ScrollWheelHandler;
import net.minecraft.client.gui.ItemSlotMouseAction;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ServerboundSelectBundleItemPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2i;

@Environment(EnvType.CLIENT)
public class IronBundlesMouseActions implements ItemSlotMouseAction {
    private final Minecraft minecraft;
    private final ScrollWheelHandler scrollWheelHandler;

    public IronBundlesMouseActions(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.scrollWheelHandler = new ScrollWheelHandler();
    }

    public boolean matches(Slot slot) {
        return slot.getItem().is(ItemTags.BUNDLES);
    }

    public boolean onMouseScrolled(double d, double e, int i, ItemStack itemStack) {
        int j = IronBundleItem.getNumberOfItemsToShow(itemStack);
        if (j == 0) {
            return false;
        } else {
            Vector2i vector2i = this.scrollWheelHandler.onMouseScroll(d, e);
            int k = vector2i.y == 0 ? -vector2i.x : vector2i.y;
            if (k != 0) {
                int l = IronBundleItem.getSelectedItem(itemStack);
                int m = ScrollWheelHandler.getNextScrollWheelSelection((double)k, l, j);
                if (l != m) {
                    this.toggleSelectedBundleItem(itemStack, i, m);
                }
            }

            return true;
        }
    }

    public void onStopHovering(Slot slot) {
        this.unselectedBundleItem(slot.getItem(), slot.index);
    }

    public void onSlotClicked(Slot slot, ClickType clickType) {
        if (clickType == ClickType.QUICK_MOVE || clickType == ClickType.SWAP) {
            this.unselectedBundleItem(slot.getItem(), slot.index);
        }

    }

    private void toggleSelectedBundleItem(ItemStack itemStack, int i, int j) {
        if (this.minecraft.getConnection() != null && j < IronBundleItem.getNumberOfItemsToShow(itemStack)) {
            ClientPacketListener clientPacketListener = this.minecraft.getConnection();
            IronBundleItem.toggleSelectedItem(itemStack, j);
            clientPacketListener.send(new ServerboundSelectBundleItemPacket(i, j));
        }

    }

    public void unselectedBundleItem(ItemStack itemStack, int i) {
        this.toggleSelectedBundleItem(itemStack, i, -1);
    }
}
