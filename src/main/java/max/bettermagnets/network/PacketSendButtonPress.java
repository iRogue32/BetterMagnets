package max.bettermagnets.network;

import com.jcraft.jogg.Packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendButtonPress implements IMessage {

	private int buttonID;
	private int worldID;
	private int playerID;
	
	public PacketSendButtonPress() {
		
	}
	
	public PacketSendButtonPress(int buttonID, EntityPlayer player) {
		this.buttonID = buttonID;
		this.worldID = player.getEntityWorld().provider.getDimension();
		this.playerID = player.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.buttonID = buf.readInt();
		this.worldID = buf.readInt();
		this.playerID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.buttonID);
		buf.writeInt(this.worldID);
		buf.writeInt(this.playerID);
	}
	
	public static class Handler implements IMessageHandler<PacketSendButtonPress, IMessage> {

		@Override
		public IMessage onMessage(PacketSendButtonPress message, MessageContext ctx) {
			FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(new Runnable() {

				@Override
				public void run() {
					handle(message, ctx);
				}
				
			});
			return null;
		}

		private void handle(PacketSendButtonPress message, MessageContext ctx) {
			Entity player = DimensionManager.getWorld(message.worldID).getEntityByID(message.playerID);
			if (player instanceof EntityPlayer) {
				Container container = ((EntityPlayer) player).openContainer;
				if (container instanceof IHasButton) {
					((IHasButton) container).buttonPressed(message.buttonID, (EntityPlayer) player); 
				}
			}
		}
		
	}

}
