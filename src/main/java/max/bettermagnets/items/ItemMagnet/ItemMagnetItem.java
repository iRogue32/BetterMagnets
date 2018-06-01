package max.bettermagnets.items.ItemMagnet;

import java.util.List;

import max.bettermagnets.BetterMagnets;
import max.bettermagnets.client.ParticleMagnet;
import max.bettermagnets.handlers.BetterMagnetGuiHandler;
import max.bettermagnets.items.ModItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagnetItem extends Item {
	
	public static final int TIER_ONE_FILTER_SIZE = 3;
	public static final int TIER_TWO_FILTER_SIZE = 9;
	public static final int TIER_THREE_FILTER_SIZE = 15;
	
	public boolean isActive = false;
	public boolean isBlacklist = true;
	private int range;
	private static double velocity = 0.03;
	
	public int blacklistSize;
	
	public ItemMagnetItem(String name, int size, int range) {
		super();
		
		setUnlocalizedName(name);
		setRegistryName(name);
		
		blacklistSize = size;
		setMaxStackSize(1);
		this.range = range;
		
		ModItemRegistry.items.add(this);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());	
		}
		NBTTagCompound nbt = stack.getTagCompound();
		nbt.setBoolean("isActive", false);
		nbt.setBoolean("isBlacklist", true);
		super.onCreated(stack, worldIn, playerIn);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = stack.getTagCompound();
		isActive = nbt.getBoolean("isActive");
		isBlacklist = nbt.getBoolean("isBlacklist");
		if(isActive) {
			tooltip.add("Active");
		}
		else {
			tooltip.add("Inactive");
		}
		if (isBlacklist) {
			tooltip.add("Mode: Blacklist");
		}
		else {
			tooltip.add("Mode: Whitelist");
		}
		tooltip.add("Range: " + range);
	}
	
	public static void writeBlacklistToNBT(ItemStackHandlerBlacklist blacklist, ItemStack stack) {
		NBTTagCompound compound = stack.getTagCompound();
		if(compound != null) {
			NBTTagList list = new NBTTagList();
			for(int i = 0; i < blacklist.getSlots(); ++i) {
				ItemStack stackInSlot = blacklist.getStackInSlot(i);
				NBTTagCompound compound1 = new NBTTagCompound();
				if(stackInSlot != ItemStack.EMPTY) {
					stackInSlot.writeToNBT(compound1);
				}
				list.appendTag(compound1);
			}
			compound.setTag("Blacklist", list);
		}
	}
	
	public static void readBlacklistFromNBT(ItemStackHandlerBlacklist blacklist, ItemStack stack) {
		NBTTagCompound compound = stack.getTagCompound();
		if(compound != null) {
			NBTTagList list = compound.getTagList("Blacklist", 10);
			for(int i = 0; i < blacklist.getSlots(); ++i) {
				NBTTagCompound compound1 = list.getCompoundTagAt(i);
				blacklist.setStackInSlot(i, compound1 != null && compound1.hasKey("id") ? new ItemStack(compound1) : ItemStack.EMPTY);
			}
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()) {
			ItemStack stack = player.getHeldItem(hand);
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null) {
				nbt = new NBTTagCompound();
			}
			if (nbt.hasKey("isBlacklist")) {
				nbt.setBoolean("isBlacklist", !nbt.getBoolean("isBlacklist"));
			}
			else {
				nbt.setBoolean("isBlacklist", true);
			}
		}
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(player.isSneaking()) {
			ItemStack stack = player.getHeldItem(hand);
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null) {
				nbt = new NBTTagCompound();
			}
			if (nbt.hasKey("isActive")) {
				nbt.setBoolean("isActive", !nbt.getBoolean("isActive"));
			}
			else {
				nbt.setBoolean("isActive", true);
			}
		}
		else {
			player.openGui(BetterMagnets.instance, BetterMagnetGuiHandler.ITEM_MAGNET, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound nbt1 = stack.getTagCompound();
			nbt1.setBoolean("isBlacklist", true);
		}
		NBTTagCompound nbt = stack.getTagCompound();
		isActive = nbt.getBoolean("isActive");
		isBlacklist = nbt.getBoolean("isBlacklist");
		if(isActive) {
			if(entityIn instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entityIn;
				double X = player.getPosition().getX();
				double Y = player.getPosition().getY() + 1;
				double Z = player.getPosition().getZ();
				List<EntityItem> nearbyItems = player.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(player.getPosition().getX() - range, player.getPosition().getY() - range, player.getPosition().getZ() - range, player.getPosition().getX() + range, player.getPosition().getY() + range, player.getPosition().getZ() + range));
				for(EntityItem i : nearbyItems) {
					if (isBlacklist) {
						if(!isInFilter(i, stack)) {
							i.addVelocity((X - i.posX) * velocity, (Y - i.posY) * velocity, (Z - i.posZ) * velocity);
							BetterMagnets.proxy.generateMagnetParticles(worldIn, i);
						}
					}
					else {
						if(isInFilter(i, stack)) {
							i.addVelocity((X - i.posX) * velocity, (Y - i.posY) * velocity, (Z - i.posZ) * velocity);
							BetterMagnets.proxy.generateMagnetParticles(worldIn, i);
						}
					}
				}
			}
		}
	}
	
	//filter helper function
	private boolean isInFilter(EntityItem item, ItemStack magnet) {
		ItemStack stack;
		NBTTagCompound compound = magnet.getTagCompound();
		if(compound != null) {
			NBTTagList list = compound.getTagList("Blacklist", 10);
			for(int i = 0; i < blacklistSize; ++i) {
				NBTTagCompound compound1 = list.getCompoundTagAt(i);
				if(compound1 != null && compound1.hasKey("id")) {
					stack = new ItemStack(compound1);
				}
				else {
					stack = ItemStack.EMPTY;
				}
				if (item.getItem().getItem() == stack.getItem() && item.getItem().getItemDamage() == stack.getItemDamage()) {
					return true;
				}
			}
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt.hasKey("isActive")) {
			isActive = nbt.getBoolean("isActive");
			if(isActive) {
				return true;
			}
		}
		return false;
	}
	

	
}
