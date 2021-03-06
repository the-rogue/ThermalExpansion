package cofh.thermalexpansion.block.simple;

import cofh.lib.util.helpers.StringHelper;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockGlass extends ItemBlock {

	public ItemBlockGlass(Block block) {

		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		return StringHelper.localize(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		if (stack.getItemDamage() == 1) {
			return "tile.thermalexpansion.glassLumium.name";
		}
		return "tile.thermalexpansion.glass.name";
	}

	@Override
	public int getMetadata(int i) {

		return i;
	}

}
