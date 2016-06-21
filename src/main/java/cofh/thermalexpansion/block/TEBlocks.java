package cofh.thermalexpansion.block;

import cofh.api.core.IInitializer;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.core.ProxyClient;

import java.util.ArrayList;

public class TEBlocks {

	private TEBlocks() {

	}

	public static void preInit() {

		blockMachine = new BlockMachine();

		initList.add(blockMachine);

		ProxyClient.modelList.add(blockMachine);

		for (int i = 0; i < initList.size(); i++) {
			initList.get(i).preInit();
		}
	}

	public static void initialize() {

		for (int i = 0; i < initList.size(); i++) {
			initList.get(i).initialize();
		}
	}

	public static void postInit() {

		for (int i = 0; i < initList.size(); i++) {
			initList.get(i).postInit();
		}
	}

	static ArrayList<IInitializer> initList = new ArrayList<IInitializer>();

	/* REFERENCES */
	public static BlockMachine blockMachine;

}
