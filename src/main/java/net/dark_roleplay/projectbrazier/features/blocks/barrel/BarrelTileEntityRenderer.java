package net.dark_roleplay.projectbrazier.features.blocks.barrel;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BarrelTileEntityRenderer extends TileEntityRenderer <BarrelTileEntity>{
	public BarrelTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(BarrelTileEntity tileEntity, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		LazyOptional<IFluidHandler> lazyTank = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		if(tileEntity.getStorageType() != BarrelTileEntity.StorageType.FLUID || !lazyTank.isPresent()) return;

		IFluidHandler tank = lazyTank.orElse(null);
		if(tank == null) return;

		FluidStack fluid = tank.getFluidInTank(0);
		if(fluid.isEmpty()) return;

		float maxCapacity = tank.getTankCapacity(0);
		int content = fluid.getAmount();

		ResourceLocation fluidTexture = fluid.getFluid().getAttributes().getStillTexture(fluid);
		TextureAtlasSprite fluidSprite = Minecraft.getInstance().getModelManager().getAtlasTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE).getSprite(fluidTexture);

		IVertexBuilder builder = buffer.getBuffer(RenderType.getTranslucentMovingBlock());

		createQuad(matrix.getLast().getMatrix(), builder, MathHelper.lerp(content/maxCapacity, 0.125F, 0.875F), fluidSprite, combinedLight, fluid.getFluid().getAttributes().getColor());
	}

	private void createQuad(Matrix4f matrix, IVertexBuilder builder, float verticalOffset, TextureAtlasSprite sprite, int light, int color){
		int r = color >> 16 & 0xFF;
		int g = color >> 8 & 0xFF;
		int b = color & 0xFF;
		builder.pos(matrix, 0.125F, verticalOffset, 0.875F).color(r, g, b, 255).tex(sprite.getInterpolatedU(0), sprite.getInterpolatedV(0)).lightmap(light).normal(0, 1F, 0).endVertex();
		builder.pos(matrix, 0.875F, verticalOffset, 0.875F).color(r, g, b, 255).tex(sprite.getInterpolatedU(16), sprite.getInterpolatedV(0)).lightmap(light).normal(0, 1F, 0).endVertex();
		builder.pos(matrix, 0.875F, verticalOffset, 0.125F).color(r, g, b, 255).tex(sprite.getInterpolatedU(16), sprite.getInterpolatedV(16)).lightmap(light).normal(0, 1F, 0).endVertex();
		builder.pos(matrix, 0.125F, verticalOffset, 0.125F).color(r, g, b, 255).tex(sprite.getInterpolatedU(0), sprite.getInterpolatedV(16)).lightmap(light).normal(0, 1F, 0).endVertex();
	}
}
