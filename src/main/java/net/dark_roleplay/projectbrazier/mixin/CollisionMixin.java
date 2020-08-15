package net.dark_roleplay.projectbrazier.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class CollisionMixin {

	@Inject(method="getAllowedMovement(Lnet/minecraft/util/math/vector/Vector3d;)Lnet/minecraft/util/math/vector/Vector3d;", at=@At("TAIL"), cancellable = true)
	public void considerAdditionalCollision(Vector3d vec, CallbackInfoReturnable<Vector3d> info) {
//		Vector3d val = info.getReturnValue();
//
//		Entity entity = ((Entity)((Object) this));
//
//		AxisAlignedBB bb = new AxisAlignedBB(-15583, 166, -2511,- 15575, 167, -2506);
//
//		if(bb.contains(entity.getPositionVec()) && val.y < 0) {
//			val = new Vector3d(val.x, 0, val.z);
//		}
//		info.setReturnValue(val);
	}
}
