package max.bettermagnets.network;

import java.util.ArrayList;
import java.util.List;

import max.bettermagnets.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	
	private static int packetID = 0;
	
	public static SimpleNetworkWrapper INSTANCE = null;
	
	public PacketHandler() {
		
	}
	
	public static void nextID() {
		packetID++;
	}
	
	public static void init() {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
		INSTANCE.registerMessage(PacketSendButtonPress.Handler.class, PacketSendButtonPress.class, 0, Side.SERVER);
	}
	
}
