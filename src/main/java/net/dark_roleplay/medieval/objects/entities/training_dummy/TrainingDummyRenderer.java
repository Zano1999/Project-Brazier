package net.dark_roleplay.medieval.objects.entities.training_dummy;

import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class TrainingDummyRenderer extends EntityRenderer<TrainingDummyEntity> {

    TrainingDummyModel model = new TrainingDummyModel();

    public TrainingDummyRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(TrainingDummyEntity entity) {
        return new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/entity/bambi.png");
    }

    @Override
    public void doRender(TrainingDummyEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        this.renderManager.textureManager.bindTexture(this.getEntityTexture(entity));
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();

        GlStateManager.translated(x, y, z);


        model.render(entity, 0 , 0, 0, 0, 0, 0.0625F);

        GlStateManager.popMatrix();
        GlStateManager.enableCull();
    }

    @Override
    public boolean shouldRender(TrainingDummyEntity livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }
}
