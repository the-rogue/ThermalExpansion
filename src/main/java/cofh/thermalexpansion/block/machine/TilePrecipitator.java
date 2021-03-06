package cofh.thermalexpansion.block.machine;

import cofh.api.core.ICustomInventory;
import cofh.core.network.PacketCoFHBase;
import cofh.core.util.fluid.FluidTankAdv;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalexpansion.block.machine.BlockMachine.Types;
import cofh.thermalexpansion.core.TEProps;
import cofh.thermalexpansion.gui.client.machine.GuiPrecipitator;
import cofh.thermalexpansion.gui.container.machine.ContainerPrecipitator;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TilePrecipitator extends TileMachineBase implements ICustomInventory, IFluidHandler {

	public static void initialize() {

		int type = BlockMachine.Types.PRECIPITATOR.ordinal();

		processItems[0] = new ItemStack(Items.snowball, 4, 0);
		processItems[1] = new ItemStack(Blocks.snow);
		processItems[2] = new ItemStack(Blocks.ice);

		defaultSideConfig[type] = new SideConfig();
		defaultSideConfig[type].numConfig = 4;
		defaultSideConfig[type].slotGroups = new int[][] { {}, {}, { 0 }, { 0 } };
		defaultSideConfig[type].allowInsertionSide = new boolean[] { false, true, false, true };
		defaultSideConfig[type].allowExtractionSide = new boolean[] { false, false, true, true };
		defaultSideConfig[type].allowInsertionSlot = new boolean[] { false, false };
		defaultSideConfig[type].allowExtractionSlot = new boolean[] { true, false };
		defaultSideConfig[type].sideTex = new int[] { 0, 1, 4, 7 };
		defaultSideConfig[type].defaultSides = new byte[] { 1, 1, 2, 2, 2, 2 };

		String category = "Machine.Precipitator";
		int basePower = MathHelper.clamp(ThermalExpansion.config.get(category, "BasePower", 20), 10, 500);
		ThermalExpansion.config.set(category, "BasePower", basePower);
		defaultEnergyConfig[type] = new EnergyConfig();
		defaultEnergyConfig[type].setParamsPower(basePower);

		GameRegistry.registerTileEntity(TilePrecipitator.class, "thermalexpansion.Precipitator");
	}

	static int[] processWater = { 500, 500, 1000 };
	static int[] processEnergy = { 800, 800, 1600 };
	static ItemStack[] processItems = new ItemStack[3];

	int outputTracker;
	byte curSelection;
	byte prevSelection;
	FluidStack renderFluid = new FluidStack(FluidRegistry.WATER, 0);
	FluidTankAdv tank = new FluidTankAdv(TEProps.MAX_FLUID_SMALL);

	public TilePrecipitator() {

		super(Types.PRECIPITATOR);
		inventory = new ItemStack[1 + 1];
	}

	@Override
	protected boolean canStart() {

		if (energyStorage.getEnergyStored() < processEnergy[curSelection] || tank.getFluidAmount() < processWater[curSelection]) {
			return false;
		}
		if (inventory[0] == null) {
			return true;
		}
		if (!inventory[0].isItemEqual(processItems[curSelection])) {
			return false;
		}
		return inventory[0].stackSize + processItems[curSelection].stackSize <= processItems[prevSelection].getMaxStackSize();
	}

	@Override
	protected boolean canFinish() {

		return processRem <= 0;
	}

	@Override
	protected void processStart() {

		processMax = processEnergy[curSelection];
		processRem = processMax;
		prevSelection = curSelection;
	}

	@Override
	protected void processFinish() {

		if (inventory[0] == null) {
			inventory[0] = processItems[prevSelection].copy();
		} else {
			inventory[0].stackSize += processItems[prevSelection].stackSize;
		}
		tank.drain(processWater[prevSelection], true);
		prevSelection = curSelection;
	}

	@Override
	protected void transferOutput() {

		if (!augmentAutoOutput) {
			return;
		}
		if (inventory[0] == null) {
			return;
		}
		int side;
		for (int i = outputTracker + 1; i <= outputTracker + 6; i++) {
			side = i % 6;

			if (sideCache[side] == 2) {
				if (transferItem(0, AUTO_TRANSFER[level], side)) {
					outputTracker = side;
					break;
				}
			}
		}
	}

	@Override
	protected void onLevelChange() {

		super.onLevelChange();

		tank.setCapacity(TEProps.MAX_FLUID_SMALL * FLUID_CAPACITY[level]);
	}

	@Override
	protected boolean readPortableTagInternal(EntityPlayer player, NBTTagCompound tag) {

		if (!super.readPortableTagInternal(player, tag)) {
			return false;
		}
		if (tag.hasKey("Sel")) {
			curSelection = tag.getByte("Sel");
			if (!isActive) {
				prevSelection = curSelection;
			}
		}
		return true;
	}

	@Override
	protected boolean writePortableTagInternal(EntityPlayer player, NBTTagCompound tag) {

		if (!super.writePortableTagInternal(player, tag)) {
			return false;
		}
		tag.setByte("Sel", curSelection);
		return true;
	}

	/* GUI METHODS */
	@Override
	public Object getGuiClient(InventoryPlayer inventory) {

		return new GuiPrecipitator(inventory, this);
	}

	@Override
	public Object getGuiServer(InventoryPlayer inventory) {

		return new ContainerPrecipitator(inventory, this);
	}

	@Override
	public FluidTankAdv getTank() {

		return tank;
	}

	@Override
	public FluidStack getTankFluid() {

		return tank.getFluid();
	}

	public int getCurSelection() {

		return curSelection;
	}

	public int getPrevSelection() {

		return prevSelection;
	}

	/* NBT METHODS */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);

		outputTracker = nbt.getInteger("Tracker");
		prevSelection = nbt.getByte("Prev");
		curSelection = nbt.getByte("Sel");

		tank.readFromNBT(nbt);

		if (tank.getFluid() != null) {
			renderFluid = tank.getFluid();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);

		nbt.setInteger("Tracker", outputTracker);
		nbt.setByte("Prev", prevSelection);
		nbt.setByte("Sel", curSelection);
		tank.writeToNBT(nbt);
	}

	/* NETWORK METHODS */
	@Override
	public PacketCoFHBase getPacket() {

		PacketCoFHBase payload = super.getPacket();

		payload.addFluidStack(renderFluid);
		return payload;
	}

	@Override
	public PacketCoFHBase getGuiPacket() {

		PacketCoFHBase payload = super.getGuiPacket();

		payload.addByte(curSelection);
		payload.addByte(prevSelection);

		if (tank.getFluid() == null) {
			payload.addFluidStack(renderFluid);
		} else {
			payload.addFluidStack(tank.getFluid());
		}
		return payload;
	}

	@Override
	public PacketCoFHBase getFluidPacket() {

		PacketCoFHBase payload = super.getFluidPacket();

		payload.addFluidStack(renderFluid);

		return payload;
	}

	@Override
	public PacketCoFHBase getModePacket() {

		PacketCoFHBase payload = super.getModePacket();

		payload.addByte(curSelection);

		return payload;
	}

	@Override
	protected void handleGuiPacket(PacketCoFHBase payload) {

		super.handleGuiPacket(payload);

		curSelection = payload.getByte();
		prevSelection = payload.getByte();
		tank.setFluid(payload.getFluidStack());
	}

	@Override
	protected void handleFluidPacket(PacketCoFHBase payload) {

		super.handleFluidPacket(payload);

		renderFluid = payload.getFluidStack();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	protected void handleModePacket(PacketCoFHBase payload) {

		super.handleModePacket(payload);

		curSelection = payload.getByte();
		if (!isActive) {
			prevSelection = curSelection;
		}
	}

	public void setMode(int i) {

		byte lastSelection = curSelection;
		curSelection = (byte) i;
		sendModePacket();
		curSelection = lastSelection;
	}

	/* ITilePacketHandler */
	@Override
	public void handleTilePacket(PacketCoFHBase payload, boolean isServer) {

		super.handleTilePacket(payload, isServer);

		if (!isServer) {
			renderFluid = payload.getFluidStack();
		} else {
			payload.getFluidStack();
		}
	}

	/* ICustomInventory */
	@Override
	public ItemStack[] getInventorySlots(int inventoryIndex) {

		return processItems;
	}

	@Override
	public int getSlotStackLimit(int slotIndex) {

		return 64;
	}

	@Override
	public void onSlotUpdate() {

		markDirty();
	}

	/* IFluidHandler */
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

		if (from != ForgeDirection.UNKNOWN && sideCache[from.ordinal()] != 1) {
			return 0;
		}
		if (resource.getFluid() != FluidRegistry.WATER) {
			return 0;
		}
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {

		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {

		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {

		return new FluidTankInfo[] { tank.getInfo() };
	}

}
