package cofh.thermalexpansion.item;

import cofh.api.core.IInitializer;
import cofh.core.item.ItemCoFHBase;
import cofh.thermalexpansion.ThermalExpansion;

import net.minecraft.item.ItemStack;

public class ItemAugment extends ItemCoFHBase implements IInitializer {

	public ItemAugment() {

		super("thermalexpansion");

		setUnlocalizedName("augment");
		setCreativeTab(ThermalExpansion.tabItems);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		return true;
	}

	/* REFERENCES */
	public static ItemStack generalAutoOutput;
	public static ItemStack generalAutoInput;
	public static ItemStack generalReconfigSides;
	public static ItemStack generalRedstoneControl;

	public static ItemStack dynamoCoilDuct;
	public static ItemStack dynamoThrottle;

	public static ItemStack machineNull;
	public static ItemStack machineFurnaceFood;

}
