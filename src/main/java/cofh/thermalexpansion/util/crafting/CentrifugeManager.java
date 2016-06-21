package cofh.thermalexpansion.util.crafting;

import cofh.lib.inventory.ComparableItemStack;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalexpansion.api.crafting.recipes.ICentrifugeRecipe;

import gnu.trove.map.hash.THashMap;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class CentrifugeManager {

	private static Map<ComparableItemStackCentrifuge, RecipeCentrifuge> recipeMap = new THashMap<ComparableItemStackCentrifuge, RecipeCentrifuge>();

	private static boolean allowOverwrite = false;

	static {
		allowOverwrite = ThermalExpansion.CONFIG.get("RecipeManagers.Centrifuge", "AllowRecipeOverwrite", false);
	}

	private CentrifugeManager() {

	}

	public static RecipeCentrifuge getRecipe(ItemStack input) {

		if (input == null) {
			return null;
		}
		ComparableItemStackCentrifuge query = new ComparableItemStackCentrifuge(input);

		RecipeCentrifuge recipe = recipeMap.get(query);

		if (recipe == null) {
			query.metadata = OreDictionary.WILDCARD_VALUE;
			recipe = recipeMap.get(query);
		}
		return recipe;
	}

	public static boolean recipeExists(ItemStack input) {

		return getRecipe(input) != null;
	}

	public static RecipeCentrifuge[] getRecipeList() {

		return recipeMap.values().toArray(new RecipeCentrifuge[0]);
	}

	public static void addDefaultRecipes() {

	}

	public static void loadRecipes() {

	}

	public static void refreshRecipes() {

		Map<ComparableItemStackCentrifuge, RecipeCentrifuge> tempMap = new THashMap<ComparableItemStackCentrifuge, RecipeCentrifuge>(recipeMap.size());
		RecipeCentrifuge tempRecipe;

		for (Entry<ComparableItemStackCentrifuge, RecipeCentrifuge> entry : recipeMap.entrySet()) {
			tempRecipe = entry.getValue();
			tempMap.put(new ComparableItemStackCentrifuge(tempRecipe.input), tempRecipe);
		}
		recipeMap.clear();
		recipeMap = tempMap;
	}

	/* ADD RECIPES */
	protected static boolean addTERecipe(int energy, ItemStack input, List<ItemStack> primaryOutputs, FluidStack secondaryOutput) {

		if (input == null || primaryOutputs == null || primaryOutputs.isEmpty() || energy <= 0) {
			return false;
		}
		RecipeCentrifuge recipe = new RecipeCentrifuge(input, primaryOutputs, secondaryOutput, energy);
		recipeMap.put(new ComparableItemStackCentrifuge(input), recipe);
		return true;
	}

	public static boolean addRecipe(int energy, ItemStack input, List<ItemStack> primaryOutputs, FluidStack secondaryOutput, boolean overwrite) {

		if (input == null || primaryOutputs == null || primaryOutputs.isEmpty() || energy <= 0 || !(allowOverwrite & overwrite) && recipeExists(input)) {
			return false;
		}
		RecipeCentrifuge recipe = new RecipeCentrifuge(input, primaryOutputs, secondaryOutput, energy);
		recipeMap.put(new ComparableItemStackCentrifuge(input), recipe);
		return true;
	}

	/* REMOVE RECIPES */
	public static boolean removeRecipe(ItemStack input) {

		return recipeMap.remove(new ComparableItemStackCentrifuge(input)) != null;
	}

	/* RECIPE CLASS */
	public static class RecipeCentrifuge implements ICentrifugeRecipe {

		final ItemStack input;
		final List<ItemStack> primaryOutputs;
		final FluidStack secondaryOutput;

		final int energy;

		RecipeCentrifuge(ItemStack input, List<ItemStack> primaryOutputs, FluidStack secondaryOutput, int energy) {

			this.input = input;
			this.primaryOutputs = primaryOutputs;
			this.secondaryOutput = secondaryOutput;
			this.energy = energy;

			if (input.stackSize <= 0) {
				input.stackSize = 1;
			}
		}

		@Override
		public ItemStack getInput() {

			return input.copy();
		}

		@Override
		public List<ItemStack> getPrimaryOutputs() {

			return primaryOutputs;
		}

		@Override
		public FluidStack getSecondaryOutput() {

			return secondaryOutput;
		}

		@Override
		public int getEnergy() {

			return energy;
		}
	}

	/* ITEMSTACK CLASS */
	public static class ComparableItemStackCentrifuge extends ComparableItemStack {

		static final String DUST = "dust";

		public static boolean safeOreType(String oreName) {

			return oreName.startsWith(DUST);
		}

		public static int getOreID(ItemStack stack) {

			int id = ItemHelper.oreProxy.getPrimaryOreID(stack);

			if (id == -1 || !safeOreType(ItemHelper.oreProxy.getOreName(id))) {
				return -1;
			}
			return id;
		}

		public ComparableItemStackCentrifuge(ItemStack stack) {

			super(stack);
			oreID = getOreID(stack);
		}

		public ComparableItemStackCentrifuge(Item item, int damage, int stackSize) {

			super(item, damage, stackSize);
			this.oreID = getOreID(this.toItemStack());
		}

		@Override
		public ComparableItemStackCentrifuge set(ItemStack stack) {

			super.set(stack);
			oreID = getOreID(stack);

			return this;
		}
	}

}
