package max.bettermagnets.items;

import java.util.ArrayList;
import java.util.List;

import max.bettermagnets.config.ConfigBetterMagnets;
import max.bettermagnets.items.ItemMagnet.ItemMagnetItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItemRegistry {

	public static List<Item> items = new ArrayList<Item>();
	
	public static ItemMagnetItem itemMagnet1;
	public static ItemMagnetItem itemMagnet2;
	public static ItemMagnetItem itemMagnet3;
	
	public static void initItems() {
		itemMagnet1 = new ItemMagnetItem("item_magnet", ItemMagnetItem.TIER_ONE_FILTER_SIZE, ConfigBetterMagnets.tier1Range, ConfigBetterMagnets.tier1EnergyStorage, ConfigBetterMagnets.tier1EnergyTransfer);
		itemMagnet2 = new ItemMagnetItem("item_magnet_2", ItemMagnetItem.TIER_TWO_FILTER_SIZE, ConfigBetterMagnets.tier2Range, ConfigBetterMagnets.tier2EnergyStorage, ConfigBetterMagnets.tier2EnergyTransfer);
		itemMagnet3 = new ItemMagnetItem("item_magnet_3", ItemMagnetItem.TIER_THREE_FILTER_SIZE, ConfigBetterMagnets.tier3Range, ConfigBetterMagnets.tier3EnergyStorage, ConfigBetterMagnets.tier3EnergyTransfer);
	}
	
	@SubscribeEvent
	public void onItemRegistry(RegistryEvent.Register<Item> e) {
		IForgeRegistry<Item> reg = e.getRegistry();
		reg.registerAll(items.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public void onModelRegister(ModelRegistryEvent e) {
		for (Item item : items) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	
}
