package net.dark_roleplay.projectbrazier.features.blocks.drawbridge;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;

public class DrawbridgeAnchorTileEntityRenderer extends TileEntityRenderer<DrawbridgeAnchorTileEntity> {

	public static final ResourceLocation[] LOCATIONS = new ResourceLocation[]{
			new ResourceLocation(ProjectBrazier.MODID, "block/drawbridge/drawbridge_bl"),
			new ResourceLocation(ProjectBrazier.MODID, "block/drawbridge/drawbridge_b"),
			new ResourceLocation(ProjectBrazier.MODID, "block/drawbridge/drawbridge_br"),
			new ResourceLocation(ProjectBrazier.MODID, "block/drawbridge/drawbridge_cl"),
			new ResourceLocation(ProjectBrazier.MODID, "block/drawbridge/drawbridge_c"),
			new ResourceLocation(ProjectBrazier.MODID, "block/drawbridge/drawbridge_cr"),
			new ResourceLocation(ProjectBrazier.MODID, "block/drawbridge/drawbridge_tl"),
			new ResourceLocation(ProjectBrazier.MODID, "block/drawbridge/drawbridge_t"),
			new ResourceLocation(ProjectBrazier.MODID, "block/drawbridge/drawbridge_tr")
	};

	private static IBakedModel[] models = new IBakedModel[9];

	public DrawbridgeAnchorTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
		super(rendererDispatcher);
	}

	private void initDrawbridgeModels(){
		for(int i = 0; i < 9; i++){
			models[i] = Minecraft.getInstance().getModelManager().getModel(LOCATIONS[i]);
		}
	}

	@Override
	public void render(DrawbridgeAnchorTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlayIn) {
		if(models[0] == null){
			initDrawbridgeModels();
		}
		initDrawbridgeModels();

		IVertexBuilder vBuffer = buffer.getBuffer(RenderType.getSolid());

		Direction dir = te.getBlockState().get(DrawbridgeAnchorBlock.HORIZONTAL_FACING);
		Direction dirRY = dir.rotateY();

		matrixStack.push();
		matrixStack.translate(0.5F, 0.5F, 0.5F);
		matrixStack.rotate(dirRY.toVector3f().rotationDegrees(MathHelper.lerp(partialTicks, te.getPrevAngle(), te.getAngle())));

		BlockPos.Mutable pos2 = new BlockPos.Mutable(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());

		switch(dir){
			case EAST:
				matrixStack.rotate(Vector3f.YP.rotationDegrees(270));
				break;
			case SOUTH:
				matrixStack.rotate(Vector3f.YP.rotationDegrees(180));
				break;
			case WEST:
				matrixStack.rotate(Vector3f.YP.rotationDegrees(90));
				break;
			case NORTH:
				matrixStack.rotate(Vector3f.YP.rotationDegrees(0));
				break;
		}
		matrixStack.translate(-0.5F, -0.5F, -0.5F);

		for (int w = 0; w < te.getWidth(); w++) {
			matrixStack.translate(1, 0, 0);
			pos2.move(dirRY);
			for (int h = 0; h < te.getHeight(); h++) {
				IBakedModel model = null;
				if (w == 0) {
					if (h == 0)
						model = models[0];
					else if (h == te.getHeight() - 1)
						model = models[6];//
					else
						model = models[3];//
				} else if (w == te.getWidth() - 1) {
					if (h == 0)
						model = models[2];
					else if (h == te.getHeight() - 1)
						model = models[8];//
					else
						model = models[5];//
				} else {
					if (h == 0)
						model = models[1];
					else if (h == te.getHeight() - 1)
						model = models[7];//T
					else
						model = models[4];//
				}


				Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer()
						.renderModel(te.getWorld(), model, Blocks.OAK_PLANKS.getDefaultState(), pos2, matrixStack, vBuffer, false, te.getWorld().getRandom(), 0L, combinedLight, EmptyModelData.INSTANCE);

				matrixStack.translate(0, 0, -1);
				pos2.move(dir);
			}
			matrixStack.translate(0, 0, te.getHeight());
			pos2.move(dir, -te.getHeight());
		}


		matrixStack.pop();

		//Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntity.getWorld(), planksModel, tileEntity.getBlockState(), tileEntity.getPos(), buffer, true, tileEntity.getWorld().getRandom(), 0L, EmptyModelData.INSTANCE);

	}

	@Override
	public boolean isGlobalRenderer(DrawbridgeAnchorTileEntity te) {
		return true;
	}
}
