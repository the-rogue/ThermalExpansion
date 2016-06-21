package cofh.thermalexpansion.api.crafting;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ICentrifugeHandler {

	public boolean addRecipe(int energy, ItemStack input, List<ItemStack> primaryOutputs, FluidStack secondaryOutput, boolean overwrite);

	public boolean removeRecipe(ItemStack input);

}
