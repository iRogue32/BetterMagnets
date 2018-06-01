package max.bettermagnets.items.ItemMagnet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMagnetItem extends Container {
	
	ItemStackHandlerBlacklist blacklist;
	InventoryPlayer inventory;
	
	public ContainerMagnetItem() {
		//should never get called
	}
	
	public ContainerMagnetItem(InventoryPlayer inventory) {
		ItemMagnetItem magnet = ((ItemMagnetItem)inventory.getStackInSlot(inventory.currentItem).getItem());
		this.inventory = inventory;
		this.blacklist = new ItemStackHandlerBlacklist(((ItemMagnetItem)inventory.getStackInSlot(inventory.currentItem).getItem()).blacklistSize);
		
		if (blacklist.getSlots() == magnet.TIER_ONE_FILTER_SIZE) {
			addSlotToContainer(new SlotBlacklist(blacklist, 0, 80, 17));
			addSlotToContainer(new SlotBlacklist(blacklist, 1, 80, 35));
			addSlotToContainer(new SlotBlacklist(blacklist, 2, 80, 53));
		}
		
		else if (blacklist.getSlots() == magnet.TIER_TWO_FILTER_SIZE) {
			addSlotToContainer(new SlotBlacklist(blacklist, 0, 62, 17));
			addSlotToContainer(new SlotBlacklist(blacklist, 1, 80, 17));
			addSlotToContainer(new SlotBlacklist(blacklist, 2, 98, 17));
			addSlotToContainer(new SlotBlacklist(blacklist, 3, 62, 35));
			addSlotToContainer(new SlotBlacklist(blacklist, 4, 80, 35));
			addSlotToContainer(new SlotBlacklist(blacklist, 5, 98, 35));
			addSlotToContainer(new SlotBlacklist(blacklist, 6, 62, 53));
			addSlotToContainer(new SlotBlacklist(blacklist, 7, 80, 53));
			addSlotToContainer(new SlotBlacklist(blacklist, 8, 98, 53));
		}
		
		else if (blacklist.getSlots() == magnet.TIER_THREE_FILTER_SIZE) {
			addSlotToContainer(new SlotBlacklist(blacklist, 0, 44, 17));
			addSlotToContainer(new SlotBlacklist(blacklist, 1, 62, 17));
			addSlotToContainer(new SlotBlacklist(blacklist, 2, 80, 17));
			addSlotToContainer(new SlotBlacklist(blacklist, 3, 98, 17));
			addSlotToContainer(new SlotBlacklist(blacklist, 4, 116, 17));
			addSlotToContainer(new SlotBlacklist(blacklist, 5, 44, 35));
			addSlotToContainer(new SlotBlacklist(blacklist, 6, 62, 35));
			addSlotToContainer(new SlotBlacklist(blacklist, 7, 80, 35));
			addSlotToContainer(new SlotBlacklist(blacklist, 8, 98, 35));
			addSlotToContainer(new SlotBlacklist(blacklist, 9, 116, 35));
			addSlotToContainer(new SlotBlacklist(blacklist, 10, 44, 53));
			addSlotToContainer(new SlotBlacklist(blacklist, 11, 62, 53));
			addSlotToContainer(new SlotBlacklist(blacklist, 12, 80, 53));
			addSlotToContainer(new SlotBlacklist(blacklist, 13, 98, 53));
			addSlotToContainer(new SlotBlacklist(blacklist, 14, 116, 53));
		}
		
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int k = 0; k < 9; ++k) {
			addSlotToContainer(new Slot(inventory, k, 8 + k * 18, 142));
		}
		ItemStack stack = inventory.getCurrentItem();
		if(stack != ItemStack.EMPTY && stack.getItem() instanceof ItemMagnetItem) {
			ItemMagnetItem.readBlacklistFromNBT(blacklist, inventory.getCurrentItem());
		}
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if (slotId >= 0 && slotId < this.inventorySlots.size()) {
			Slot slot = this.getSlot(slotId);
			if (slot instanceof SlotBlacklist) {
				((SlotBlacklist) slot).slotClicked(player);
				return ItemStack.EMPTY;
			}
		}
		else if (clickTypeIn == ClickType.SWAP && dragType == this.inventory.currentItem) {
			return ItemStack.EMPTY;
		}
		else {
			return super.slotClick(slotId, dragType, clickTypeIn, player);
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		ItemStack stack = playerIn.inventory.getCurrentItem();
		if(stack != ItemStack.EMPTY && stack.getItem() instanceof ItemMagnetItem) {
			ItemMagnetItem.writeBlacklistToNBT(blacklist, playerIn.inventory.getCurrentItem());
		}
		super.onContainerClosed(playerIn);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
