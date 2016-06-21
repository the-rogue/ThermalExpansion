package cofh.thermalexpansion.util;

import cofh.thermalexpansion.api.crafting.CraftingHandlers;
import cofh.thermalexpansion.api.crafting.ICentrifugeHandler;
import cofh.thermalexpansion.api.crafting.IChargerHandler;
import cofh.thermalexpansion.api.crafting.ICrucibleHandler;
import cofh.thermalexpansion.api.crafting.IFurnaceHandler;
import cofh.thermalexpansion.api.crafting.IInsolatorHandler;
import cofh.thermalexpansion.api.crafting.IPulverizerHandler;
import cofh.thermalexpansion.api.crafting.ISawmillHandler;
import cofh.thermalexpansion.api.crafting.ISmelterHandler;
import cofh.thermalexpansion.api.crafting.ITransposerHandler;
import cofh.thermalexpansion.api.fuels.ICompressionHandler;
import cofh.thermalexpansion.api.fuels.IEnervationHandler;
import cofh.thermalexpansion.api.fuels.IMagmaticHandler;
import cofh.thermalexpansion.api.fuels.IReactantHandler;
import cofh.thermalexpansion.api.fuels.ISteamHandler;
import cofh.thermalexpansion.util.crafting.CentrifugeManager;
import cofh.thermalexpansion.util.crafting.ChargerManager;
import cofh.thermalexpansion.util.crafting.CrucibleManager;
import cofh.thermalexpansion.util.crafting.FurnaceManager;
import cofh.thermalexpansion.util.crafting.InsolatorManager;
import cofh.thermalexpansion.util.crafting.PulverizerManager;
import cofh.thermalexpansion.util.crafting.SawmillManager;
import cofh.thermalexpansion.util.crafting.SmelterManager;
import cofh.thermalexpansion.util.crafting.TransposerManager;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * This basically is the manager for the "unsafe" API interactions. It initializes external handles and maintains the Handler objects.
 *
 * @author King Lemming
 *
 */
public class APIWarden {

	private APIWarden() {

	}

	static {
		CraftingHandlers.furnace = new FurnaceHandler();
		CraftingHandlers.pulverizer = new PulverizerHandler();
		CraftingHandlers.sawmill = new SawmillHandler();
		CraftingHandlers.smelter = new SmelterHandler();
		CraftingHandlers.insolator = new InsolatorHandler();
		CraftingHandlers.charger = new ChargerHandler();
		CraftingHandlers.crucible = new CrucibleHandler();
		CraftingHandlers.transposer = new TransposerHandler();
		CraftingHandlers.centrifuge = new CentrifugeHandler();
	}

	/** MACHINES */

	/* FURNACE */
	public static class FurnaceHandler implements IFurnaceHandler {

		@Override
		public boolean addRecipe(int energy, ItemStack input, ItemStack output, boolean overwrite) {

			return FurnaceManager.addRecipe(energy, input, output, overwrite);
		}

		@Override
		public boolean removeRecipe(ItemStack input) {

			return FurnaceManager.removeRecipe(input);
		}
	}

	/* PULVERIZER */
	public static class PulverizerHandler implements IPulverizerHandler {

		@Override
		public boolean addRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance, boolean overwrite) {

			return PulverizerManager.addRecipe(energy, input, primaryOutput, secondaryOutput, secondaryChance, overwrite);
		}

