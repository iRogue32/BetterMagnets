package max.bettermagnets.items.ItemMagnet;

import java.io.IOException;

import max.bettermagnets.Reference;
import max.bettermagnets.network.PacketHandler;
import max.bettermagnets.network.PacketSendButtonPress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.client.gui.inventory.GuiDispenser;

public class GuiMagnetItem extends GuiContainer {

	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/blacklist1.png");
	private static final ResourceLocation BG_TEXTURE_2 = new ResourceLocation(Reference.MODID, "textures/gui/blacklist2.png");
	private static final ResourceLocation BG_TEXTURE_3 = new ResourceLocation(Reference.MODID, "textures/gui/blacklist3.png");
	
	private int size;
	private ItemMagnetItem magnet;
	private ItemStack stack;
	
	private GuiButton blacklistButton;
	private Container container;
	private int guiWidth = 176;
	private int guiHeight = 166;
	
	public GuiMagnetItem(InventoryPlayer inventory) {
		super(new ContainerMagnetItem(inventory));
		stack = inventory.getStackInSlot(inventory.currentItem);
		magnet = (ItemMagnetItem) inventory.getStackInSlot(inventory.currentItem).getItem();
		size = ((ItemMagnetItem) inventory.getStackInSlot(inventory.currentItem).getItem()).blacklistSize;
		container = inventory.player.openContainer;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		blacklistButton = new GuiButton(0, this.guiLeft - 21, this.guiTop + 8, 20, 20, ((ContainerMagnetItem) this.inventorySlots).isBlacklist ? "B" : "W" );
		this.buttonList.add(blacklistButton);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		boolean isBlacklist = ((ContainerMagnetItem)this.inventorySlots).isBlacklist;
		this.blacklistButton.displayString = isBlacklist ? "B" : "W";
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        if (this.blacklistButton.isMouseOver()) {
        	this.drawHoveringText("Filter Mode: " + (((ContainerMagnetItem) this.inventorySlots).isBlacklist ? "Blacklist" : "Whitelist"), mouseX, mouseY);
        }
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		if (size == magnet.TIER_ONE_FILTER_SIZE) {
			mc.getTextureManager().bindTexture(BG_TEXTURE);
		}
		else if (size == magnet.TIER_TWO_FILTER_SIZE) {
			mc.getTextureManager().bindTexture(BG_TEXTURE_2);
		}
		else if (size == magnet.TIER_THREE_FILTER_SIZE) {
			mc.getTextureManager().bindTexture(BG_TEXTURE_3);
		}
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		String s = "Item Magnet";
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
    }
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			int buttonID = button.id;
			EntityPlayer player = Minecraft.getMinecraft().player;
			PacketHandler.INSTANCE.sendToServer(new PacketSendButtonPress(buttonID, player));
		}
	}

}
