package cofh.thermalexpansion.util.crafting;

import cofh.thermalexpansion.ThermalExpansion;

public class FreezerManager {

	private static boolean allowOverwrite = false;

	static {
		allowOverwrite = ThermalExpansion.CONFIG.get("RecipeManagers.Freezer", "AllowRecipeOverwrite", false);
	}

	private FreezerManager() {

	}

	/* RECIPE CLASS */

}
