package cofh.thermalexpansion.render;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ISmartBlockModel;

public class ModelMachine implements ISmartBlockModel {

	/* ISmartBlockModel */
	@Override
	public IBakedModel handleBlockState(IBlockState state) {

		return null;
	}

	/* IBakedModel */
	@Override
	public List<BakedQuad> getFaceQuads(EnumFacing face) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<BakedQuad> getGeneralQuads() {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAmbientOcclusion() {

		return false;
	}

	@Override
	public boolean isGui3d() {

		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {

		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {

		return null;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {

		return null;
	}

	public class InternalBakedModel implements IBakedModel {

		EnumFacing face;
		boolean isActive;
		int[] sideConfig = new int[6];

		/* IBakedModel */
		@Override
		public List<BakedQuad> getFaceQuads(EnumFacing face) {

			return Collections.emptyList();
		}

		@Override
		public List<BakedQuad> getGeneralQuads() {

			return null;
		}

		@Override
		public boolean isAmbientOcclusion() {

			return true;
		}

		@Override
		public boolean isGui3d() {

			return true;
		}

		@Override
		public boolean isBuiltInRenderer() {

			return false;
		}

		@Override
		public TextureAtlasSprite getParticleTexture() {

			return null;
		}

		@Override
		public ItemCameraTransforms getItemCameraTransforms() {

			return ItemCameraTransforms.DEFAULT;
		}

	}

}
