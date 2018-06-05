package max.bettermagnets.network;

import net.minecraft.entity.player.EntityPlayer;

public interface IHasButton {
	
	public void buttonPressed(int buttonId, EntityPlayer player);
	
}
