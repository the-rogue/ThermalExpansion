package cofh.thermalexpansion.util;

import buildcraft.api.tools.IToolWrench;
import buildcraft.api.transport.IPipeTile;

import cofh.api.item.IAugmentItem;
import cofh.api.item.IToolHammer;
import cofh.api.transport.IItemDuct;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.InventoryHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Utils {

	/* ITEM FUNCTIONS */
	public static boolean isAugmentItem(ItemStack container) {

		return container != null && container.getItem() instanceof IAugmentItem;
	}

	/* TILE FUNCTIONS - INSERTION */
	public static int addToAdjacentInsertion(TileEntity tile, int from, ItemStack stack) {

		return addToAdjacentInsertion(tile.xCoord, tile.yCoord, tile.zCoord, tile.getWorldObj(), from, stack);
	}

	public static int addToAdjacentInsertion(int x, int y, int z, World worldObj, int from, ItemStack stack) {

		TileEntity theTile = BlockHelper.getAdjacentTileEntity(worldObj, x, y, z, from);

		if (!InventoryHelper.isInsertion(theTile)) {
			return stack.stackSize;
		}
		stack = InventoryHelper.addToInsertion(theTile, from, stack);
		return stack == null ? 0 : stack.stackSize;
	}

	public static int addToInsertion(TileEntity theTile, int from, ItemStack stack) {

		if (!(InventoryHelper.isInsertion(theTile))) {
			return stack.stackSize;
		}
		stack = InventoryHelper.addToInsertion(theTile, from, stack);

		return stack == null ? 0 : stack.stackSize;
	}

	public static int addToInsertion(int xCoord, int yCoord, int zCoord, World worldObj, int from, ItemStack stack) {

		TileEntity theTile = worldObj.getTileEntity(xCoord, yCoord, zCoord);

		if (!InventoryHelper.isInsertion(theTile)) {
			return stack.stackSize;
		}
		stack = InventoryHelper.addToInsertion(theTile, from, stack);

		return stack == null ? 0 : stack.stackSize;
	}

	public static int addToInsertion(IInventory tile, int from, ItemStack stack) {

		if (!InventoryHelper.isInsertion(tile)) {
			return stack.stackSize;
		}
		stack = InventoryHelper.addToInsertion(tile, from, stack);

		return stack == null ? 0 : stack.stackSize;
	}

	public static int canAddToInventory(int xCoord, int yCoord, int zCoord, World worldObj, int from, ItemStack stack) {

		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord, zCoord);

		if (!InventoryHelper.isInventory(tile)) {
			return stack.stackSize;
		}
		stack = InventoryHelper.simulateInsertItemStackIntoInventory((IInventory) tile, stack, from ^ 1);

		return stack == null ? 0 : stack.stackSize;
	}

	public static int addToPipeTile(TileEntity theTile, int side, ItemStack stack) {

		if (bcPipeExists) {
			return addToPipeTile_do(theTile, side, stack);
		}
		return 0;
	}

	private static int addToPipeTile_do(TileEntity tile, int side, ItemStack stack) {

		if (tile instanceof IPipeTile) {
			@SuppressWarnings("deprecation")
			int used = ((IPipeTile) tile).injectItem(stack, true, ForgeDirection.VALID_DIRECTIONS[side ^ 1]);
			return used;
		}
		return 0;
	}

	/* TILE FUNCTIONS - EXTRACTION */
	// public static ItemStack extractFromAdjacentInventoryIntoSlot(TileEntity tile, int from, int slot, int amount) {
	//
	// IInventory theInv = (IInventory) tile;
	// TileEntity theTile = BlockHelper.getAdjacentTileEntity(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, from);
	// ItemStack stack = theInv.getStackInSlot(slot);
	//
	// if (!InventoryHelper.isInventory(theTile)) {
	// return stack;
	// }
	// stack = InventoryHelper.addToInsertion(theTile, from, stack);
	// return stack == null ? 0 : stack.stackSize;
	// }

	/* QUERY FUNCTIONS */
	public static boolean isAdjacentInput(TileEntity tile, int side) {

		return isAdjacentInput(tile.xCoord, tile.yCoord, tile.zCoord, tile.getWorldObj(), side);
	}

	public static boolean isAdjacentInput(int x, int y, int z, World worldObj, int side) {

		TileEntity tile = BlockHelper.getAdjacentTileEntity(worldObj, x, y, z, side);

		return isAccessibleInput(tile, side);
	}

	public static boolean isAdjacentOutput(TileEntity tile, int side) {

		return isAdjacentOutput(tile.xCoord, tile.yCoord, tile.zCoord, tile.getWorldObj(), side);
	}

	public static boolean isAdjacentOutput(int x, int y, int z, World worldObj, int side) {

		TileEntity tile = BlockHelper.getAdjacentTileEntity(worldObj, x, y, z, side);

		return isAccessibleOutput(tile, side);
	}

	public static boolean isAccessibleInput(TileEntity tile, int side) {

		if (tile instanceof ISidedInventory && ((ISidedInventory) tile).getAccessibleSlotsFromSide(BlockHelper.SIDE_OPPOSITE[side]).length <= 0) {
			return false;
		}
		if (tile instanceof IInventory && ((IInventory) tile).getSizeInventory() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isAccessibleOutput(TileEntity tile, int side) {

		if (tile instanceof ISidedInventory && ((ISidedInventory) tile).getAccessibleSlotsFromSide(BlockHelper.SIDE_OPPOSITE[side]).length <= 0) {
			return false;
		}
		if (tile instanceof IInventory && ((IInventory) tile).getSizeInventory() > 0) {
			return true;
		}
		if (tile instanceof IItemDuct) {
			return true;
		}
		return false;
	}

	public static boolean isHoldingBlock(EntityPlayer player) {

		Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;
		return equipped instanceof ItemBlock;
	}

	public static boolean isHoldingUsableWrench(EntityPlayer player, int x, int y, int z) {

		Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;
		if (equipped instanceof IToolHammer) {
			return ((IToolHammer) equipped).isUsable(player.getCurrentEquippedItem(), player, x, y, z);
		} else if (bcWrenchExists) {
			return canHandleBCWrench(equipped, player, x, y, z);
		}
		return false;
	}

	public static void usedWrench(EntityPlayer player, int x, int y, int z) {

		Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;
		if (equipped instanceof IToolHammer) {
			((IToolHammer) equipped).toolUsed(player.getCurrentEquippedItem(), player, x, y, z);
		} else if (bcWrenchExists) {
			bcWrenchUsed(equipped, player, x, y, z);
		}
	}

	// BCHelper {
	private static boolean bcWrenchExists = false;
	private static boolean bcPipeExists = false;

	static {
		try {
			Class.forName("buildcraft.api.tools.IToolWrench");
			bcWrenchExists = true;
		} catch (Throwable t) {
			// pokemon!
		}
		try {
			Class.forName("buildcraft.api.transport.IPipeTile");
			bcPipeExists = true;
		} catch (Throwable t) {
			// pokemon!
		}
	}

	private static boolean canHandleBCWrench(Item item, EntityPlayer p, int x, int y, int z) {

		return item instanceof IToolWrench && ((IToolWrench) item).canWrench(p, x, y, z);
	}

	private static void bcWrenchUsed(Item item, EntityPlayer p, int x, int y, int z) {

		if (item instanceof IToolWrench) {
			((IToolWrench) item).wrenchUsed(p, x, y, z);
		}
	}

	public static boolean isPipeTile(TileEntity tile) {

		return bcPipeExists && isPipeTile_do(tile);
	}

	private static boolean isPipeTile_do(TileEntity tile) {

		return tile instanceof IPipeTile;
	}

	// }

}
