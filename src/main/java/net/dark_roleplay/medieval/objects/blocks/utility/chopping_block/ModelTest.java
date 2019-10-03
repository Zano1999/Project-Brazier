package net.dark_roleplay.medieval.objects.blocks.utility.chopping_block;

import net.dark_roleplay.medieval.testing.blockstate_loading.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;

public class ModelTest extends EntityModel {
    private final RendererModel body;
    private final RendererModel rightLeg;
    private final RendererModel leftLeg;
    private final RendererModel head;
    private final RendererModel leftArm;
    private final RendererModel rightArm;

    public ModelTest() {
        textureWidth = 32;
        textureHeight = 32;

        body = new RendererModel(this);
        body.setRotationPoint(0.0F, 6.0F, 0.0F);
        body.cubeList.add(new ModelBox(body, 0, 0, -4.0F, -6.0F, -2.0F, 8, 12, 4, 0.0F, false));

        rightLeg = new RendererModel(this);
        rightLeg.setRotationPoint(-2.0F, 6.0F, 0.0F);
        setRotationAngle(rightLeg, 0.0F, 0.0F, 0.5236F);
        body.addChild(rightLeg);
        rightLeg.cubeList.add(new ModelBox(rightLeg, 0, 0, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

        leftLeg = new RendererModel(this);
        leftLeg.setRotationPoint(2.0F, 6.0F, 0.0F);
        setRotationAngle(leftLeg, 0.5236F, 0.0F, 0.0F);
        body.addChild(leftLeg);
        leftLeg.cubeList.add(new ModelBox(leftLeg, 0, 0, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

        head = new RendererModel(this);
        head.setRotationPoint(0.0F, -6.0F, 0.0F);
        body.addChild(head);
        head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));

        leftArm = new RendererModel(this);
        leftArm.setRotationPoint(4.0F, -6.0F, 0.0F);
        setRotationAngle(leftArm, 0.0F, 0.0F, -0.8727F);
        body.addChild(leftArm);
        leftArm.cubeList.add(new ModelBox(leftArm, 0, 0, 0.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

        rightArm = new RendererModel(this);
        rightArm.setRotationPoint(-4.0F, -6.0F, 0.0F);
        setRotationAngle(rightArm, -0.4363F, 0.0F, 0.2618F);
        body.addChild(rightArm);
        rightArm.cubeList.add(new ModelBox(rightArm, 0, 0, -4.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
    }
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        body.render(scale);
    }
    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}