package cofh.thermalexpansion.api.crafting.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ICentrifugeRecipe {

	ItemStack getInput();

	List<ItemStack> getPrimaryOutputs();

	FluidStack getSecondaryOutput();

	int getEnergy();
}
