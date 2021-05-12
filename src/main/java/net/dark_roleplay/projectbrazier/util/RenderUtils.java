package net.dark_roleplay.projectbrazier.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.*;

public class RenderUtils {

	public static Vector3d getCameraPos(){
		return Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
	}

	public static ActiveRenderInfo getRenderInfo(){
		return Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
	}

	public static Matrix4f getProjectionMatrix(float partialTicks){
		return Minecraft.getInstance().gameRenderer.getProjectionMatrix(getRenderInfo(), partialTicks, true);
	}

	public static Matrix4f getViewMatrix(){
		ActiveRenderInfo renderInfo = getRenderInfo();
		Vector3f up = renderInfo.getUpVector();
		Vector3f forward = renderInfo.getViewVector();
		Vector3f right = new Vector3f(-1, 0, 0); //No getter available, calculate ourself
		right.transform(renderInfo.getRotation());
		return new Matrix4f(new float[]{
				right.getX(), right.getY(), right.getZ(), 0,
				-up.getX(), -up.getY(), -up.getZ(), 0,
				forward.getX(), forward.getY(), forward.getZ(), 0,
				0, 0, 0, 1
		});
	}

	public static Matrix4f getPVMatrix(float partialTicks){
		Matrix4f projectionMatrix = getProjectionMatrix(partialTicks);
		projectionMatrix.mul(getViewMatrix());
		return projectionMatrix;
	}

	public static Pair<Vector3d, Vector3d> screenToWorldSpaceRay(float partialTicks){
		double sWidth = Minecraft.getInstance().getMainWindow().getWidth();
		double sHeight = Minecraft.getInstance().getMainWindow().getHeight();
		double mouseX = Minecraft.getInstance().mouseHelper.getMouseX();
		double mouseY = Minecraft.getInstance().mouseHelper.getMouseY();

		Matrix4f pvMatrix = getPVMatrix(partialTicks);
		pvMatrix.invert();

		Vector4f rayOrigin = new Vector4f((float) ((sWidth-mouseX)/sWidth)*2-1, (float) ((sHeight-mouseY)/sHeight)*2-1, -1, 1.0F);
		Vector4f rayEnd = new Vector4f(rayOrigin.getX(), rayOrigin.getY(), 1, 1.0F);

		rayOrigin.transform(pvMatrix);
		if(rayOrigin.getW() == 0.0F)
			throw new IllegalArgumentException("Received invalid Projection View Matrix, this shouldn't happen!");
		rayOrigin.perspectiveDivide();

		rayEnd.transform(pvMatrix);
		if(rayEnd.getW() == 0.0F)
			throw new IllegalArgumentException("Received invalid Projection View Matrix, this shouldn't happen!");
		rayEnd.perspectiveDivide();

		Vector3d rayOrigin3 = new Vector3d(rayOrigin.getX(), rayOrigin.getY(), rayOrigin.getZ());
		Vector3d rayEnd3 = new Vector3d(rayEnd.getX(), rayEnd.getY(), rayEnd.getZ());
		Vector3d ray = rayEnd3.subtract(rayOrigin3).normalize().inverse();

		return Pair.of(rayOrigin3, ray);
	}

	public static Vector2f worldToScreenSpace(Vector3d worldPos, float partialTicks){
		Vector3f newWorldPos = new Vector3f(getCameraPos().subtract(worldPos));

		Quaternion rotation = RenderUtils.getRenderInfo().getRotation().copy();
		rotation.conjugate();
		newWorldPos.transform(rotation);

		if (Minecraft.getInstance().gameSettings.viewBobbing && getRenderInfo().getRenderViewEntity() instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) getRenderInfo().getRenderViewEntity();
			float f = playerentity.distanceWalkedModified - playerentity.prevDistanceWalkedModified;
			float f1 = -(playerentity.distanceWalkedModified + f * partialTicks);
			float f2 = MathHelper.lerp(partialTicks, playerentity.prevCameraYaw, playerentity.cameraYaw);

			Quaternion rotA = Vector3f.ZP.rotationDegrees(MathHelper.sin(f1 * (float)Math.PI) * f2 * 3.0F);
			Quaternion rotB = Vector3f.XP.rotationDegrees(Math.abs(MathHelper.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F);
			rotB.conjugate();
			rotA.conjugate();

			newWorldPos.transform(rotB);
			newWorldPos.transform(rotA);

			newWorldPos.add(new Vector3f(MathHelper.sin(f1 * (float)Math.PI) * f2 * 0.5F, Math.abs(MathHelper.cos(f1 * (float)Math.PI) * f2), 0.0F));

		}

		float half_height = (float) Minecraft.getInstance().getMainWindow().getHeight() / 2;
		float scale_factor = half_height / (newWorldPos.getZ() * (float) Math.tan(Math.toRadians(Minecraft.getInstance().gameRenderer.getFOVModifier(getRenderInfo(),partialTicks, true)  / 2)));

		return new Vector2f(-newWorldPos.getX() * scale_factor, newWorldPos.getY() * scale_factor);
	}
}
