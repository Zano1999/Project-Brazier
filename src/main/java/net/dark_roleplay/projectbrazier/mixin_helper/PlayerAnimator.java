package net.dark_roleplay.projectbrazier.mixin_helper;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.player.Player;

public class PlayerAnimator {

	public static void animatePlayerZipline(Player player, float limbSwing, float limbSwingAmount, float ageInTicks, ModelPart... limbs) {
		ModelPart
				leftArm = limbs[0],
				rightArm = limbs[1],
				leftLeg = limbs[2],
				rightLeg = limbs[3];

		leftArm.zRot = (float) Math.toRadians(190);
		leftArm.y = 0;
		rightArm.zRot = (float) Math.toRadians(170);
		rightArm.y = 0;

		float factor = 4;
		float animTick = ((ageInTicks % (180*factor))/factor) - 90;

		leftLeg.zRot = (float) (Math.toRadians(Math.sin(animTick - 30) * 4) + Math.toRadians(-10));
		rightLeg.zRot = (float) (Math.toRadians(Math.sin(animTick) * 4) + Math.toRadians(10));
		leftLeg.xRot = (float) (Math.toRadians(Math.sin(animTick) * 6));
		rightLeg.xRot = (float) (Math.toRadians(Math.sin(animTick + 10) * 7));
	}
}
