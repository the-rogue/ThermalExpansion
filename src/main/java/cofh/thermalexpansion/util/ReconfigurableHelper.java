package cofh.thermalexpansion.util;

import cofh.thermalexpansion.block.TileReconfigurable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ReconfigurableHelper {

	public static final byte DEFAULT_FACING = 3;
	public static final byte[] DEFAULT_SIDES = new byte[] { 0, 0, 0, 0, 0, 0 };

	private ReconfigurableHelper() {

	}

	/* NBT TAG HELPERS */
	public static NBTTagCompound setItemStackTagReconfig(NBTTagCompound tag, TileReconfigurable tile) {

		if (tile == null) {
			return null;
		}
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setByte("Facing", (byte) tile.getFacing());
		tag.setByteArray("SideCache", tile.sideCache);
		return tag;
	}

	public static byte getFacingFromNBT(NBTTagCompound nbt) {

		return !nbt.hasKey("Facing") ? DEFAULT_FACING : nbt.getByte("Facing");
	}

	public static byte[] getSideCacheFromNBT(NBTTagCompound tag, byte[] defaultSides) {

		if (tag == null) {
			return defaultSides.clone();
		}
		byte[] retSides = tag.getByteArray("SideCache");
		return retSides.length < 6 ? defaultSides.clone() : retSides;
	}

	/* ITEM HELPERS */
	public static boolean hasReconfigInfo(ItemStack stack) {

		return !stack.hasTagCompound() ? false : stack.getTagCompound().hasKey("Facing") && stack.getTagCompound().hasKey("SideCache");
	}

	public static boolean setFacing(ItemStack stack, int facing) {

		if (facing < 0 || facing > 5) {
			return false;
		}
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setByte("Facing", (byte) facing);
		return true;
	}

	public static boolean setSideCache(ItemStack stack, byte[] sideCache) {

		if (sideCache.length < 6) {
			return false;
		}
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setByteArray("SideCache", sideCache);
		return true;
	}

	public static byte getFacing(ItemStack stack) {

		return !stack.hasTagCompound() || !stack.getTagCompound().hasKey("Facing") ? DEFAULT_FACING : stack.getTagCompound().getByte("Facing");
	}

	public static byte[] getSideCache(ItemStack stack) {

		if (!stack.hasTagCompound()) {
			return DEFAULT_SIDES.clone();
		}
		byte[] retSides = stack.getTagCompound().getByteArray("SideCache");
		return retSides.length < 6 ? DEFAULT_SIDES.clone() : retSides;
	}

	public static byte[] getSideCache(ItemStack stack, byte[] defaultSides) {

		if (!stack.hasTagCompound()) {
			return defaultSides.clone();
		}
		byte[] retSides = stack.getTagCompound().getByteArray("SideCache");
		return retSides.length < 6 ? defaultSides.clone() : retSides;
	}

}
