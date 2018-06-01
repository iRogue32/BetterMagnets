package max.bettermagnets.handlers;

import max.bettermagnets.items.ItemMagnet.ContainerMagnetItem;
import max.bettermagnets.items.ItemMagnet.GuiMagnetItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class BetterMagnetGuiHandler implements IGuiHandler {

	public static final int ITEM_MAGNET = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case ITEM_MAGNET :
				return new ContainerMagnetItem(player.inventory);
		}
		return null;	
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case ITEM_MAGNET :
				return new GuiMagnetItem(player.inventory);
	}
		return null;
	}

}
