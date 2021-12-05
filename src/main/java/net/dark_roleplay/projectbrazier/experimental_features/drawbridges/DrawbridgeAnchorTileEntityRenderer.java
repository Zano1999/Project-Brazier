package net.dark_roleplay.projectbrazier.experimental_features.drawbridges;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

		IVertexBuilder vBuffer = buffer.getBuffer(RenderType.solid());

		Direction dir = te.getBlockState().getValue(DrawbridgeAnchorBlock.HORIZONTAL_FACING);
		Direction dirRY = dir.getClockWise();

		matrixStack.pushPose();
		matrixStack.translate(0.5F, 0.5F, 0.5F);
		matrixStack.mulPose(dirRY.step().rotationDegrees(Mth.lerp(partialTicks, te.getPrevAngle(), te.getAngle())));

		BlockPos.Mutable pos2 = new BlockPos.Mutable(te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ());

		switch(dir){
			case EAST:
				matrixStack.mulPose(Vector3f.YP.rotationDegrees(270));
				break;
			case SOUTH:
				matrixStack.mulPose(Vector3f.YP.rotationDegrees(180));
				break;
			case WEST:
				matrixStack.mulPose(Vector3f.YP.rotationDegrees(90));
				break;
			case NORTH:
				matrixStack.mulPose(Vector3f.YP.rotationDegrees(0));
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


				Minecraft.getInstance().getBlockRenderer().getModelRenderer()
						.renderModel(te.getLevel(), model, Blocks.OAK_PLANKS.defaultBlockState(), pos2, matrixStack, vBuffer, false, te.getLevel().getRandom(), 0L, combinedLight, EmptyModelData.INSTANCE);

				matrixStack.translate(0, 0, -1);
				pos2.move(dir);
			}
			matrixStack.translate(0, 0, te.getHeight());
			pos2.move(dir, -te.getHeight());
		}


		matrixStack.popPose();

		//Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntity.getWorld(), planksModel, tileEntity.getBlockState(), tileEntity.getPos(), buffer, true, tileEntity.getWorld().getRandom(), 0L, EmptyModelData.INSTANCE);

	}

	@Override
	public boolean shouldRenderOffScreen(DrawbridgeAnchorTileEntity te) {
		return true;
	}
}
