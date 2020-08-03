package net.dark_roleplay.projectbrazier.features.blocks.drawbridge;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.lwjgl.system.MathUtil;
import sun.java2d.windows.GDIRenderer;

public class DrawbridgeAnchorTileEntityRenderer extends TileEntityRenderer<DrawbridgeAnchorTileEntity> {
	public DrawbridgeAnchorTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
		super(rendererDispatcher);
	}

	@Override
	public void render(DrawbridgeAnchorTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlayIn) {
		IBakedModel planksModel = Minecraft.getInstance().getModelManager().getModel(BlockModelShapes.getModelLocation(Blocks.OAK_PLANKS.getDefaultState()));

		//public boolean renderModelSmooth(IBlockDisplayReader worldIn, IBakedModel modelIn, BlockState stateIn, BlockPos
		//posIn, MatrixStack matrixStackIn, IVertexBuilder buffer, boolean checkSides, Random randomIn, long rand, int combinedOverlayIn, net.minecraftforge.client.model.data.IModelData modelData) {

		IVertexBuilder vBuffer = buffer.getBuffer(RenderType.getSolid());

		Direction dir = te.getBlockState().get(DrawbridgeAnchorBlock.HORIZONTAL_FACING);
		Direction dirRY = dir.rotateY();

		matrixStack.push();

		matrixStack.translate(0.5F, 0.5F, 0.5F);
		matrixStack.rotate(dirRY.toVector3f().rotationDegrees(MathHelper.lerp(partialTicks, te.getPrevAngle(), te.getAngle())));

		matrixStack.translate(-0.5F, -0.5F, -0.5F);
		BlockPos.Mutable pos2 = new BlockPos.Mutable(te.getPos().getX(), te.getPos().getY() ,te.getPos().getZ());

		for(int w = 0; w < te.getWidth(); w++){
			matrixStack.translate(dirRY.getXOffset(), dirRY.getYOffset(), dirRY.getZOffset());
			pos2.move(dirRY);
			for(int h = 0; h < te.getHeight(); h++) {
				Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer()
						.renderModelSmooth(te.getWorld(), planksModel, Blocks.OAK_PLANKS.getDefaultState(), pos2, matrixStack, vBuffer, false, te.getWorld().getRandom(), 0L, combinedLight, EmptyModelData.INSTANCE);

				matrixStack.translate(dir.getXOffset(), dir.getYOffset(), dir.getZOffset());
				pos2.move(dir);
			}
			matrixStack.translate(-dir.getXOffset() * te.getHeight(), -dir.getYOffset() * te.getHeight(), -dir.getZOffset() * te.getHeight());
			pos2.move(dir, -te.getHeight());
		}


		matrixStack.pop();

			//Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntity.getWorld(), planksModel, tileEntity.getBlockState(), tileEntity.getPos(), buffer, true, tileEntity.getWorld().getRandom(), 0L, EmptyModelData.INSTANCE);

	}
}
