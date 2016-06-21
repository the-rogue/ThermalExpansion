package cofh.thermalexpansion.block.device;

import cofh.core.CoFHProps;
import cofh.lib.util.helpers.FluidHelper;
import cofh.thermalexpansion.gui.client.device.GuiNullifier;
import cofh.thermalexpansion.gui.container.device.ContainerNullifier;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class TileNullifier extends TileDeviceBase implements IFluidHandler {

	public static void initialize() {

		int type = BlockDevice.Type.NULLIFIER.ordinal();

		DEFAULT_SIDE_CONFIG[type] = new SideConfig();
		DEFAULT_SIDE_CONFIG[type].numConfig = 2;
		DEFAULT_SIDE_CONFIG[type].slotGroups = new int[][] { {}, { 0 }, {} };
		DEFAULT_SIDE_CONFIG[type].allowInsertionSide = new boolean[] { false, false, false };
		DEFAULT_SIDE_CONFIG[type].allowExtractionSide = new boolean[] { false, false, false };
		DEFAULT_SIDE_CONFIG[type].allowInsertionSlot = new boolean[] { true };
		DEFAULT_SIDE_CONFIG[type].allowExtractionSlot = new boolean[] { false };
		DEFAULT_SIDE_CONFIG[type].sideTex = new int[] { 0, 1, 4 };
		DEFAULT_SIDE_CONFIG[type].defaultSides = new byte[] { 0, 0, 0, 0, 0, 0 };

		GameRegistry.registerTileEntity(TileNullifier.class, "thermalexpansion.deviceNullifier");
	}

	protected static final int[] SLOTS = { 0 };
	protected static final Fluid renderFluid = FluidRegistry.LAVA;

	public TileNullifier() {

		super(BlockDevice.Type.NULLIFIER);
		inventory = new ItemStack[1];
	}

	@Override
	public void setDefaultSides() {

		sideCache = getDefaultSides();
		sideCache[facing] = 1;
	}

	@Override
	public int getLightValue() {

		return FluidHelper.getFluidLuminosity(renderFluid);
	}

	@Override
	public boolean sendRedstoneUpdates() {

		return true;
	}

	protected boolean isSideAccessible(EnumFacing side) {

		return side != null && sideCache[side.ordinal()] == 1 && redstoneControlOrDisable();
	}

	/* GUI METHODS */
	@Override
	public Object getGuiClient(InventoryPlayer inventory) {

		return new GuiNullifier(inventory, this);
	}

	@Override
	public Object getGuiServer(InventoryPlayer inventory) {

		return new ContainerNullifier(inventory, this);
	}

	/* NBT METHODS */
	@Override
	public void readInventoryFromNBT(NBTTagCompound nbt) {

	}

	@Override
	public void writeInventoryToNBT(NBTTagCompound nbt) {

	}

	/* IFluidHandler */
	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {

		return isSideAccessible(from) ? resource.amount : 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {

		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {

		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {

		return true;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {

		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {

		return null;
	}

	/* IInventory */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

		if (index == 0) {
			return;
		}
		inventory[index] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	/* IReconfigurableFacing */
	@Override
	public boolean setFacing(EnumFacing side) {

		int sideInt = side.ordinal();

		if (sideInt < 0 || sideInt > 5) {
			return false;
		}
		facing = (byte) sideInt;
		sideCache[facing] = 1;
		markDirty();
		sendUpdatePacket(Side.CLIENT);
		return true;
	}

	/* ISidedInventory */
	@Override
	public int[] getSlotsForFace(EnumFacing side) {

		return isSideAccessible(side) ? SLOTS : CoFHProps.EMPTY_INVENTORY;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {

		return isSideAccessible(side);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side) {

		return false;
	}

}
