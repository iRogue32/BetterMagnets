package max.bettermagnets.items.ItemMagnet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotBlacklist extends SlotItemHandler {

	public SlotBlacklist(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	public void slotClicked(EntityPlayer player) {
		ItemStack playerStack = player.inventory.getItemStack();
		ItemStack stackInBlacklistSlot = getStack();
		if(stackInBlacklistSlot != ItemStack.EMPTY && playerStack == ItemStack.EMPTY) {
			this.putStack(ItemStack.EMPTY);
		}
		else if(playerStack != ItemStack.EMPTY) {
			ItemStack stack = playerStack.copy();
			stack.setCount(1);
			this.putStack(stack);
		}
	}
	
	@Override
	public void putStack(ItemStack stack) {
		ItemStack copyStack = stack != ItemStack.EMPTY ? stack.copy() : ItemStack.EMPTY;
		super.putStack(copyStack);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer playerIn) {
		return false;
	}
	
	

}