		@Override
		public boolean removeRecipe(ItemStack input) {

			return PulverizerManager.removeRecipe(input);
		}
	}

	/* SAWMILL */
	public static class SawmillHandler implements ISawmillHandler {

		@Override
		public boolean addRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance, boolean overwrite) {

			return SawmillManager.addRecipe(energy, input, primaryOutput, secondaryOutput, secondaryChance, overwrite);
		}

		@Override
		public boolean removeRecipe(ItemStack input) {

			return SawmillManager.removeRecipe(input);
		}
	}

	/* SMELTER */
	public static class SmelterHandler implements ISmelterHandler {

		@Override
		public boolean addRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput,
				int secondaryChance, boolean overwrite) {

			return SmelterManager.addRecipe(energy, primaryInput, secondaryInput, primaryOutput, secondaryOutput, secondaryChance, overwrite);
		}

		@Override
		public boolean removeRecipe(ItemStack primaryInput, ItemStack secondaryInput) {

			return SmelterManager.removeRecipe(primaryInput, secondaryInput);
		}
	}

	/* INSOLATOR */
	public static class InsolatorHandler implements IInsolatorHandler {

		@Override
		public boolean addRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput,
				int secondaryChance, boolean overwrite) {

			return InsolatorManager.addRecipe(energy, primaryInput, secondaryInput, primaryOutput, secondaryOutput, secondaryChance, overwrite);
		}

		@Override
		public boolean removeRecipe(ItemStack primaryInput, ItemStack secondaryInput) {

			return InsolatorManager.removeRecipe(primaryInput, secondaryInput);
		}
	}

	/* CHARGER */
	public static class ChargerHandler implements IChargerHandler {

		@Override
		public boolean addRecipe(int energy, ItemStack input, ItemStack output, boolean overwrite) {

			return ChargerManager.addRecipe(energy, input, output, overwrite);
		}

		@Override
		public boolean removeRecipe(ItemStack input) {

			return ChargerManager.removeRecipe(input);
		}
	}

	/* CRUCIBLE */
	public static class CrucibleHandler implements ICrucibleHandler {

		@Override
		public boolean addRecipe(int energy, ItemStack input, FluidStack output, boolean overwrite) {

			return CrucibleManager.addRecipe(energy, input, output, overwrite);
		}

		@Override
		public boolean removeRecipe(ItemStack input) {

			return CrucibleManager.removeRecipe(input);
		}
	}

	/* TRANSPOSER */
	public static class TransposerHandler implements ITransposerHandler {

		@Override
		public boolean addFillRecipe(int energy, ItemStack input, ItemStack output, FluidStack fluid, boolean reversible, boolean overwrite) {

			return TransposerManager.addFillRecipe(energy, input, output, fluid, reversible, overwrite);
		}

		@Override
		public boolean addExtractionRecipe(int energy, ItemStack input, ItemStack output, FluidStack fluid, int chance, boolean reversible, boolean overwrite) {

			return TransposerManager.addExtractionRecipe(energy, input, output, fluid, chance, reversible, overwrite);
		}

		@Override
		public boolean removeFillRecipe(ItemStack input, FluidStack fluid) {

			return TransposerManager.removeFillRecipe(input, fluid);
		}

		@Override
		public boolean removeExtractionRecipe(ItemStack input) {

			return TransposerManager.removeExtractionRecipe(input);
		}
	}

	/* CENTRIFUGE */
	public static class CentrifugeHandler implements ICentrifugeHandler {

		@Override
		public boolean addRecipe(int energy, ItemStack input, List<ItemStack> primaryOutputs, FluidStack secondaryOutput, boolean overwrite) {

			return CentrifugeManager.addRecipe(energy, input, primaryOutputs, secondaryOutput, overwrite);
		}

		@Override
		public boolean removeRecipe(ItemStack input) {

			return CentrifugeManager.removeRecipe(input);
		}
	}

	/** DYNAMOS */

	/* STEAM */
	public static class SteamHandler implements ISteamHandler {

		@Override
		public boolean addFuel(ItemStack input, int energy) {

			return false;
		}

		@Override
		public boolean removeFuel(ItemStack input) {

			return false;
		}

	}

	/* MAGMATIC */
	public static class MagmaticHandler implements IMagmaticHandler {

		@Override
		public boolean addFuel(String name, int energy) {

			return FuelManager.addMagmaticFuel(name, energy);
		}

		@Override
		public boolean removeFuel(String name) {

			return FuelManager.removeMagmaticFuel(name);
		}

	}

	/* COMPRESSION */
	public static class CompressionHandler implements ICompressionHandler {

		@Override
		public boolean addFuel(String name, int energy) {

			return FuelManager.addCompressionFuel(name, energy);
		}

		@Override
		public boolean addCoolant(String name, int cooling) {

			return FuelManager.addCoolant(name, cooling);
		}

		@Override
		public boolean removeFuel(String name) {

			return FuelManager.removeCompressionFuel(name);
		}

		@Override
		public boolean removeCoolant(String name) {

			return FuelManager.removeCoolant(name);
		}

	}

	/* REACTANT */
	public static class ReactantHandler implements IReactantHandler {

		@Override
		public boolean addFuel(String name, int energy) {

			return FuelManager.addReactantFuel(name, energy);
		}

		@Override
		public boolean addReactant(ItemStack input, int energy) {

			return FuelManager.addReactant(input, energy);
		}

		@Override
		public boolean removeFuel(String name) {

			return FuelManager.removeReactantFuel(name);
		}

		@Override
		public boolean removeReactant(ItemStack input) {

			return FuelManager.removeReactant(input);
		}

	}

	/* ENERVATION */
	public static class EnervationHandler implements IEnervationHandler {

		@Override
		public boolean addFuel(ItemStack input, int energy) {

			return false;
		}

		@Override
		public boolean removeFuel(ItemStack input) {

			return false;
		}

	}

}
