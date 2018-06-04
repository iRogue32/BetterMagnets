package max.bettermagnets.items.ItemMagnet;

import java.util.List;

import max.bettermagnets.BetterMagnets;
import max.bettermagnets.CreativeTabBetterMagnets;
import max.bettermagnets.config.ConfigBetterMagnets;
import max.bettermagnets.handlers.BetterMagnetGuiHandler;
import max.bettermagnets.items.ModItemRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagnetItem extends Item {
	
	public static final int TIER_ONE_FILTER_SIZE = 3;
	public static final int TIER_TWO_FILTER_SIZE = 9;
	public static final int TIER_THREE_FILTER_SIZE = 15;
	
	private final int maxEnergy;
	private final int energyTransfer;
	
	public boolean isActive = false;
	public boolean isBlacklist = true;
	private int range;
	private static double velocity = ConfigBetterMagnets.velocity;
	private static int energyCost = ConfigBetterMagnets.energyCost;
	
	public int blacklistSize;
	
	public ItemMagnetItem(String name, int size, int range, int maxEnergy, int energyTransfer) {
		super();
		
		setUnlocalizedName(name);
		setRegistryName(name);
		
		blacklistSize = size;
		setMaxStackSize(1);
		this.range = range;
		this.maxEnergy = maxEnergy;
		this.energyTransfer = energyTransfer;
		
		setCreativeTab(BetterMagnets.CTBM);
		
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
		nbt.setBoolean("isOnlyOne", true);
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
		
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			tooltip.add(String.format("%s/%s Redstone Flux", storage.getEnergyStored(),storage.getMaxEnergyStored()));
		}
		
		tooltip.add("Range: " + range);
		if (!nbt.getBoolean("isOnlyOne")) {
			tooltip.add(TextFormatting.RED + "MAGNET NOT ACTIVE DUE TO HAVING MULTIPLE IN THE SAME INVENTORY");
		}
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
		boolean entityHasOnlyOne = false;
		if (entityIn instanceof EntityPlayer) {
			InventoryPlayer inventory = ((EntityPlayer) entityIn).inventory;
			entityHasOnlyOne = checkIfPlayerHasOnlyOneMagnet(inventory);
			nbt.setBoolean("isOnlyOne", entityHasOnlyOne);
		}
		if(isActive && entityHasOnlyOne) {
			boolean itemsMoved = false;
			if(entityIn instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entityIn;
				double X = player.getPosition().getX();
				double Y = player.getPosition().getY() + 1;
				double Z = player.getPosition().getZ();
				List<EntityItem> nearbyItems = player.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(player.getPosition().getX() - range, player.getPosition().getY() - range, player.getPosition().getZ() - range, player.getPosition().getX() + range, player.getPosition().getY() + range, player.getPosition().getZ() + range));
				for(EntityItem i : nearbyItems) {
					if (isBlacklist) {
						if(!isInFilter(i, stack)) {
							if (this.getEnergyStored(stack) >= this.energyCost) {
								i.addVelocity((X - i.posX) * velocity, (Y - i.posY) * velocity, (Z - i.posZ) * velocity);
								BetterMagnets.proxy.generateMagnetParticles(worldIn, i);
								itemsMoved = true;
							}
						}
					}
					else {
						if(isInFilter(i, stack)) {
							if (this.getEnergyStored(stack) >= this.energyCost) {
								i.addVelocity((X - i.posX) * velocity, (Y - i.posY) * velocity, (Z - i.posZ) * velocity);
								BetterMagnets.proxy.generateMagnetParticles(worldIn, i);
								itemsMoved = true;
							}
						}
					}
				}
			}
			if (itemsMoved && stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
				if (storage instanceof EnergyStorage) {
					((EnergyStorage) storage).extractEnergy(this.energyCost, false);
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
	
	private boolean checkIfPlayerHasOnlyOneMagnet(InventoryPlayer inv) {
		int count = 0;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			if (inv.getStackInSlot(i).getItem() instanceof ItemMagnetItem) {
				count++;
			}
		}
		if (count > 1) {
			return false;
		}
		else {
			return true;
		}
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
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		if (this.getDurabilityForDisplay(stack) == 0 || !ConfigBetterMagnets.requireEnergy) {
			return false;
		}
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                double maxEnergy = storage.getMaxEnergyStored();
                double energyMissing = maxEnergy - storage.getEnergyStored();
                return energyMissing / maxEnergy;
            }
        }
		return super.getDurabilityForDisplay(stack);
	}
	
	public int getEnergyStored(ItemStack stack) {
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage instanceof EnergyStorage) {
				return storage.getEnergyStored();
			}
		}
		return this.energyCost;
	}
	
	public void setStackEnergyMax(ItemStack stack) {
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage != null) {
				while (storage.getEnergyStored() < storage.getMaxEnergyStored()) {
					storage.receiveEnergy(storage.getMaxEnergyStored(), false);
				}
			}
		}
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			ItemStack stack = new ItemStack(this);
			if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
				this.setStackEnergyMax(stack);
				items.add(stack);
			}
			ItemStack stack1 = new ItemStack(this);
			items.add(stack1);
		}
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new EnergyCapabilityProvider(stack, this);
	}
	
	private class EnergyCapabilityProvider implements ICapabilityProvider {

		EnergyStorage storage;
		
		public EnergyCapabilityProvider(ItemStack stack, ItemMagnetItem item) {
			storage = new EnergyStorage(item.maxEnergy, item.energyTransfer) {
				@Override
				public int receiveEnergy(int maxReceive, boolean simulate) {
					if(!this.canReceive()){
			            return 0;
			        }
			        int energy = this.getEnergyStored();

			        int energyReceived = Math.min(this.capacity - this.getEnergyStored(), Math.min(this.maxReceive, maxReceive));
			        if(!simulate){
			            this.setEnergyStored(this.getEnergyStored() + energyReceived);
			        }
			        return energyReceived;
				}
				@Override
				public int extractEnergy(int maxExtract, boolean simulate) {
					 if (!canExtract()) {
				            return 0;
					 }
				     int energyExtracted = Math.min(this.getEnergyStored(), Math.min(this.maxExtract, maxExtract));
				     if (!simulate) {
				         this.setEnergyStored(this.getEnergyStored() - energyExtracted);
				     }
				     return energyExtracted;
				}
				@Override
				public int getEnergyStored() {
					if (stack.hasTagCompound()) {
						return stack.getTagCompound().getInteger("Energy");
					}
					else return 0;
				}
				public void setEnergyStored(int energy) {
					if (!stack.hasTagCompound()) {
						stack.setTagCompound(new NBTTagCompound());
					}
					stack.getTagCompound().setInteger("Energy", energy);
				}
			};
		}
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			if (capability == CapabilityEnergy.ENERGY && ConfigBetterMagnets.requireEnergy) {
				return true;
			}
			else return false;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (hasCapability(capability, facing)) {
				return CapabilityEnergy.ENERGY.cast(storage);
			}
			else {
				return null;
			}
		}
		
	}
	
}
