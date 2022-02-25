package net.dark_roleplay.projectbrazier.mixin;

import net.dark_roleplay.projectbrazier.feature.entities.ZiplineEntity;
import net.dark_roleplay.projectbrazier.mixin_helper.PlayerAnimator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public class PlayerModelMixin <T extends LivingEntity> extends HumanoidModel<T> {
	@Shadow
	@Final
	public ModelPart leftSleeve;

	@Shadow
	@Final
	public ModelPart rightSleeve;

	@Shadow
	@Final
	public ModelPart leftPants;

	@Shadow
	@Final
	public ModelPart rightPants;

	public PlayerModelMixin(ModelPart rootPart) {
		super(rootPart);
	}

	@Inject(method = "setupAnim", at = @At(value = "TAIL"))
	private void setRotationAnglesTail(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if(!(entity instanceof Player player) || entity.getVehicle() == null ||!(entity.getVehicle() instanceof ZiplineEntity))
			return;

		PlayerAnimator.animatePlayerZipline(player, limbSwing, limbSwingAmount, ageInTicks, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);

		this.copyRotations();
	}

	private void copyRotations(){
		this.rightSleeve.copyFrom(this.rightArm);
		this.leftSleeve.copyFrom(this.leftArm);
		this.rightPants.copyFrom(this.rightLeg);
		this.leftPants.copyFrom(this.leftLeg);
	}
}
