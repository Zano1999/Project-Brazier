package net.dark_roleplay.medieval.objects.armor_model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

//You'll very likely have a few renames according to your version, this is 1.12 code.
public class ArmorTest extends BipedModel<PlayerEntity> {
    private final RendererModel armorHead;
    private final RendererModel armorBody;
    private final RendererModel armorRightArm;
    private final RendererModel armorLeftArm;
    private final RendererModel armorRightLeg;
    private final RendererModel armorLeftLeg;
    private final RendererModel armorRightBoot;
    private final RendererModel armorLeftBoot;

    //Don't touch the constructor just rename it to fit the class name
    public ArmorTest() { //Changed constructor
        textureWidth = 64;
        textureHeight = 64;

        armorHead = new RendererModel(this);
        armorHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedHead.addChild(armorHead);

        armorBody = new RendererModel(this);
        armorBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedBody.addChild(armorBody);
        armorRightArm = new RendererModel(this);
        armorRightArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedRightArm.addChild(armorRightArm);

        armorLeftArm = new RendererModel(this);
        armorLeftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedLeftArm.addChild(armorLeftArm);

        armorRightLeg = new RendererModel(this);
        armorRightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedRightLeg.addChild(armorRightLeg);

        armorLeftLeg = new RendererModel(this);
        armorLeftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedLeftLeg.addChild(armorLeftLeg);


        armorRightBoot = new RendererModel(this);
        armorRightBoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedRightLeg.addChild(armorRightBoot);

        armorLeftBoot = new RendererModel(this);
        armorLeftBoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedLeftLeg.addChild(armorLeftBoot);

        setupCustomModel();
    }

    private void setupCustomModel(){
        //Paste code that is responsible for adding cubes & bones here.
        //Example:
        armorHead.cubeList.add(new ModelBox(armorHead, 0, 0, -9.0F, -23.0F, 2.0F, 16, 16, 16, 0.0F, false));

        RendererModel newBone = new RendererModel(this);
        newBone.setRotationPoint(0.0F, 0.0F, 0.0F);
        armorHead.addChild(newBone);
        newBone.cubeList.add(new ModelBox(newBone, 0, 0, -1.0F, -1.0F, 0.0F, 1, 1, 1, 0.0F, false));
    }

    public BipedModel<PlayerEntity> applyData(BipedModel defaultArmor, EquipmentSlotType slot){
        this.isChild = defaultArmor.isChild;
        this.isSneak = defaultArmor.isSneak;
        this.isSitting = defaultArmor.isSitting;
        this.rightArmPose = defaultArmor.rightArmPose;
        this.leftArmPose = defaultArmor.leftArmPose;

        armorHead.isHidden = true;
        armorBody.isHidden = true;
        armorRightArm.isHidden = true;
        armorLeftArm.isHidden = true;
        armorRightLeg.isHidden = true;
        armorLeftLeg.isHidden = true;
        armorRightBoot.isHidden = true;
        armorLeftBoot.isHidden = true;
        switch(slot){
            case HEAD:
                armorHead.isHidden = false;
                break;
            case CHEST:
                armorBody.isHidden = false;
                armorRightArm.isHidden = false;
                armorLeftArm.isHidden = false;
                break;
            case LEGS:
                armorRightLeg.isHidden = false;
                armorLeftLeg.isHidden = false;
                break;
            case FEET:
                armorRightBoot.isHidden = false;
                armorLeftBoot.isHidden = false;
                break;
            default:
                break;
        }
        return this;
    }

    @Override
    //There is no need to edit this
    public void render(PlayerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        this.armorHead.copyModelAngles(this.bipedHead);
        this.armorBody.copyModelAngles(this.bipedBody);
        this.armorRightArm.copyModelAngles(this.bipedRightArm);
        this.armorLeftArm.copyModelAngles(this.bipedLeftArm);
        this.armorRightLeg.copyModelAngles(this.bipedRightLeg);
        this.armorLeftLeg.copyModelAngles(this.bipedLeftLeg);
        this.armorRightBoot.copyModelAngles(this.bipedRightLeg);
        this.armorLeftBoot.copyModelAngles(this.bipedLeftLeg);

        GlStateManager.pushMatrix();
        if (entity.shouldRenderSneaking()) GlStateManager.translatef(0.0F, 0.2F, 0.0F);
        this.armorHead.render(scale);
        this.armorBody.render(scale);
        this.armorRightArm.render(scale);
        this.armorLeftArm.render(scale);
        this.armorRightLeg.render(scale);
        this.armorLeftLeg.render(scale);
        this.armorRightBoot.render(scale);
        this.armorLeftBoot.render(scale);
        GlStateManager.popMatrix();
    }
}