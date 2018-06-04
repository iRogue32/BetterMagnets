package max.bettermagnets;

import max.bettermagnets.items.ModItemRegistry;
import max.bettermagnets.items.ItemMagnet.ItemMagnetItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabBetterMagnets extends CreativeTabs {

	public CreativeTabBetterMagnets(String label) {
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() {
		ItemStack stack = new ItemStack(ModItemRegistry.itemMagnet1);
		ModItemRegistry.itemMagnet1.setStackEnergyMax(stack);
		return stack;
	}

}
