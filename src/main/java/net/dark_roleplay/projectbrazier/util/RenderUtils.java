package net.dark_roleplay.projectbrazier.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.*;

public class RenderUtils {

	public static Vector3d getCameraPos(){
		return Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
	}

	public static ActiveRenderInfo getRenderInfo(){
		return Minecraft.getInstance().gameRenderer.getMainCamera();
	}

	public static Matrix4f getProjectionMatrix(float partialTicks){
		return Minecraft.getInstance().gameRenderer.getProjectionMatrix(getRenderInfo(), partialTicks, true);
	}

	public static Matrix4f getViewMatrix(){
		ActiveRenderInfo renderInfo = getRenderInfo();
		Vector3f up = renderInfo.getUpVector();
		Vector3f forward = renderInfo.getLookVector();
		Vector3f right = new Vector3f(-1, 0, 0); //No getter available, calculate ourself
		right.transform(renderInfo.rotation());
		return new Matrix4f(new float[]{
				right.x(), right.y(), right.z(), 0,
				-up.x(), -up.y(), -up.z(), 0,
				forward.x(), forward.y(), forward.z(), 0,
				0, 0, 0, 1
		});
	}

	public static Matrix4f getPVMatrix(float partialTicks){
		Matrix4f projectionMatrix = getProjectionMatrix(partialTicks);
		projectionMatrix.multiply(getViewMatrix());
		return projectionMatrix;
	}

	public static Pair<Vector3d, Vector3d> screenToWorldSpaceRay(float partialTicks){
		double sWidth = Minecraft.getInstance().getWindow().getScreenWidth();
		double sHeight = Minecraft.getInstance().getWindow().getScreenHeight();
		double mouseX = Minecraft.getInstance().mouseHandler.xpos();
		double mouseY = Minecraft.getInstance().mouseHandler.ypos();

		Matrix4f pvMatrix = getPVMatrix(partialTicks);
		pvMatrix.invert();

		Vector4f rayOrigin = new Vector4f((float) ((sWidth-mouseX)/sWidth)*2-1, (float) ((sHeight-mouseY)/sHeight)*2-1, -1, 1.0F);
		Vector4f rayEnd = new Vector4f(rayOrigin.x(), rayOrigin.y(), 1, 1.0F);

		rayOrigin.transform(pvMatrix);
		if(rayOrigin.w() == 0.0F)
			throw new IllegalArgumentException("Received invalid Projection View Matrix, this shouldn't happen!");
		rayOrigin.perspectiveDivide();

		rayEnd.transform(pvMatrix);
		if(rayEnd.w() == 0.0F)
			throw new IllegalArgumentException("Received invalid Projection View Matrix, this shouldn't happen!");
		rayEnd.perspectiveDivide();

		Vector3d rayOrigin3 = new Vector3d(rayOrigin.x(), rayOrigin.y(), rayOrigin.z());
		Vector3d rayEnd3 = new Vector3d(rayEnd.x(), rayEnd.y(), rayEnd.z());
		Vector3d ray = rayEnd3.subtract(rayOrigin3).normalize().reverse();

		return Pair.of(rayOrigin3, ray);
	}

	public static Pair<Vector2f, Boolean> worldToScreenSpace(Vector3d worldPos, float partialTicks, boolean useScaledGuiSize){
		Minecraft mc = Minecraft.getInstance();
		Vector3d cameraPos = getCameraPos();

		Vector3f newWorldPos = new Vector3f((float) (worldPos.x - cameraPos.x), (float)(worldPos.y - cameraPos.y), (float)(worldPos.z - cameraPos.z));

		Quaternion rotation = getRenderInfo().rotation().copy();
		rotation.conj();
		newWorldPos.transform(rotation);

		if (mc.options.bobView && getRenderInfo().getEntity() instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) getRenderInfo().getEntity();
			float f = playerentity.walkDist - playerentity.walkDistO;
			float f1 = -(playerentity.walkDist + f * partialTicks);
			float f2 = MathHelper.lerp(partialTicks, playerentity.oBob, playerentity.bob);

			newWorldPos.transform(Vector3f.XN.rotationDegrees(Math.abs(MathHelper.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F));
			newWorldPos.transform(Vector3f.ZN.rotationDegrees(MathHelper.sin(f1 * (float)Math.PI) * f2 * 3.0F));
			newWorldPos.add(new Vector3f(-MathHelper.sin(f1 * (float)Math.PI) * f2 * 0.5F, -Math.abs(MathHelper.cos(f1 * (float)Math.PI) * f2), 0.0F));
		}

		float half_height = (float) (useScaledGuiSize ? mc.getWindow().getGuiScaledHeight() : mc.getWindow().getScreenHeight()) / 2;
		float scale_factor = half_height / (newWorldPos.z() * (float) Math.tan(Math.toRadians(mc.gameRenderer.getFov(getRenderInfo(), partialTicks, true)  / 2)));

		int screenWidth = useScaledGuiSize ? mc.getWindow().getGuiScaledWidth() : mc.getWindow().getScreenWidth();
		int screenHeight = useScaledGuiSize ? mc.getWindow().getGuiScaledHeight() : mc.getWindow().getScreenHeight();

		return Pair.of(new Vector2f((-newWorldPos.x() * scale_factor) + screenWidth/2F, (-newWorldPos.y() * scale_factor) + screenHeight/2F), newWorldPos.z() > 0);
	}
}
