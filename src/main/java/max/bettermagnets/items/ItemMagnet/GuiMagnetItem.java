package max.bettermagnets.items.ItemMagnet;

import max.bettermagnets.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.client.gui.inventory.GuiDispenser;

public class GuiMagnetItem extends GuiContainer {

	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/blacklist1.png");
	private static final ResourceLocation BG_TEXTURE_2 = new ResourceLocation(Reference.MODID, "textures/gui/blacklist2.png");
	private static final ResourceLocation BG_TEXTURE_3 = new ResourceLocation(Reference.MODID, "textures/gui/blacklist3.png");
	
	private int size;
	private ItemMagnetItem magnet;
	
	private int guiWidth = 176;
	private int guiHeight = 166;
	
	public GuiMagnetItem(InventoryPlayer inventory) {
		super(new ContainerMagnetItem(inventory));
		magnet = (ItemMagnetItem) inventory.getStackInSlot(inventory.currentItem).getItem();
		size = ((ItemMagnetItem) inventory.getStackInSlot(inventory.currentItem).getItem()).blacklistSize;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int guiXPos = (width - guiWidth)/ 2;
		int guiYPos = (height - guiHeight) / 2;
		this.buttonList.add(new GuiButton(0, guiXPos + 6, guiYPos + 16, 20, 20, "B"));
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
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

}
