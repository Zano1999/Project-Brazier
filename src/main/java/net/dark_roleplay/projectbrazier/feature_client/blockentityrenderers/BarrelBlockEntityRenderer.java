package net.dark_roleplay.projectbrazier.feature_client.blockentityrenderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.feature.blockentities.BarrelBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blocks.BarrelStorageType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BarrelBlockEntityRenderer implements BlockEntityRenderer<BarrelBlockEntity> {
	public BarrelBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(BarrelBlockEntity tileEntity, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

		LazyOptional<IFluidHandler> lazyTank = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		if(tileEntity.getStorageType() != BarrelStorageType.FLUID || !lazyTank.isPresent()) return;

		IFluidHandler tank = lazyTank.orElse(null);
		if(tank == null) return;

		FluidStack fluid = tank.getFluidInTank(0);
		if(fluid.isEmpty()) return;

		float maxCapacity = tank.getTankCapacity(0);
		int content = fluid.getAmount();

		ResourceLocation fluidTexture = fluid.getFluid().getAttributes().getStillTexture(fluid);
		TextureAtlasSprite fluidSprite = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(fluidTexture);

		VertexConsumer builder = buffer.getBuffer(RenderType.translucentMovingBlock());

		createQuad(matrix.last().pose(), builder, Mth.lerp(content/maxCapacity, 0.125F, 0.875F), fluidSprite, combinedLight, fluid.getFluid().getAttributes().getColor());
	}

	private void createQuad(Matrix4f matrix, VertexConsumer builder, float verticalOffset, TextureAtlasSprite sprite, int light, int color){
		int r = color >> 16 & 0xFF;
		int g = color >> 8 & 0xFF;
		int b = color & 0xFF;
		builder.vertex(matrix, 0.125F, verticalOffset, 0.875F).color(r, g, b, 255).uv(sprite.getU(0), sprite.getV(0)).uv2(light).normal(0, 1F, 0).endVertex();
		builder.vertex(matrix, 0.875F, verticalOffset, 0.875F).color(r, g, b, 255).uv(sprite.getU(16), sprite.getV(0)).uv2(light).normal(0, 1F, 0).endVertex();
		builder.vertex(matrix, 0.875F, verticalOffset, 0.125F).color(r, g, b, 255).uv(sprite.getU(16), sprite.getV(16)).uv2(light).normal(0, 1F, 0).endVertex();
		builder.vertex(matrix, 0.125F, verticalOffset, 0.125F).color(r, g, b, 255).uv(sprite.getU(0), sprite.getV(16)).uv2(light).normal(0, 1F, 0).endVertex();
	}
}
