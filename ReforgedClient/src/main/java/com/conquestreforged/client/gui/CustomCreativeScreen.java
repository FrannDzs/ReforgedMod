package com.conquestreforged.client.gui;

import net.minecraft.client.gui.screen.inventory.CreativeCraftingListener;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public abstract class CustomCreativeScreen<T extends Container> extends CustomContainerScreen<T> {

    private boolean clickedOutside = false;
    private CreativeCraftingListener listener;

    public CustomCreativeScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    protected abstract boolean isContainerSlot(Slot slot);

    protected void sendChanges() {
        if (this.minecraft != null && minecraft.player != null) {
            minecraft.player.inventoryMenu.broadcastChanges();
        }
    }

    @Override
    protected void init() {
        super.init();
        if (this.minecraft != null && minecraft.player != null) {
            minecraft.player.inventoryMenu.removeSlotListener(listener);
            listener = new CreativeCraftingListener(minecraft);
            minecraft.player.inventoryMenu.addSlotListener(listener);
        }
    }

    @Override
    public void removed() {
        super.removed();
        if (minecraft != null) {
            if (minecraft.player != null && minecraft.player.inventory != null) {
                minecraft.player.inventoryMenu.removeSlotListener(this.listener);
            }
            minecraft.keyboardHandler.setSendRepeatsToGui(false);
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        clickedOutside = super.hasClickedOutside(mx, my, leftPos, topPos, button);
        return super.mouseClicked(mx, my, button);
    }

    @Override
    protected void slotClicked(@Nullable Slot slot, int index, int button, ClickType type) {
        if (minecraft == null || minecraft.player == null || minecraft.gameMode == null) {
            return;
        }

        onSlotClick(slot, index, button, type);

        if (slot == null && type != ClickType.QUICK_CRAFT) {
            PlayerInventory playerInventory = this.minecraft.player.inventory;
            if (!playerInventory.getCarried().isEmpty()) {
                if (!clickedOutside) {
                    playerInventory.setCarried(ItemStack.EMPTY);
                    this.minecraft.player.inventoryMenu.broadcastChanges();
                    return;
                }

                if (button == 0) {
                    this.minecraft.player.drop(playerInventory.getCarried(), true);
                    this.minecraft.gameMode.handleCreativeModeItemDrop(playerInventory.getCarried());
                    playerInventory.setCarried(ItemStack.EMPTY);
                }

                if (button == 1) {
                    ItemStack stack = playerInventory.getCarried().split(1);
                    this.minecraft.player.drop(stack, true);
                    this.minecraft.gameMode.handleCreativeModeItemDrop(stack);
                }
            }
            return;
        }

        boolean quickMove = type == ClickType.QUICK_MOVE;
        type = index == -999 && type == ClickType.PICKUP ? ClickType.THROW : type;

        if (type != ClickType.QUICK_CRAFT && isContainerSlot(slot)) {
            PlayerInventory playerinventory = minecraft.player.inventory;
            ItemStack heldStack = playerinventory.getCarried();
            ItemStack slotStack = slot.getItem();
            if (type == ClickType.SWAP) {
                if (!slotStack.isEmpty() && button >= 0 && button < 9) {
                    ItemStack stack = slotStack.copy();
                    stack.setCount(stack.getMaxStackSize());
                    minecraft.player.inventory.setItem(button, stack);
                    minecraft.player.inventoryMenu.broadcastChanges();
                }

                return;
            }

            if (type == ClickType.CLONE) {
                if (playerinventory.getCarried().isEmpty() && slot.hasItem()) {
                    ItemStack stack = slot.getItem().copy();
                    stack.setCount(stack.getMaxStackSize());
                    playerinventory.setCarried(stack);
                }

                return;
            }

            if (type == ClickType.THROW) {
                if (!slotStack.isEmpty()) {
                    ItemStack stack = slotStack.copy();
                    stack.setCount(button == 0 ? 1 : stack.getMaxStackSize());
                    this.minecraft.player.drop(stack, true);
                    this.minecraft.gameMode.handleCreativeModeItemDrop(stack);
                }

                return;
            }

            if (!heldStack.isEmpty() && !slotStack.isEmpty() && heldStack.sameItem(slotStack) && ItemStack.tagMatches(heldStack, slotStack)) {
                if (button == 0) {
                    if (quickMove) {
                        heldStack.setCount(heldStack.getMaxStackSize());
                    } else if (heldStack.getCount() < heldStack.getMaxStackSize()) {
                        heldStack.grow(1);
                    }
                } else {
                    heldStack.shrink(1);
                }
            } else if (!slotStack.isEmpty() && heldStack.isEmpty()) {
                playerinventory.setCarried(slotStack.copy());
                heldStack = playerinventory.getCarried();
                if (quickMove) {
                    heldStack.setCount(heldStack.getMaxStackSize());
                }
            } else if (button == 0) {
                playerinventory.setCarried(ItemStack.EMPTY);
            } else {
                playerinventory.getCarried().shrink(1);
            }
        } else if (this.menu != null) {
            ItemStack slotStack = slot == null ? ItemStack.EMPTY : this.menu.getSlot(slot.index).getItem();
            this.menu.clicked(slot == null ? index : slot.index, button, type, this.minecraft.player);
            if (Container.getQuickcraftHeader(button) == 2) {
                int start = this.menu.slots.size() - 9;
                for (int k = 0; k < 9; ++k) {
                    this.minecraft.gameMode.handleCreativeModeItemAdd(this.menu.getSlot(start + k).getItem(), 36 + k);
                }
            } else if (slot != null) {
                ItemStack itemstack4 = this.menu.getSlot(slot.index).getItem();
                this.minecraft.gameMode.handleCreativeModeItemAdd(itemstack4, slot.index - (this.menu).slots.size() + 9 + 36);
                int i = 45 + button;
                if (type == ClickType.SWAP) {
                    this.minecraft.gameMode.handleCreativeModeItemAdd(slotStack, i - (this.menu).slots.size() + 9 + 36);
                } else if (type == ClickType.THROW && !slotStack.isEmpty()) {
                    ItemStack stack = slotStack.copy();
                    stack.setCount(button == 0 ? 1 : stack.getMaxStackSize());
                    this.minecraft.player.drop(stack, true);
                    this.minecraft.gameMode.handleCreativeModeItemDrop(stack);
                }

                this.minecraft.player.inventoryMenu.broadcastChanges();
            }
        }
    }
}
