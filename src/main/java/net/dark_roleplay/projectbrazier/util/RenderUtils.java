package net.dark_roleplay.projectbrazier.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class RenderUtils {

	public static Vec3 getCameraPos(){
		return Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
	}

	public static Camera getRenderInfo(){
		return Minecraft.getInstance().gameRenderer.getMainCamera();
	}

	public static Matrix4f getProjectionMatrix(float partialTicks){
		return Minecraft.getInstance().gameRenderer.getProjectionMatrix(partialTicks);
	}

	public static Matrix4f getViewMatrix(){
		Camera renderInfo = getRenderInfo();
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

	public static Pair<Vec3, Vec3> screenToWorldSpaceRay(float partialTicks){
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

		Vec3 rayOrigin3 = new Vec3(rayOrigin.x(), rayOrigin.y(), rayOrigin.z());
		Vec3 rayEnd3 = new Vec3(rayEnd.x(), rayEnd.y(), rayEnd.z());
		Vec3 ray = rayEnd3.subtract(rayOrigin3).normalize().reverse();

		return Pair.of(rayOrigin3, ray);
	}

	public static Pair<Vec2, Boolean> worldToScreenSpace(Vec3 worldPos, float partialTicks, boolean useScaledGuiSize){
		Minecraft mc = Minecraft.getInstance();
		Vec3 cameraPos = getCameraPos();

		Vector3f newWorldPos = new Vector3f((float) (worldPos.x - cameraPos.x), (float)(worldPos.y - cameraPos.y), (float)(worldPos.z - cameraPos.z));

		Quaternion rotation = getRenderInfo().rotation().copy();
		rotation.conj();
		newWorldPos.transform(rotation);

		if (mc.options.bobView && getRenderInfo().getEntity() instanceof Player) {
			Player playerentity = (Player) getRenderInfo().getEntity();
			float f = playerentity.walkDist - playerentity.walkDistO;
			float f1 = -(playerentity.walkDist + f * partialTicks);
			float f2 = Mth.lerp(partialTicks, playerentity.oBob, playerentity.bob);

			newWorldPos.transform(Vector3f.XN.rotationDegrees(Math.abs(Mth.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F));
			newWorldPos.transform(Vector3f.ZN.rotationDegrees(Mth.sin(f1 * (float)Math.PI) * f2 * 3.0F));
			newWorldPos.add(new Vector3f(-Mth.sin(f1 * (float)Math.PI) * f2 * 0.5F, -Math.abs(Mth.cos(f1 * (float)Math.PI) * f2), 0.0F));
		}

		float half_height = (float) (useScaledGuiSize ? mc.getWindow().getGuiScaledHeight() : mc.getWindow().getScreenHeight()) / 2;//TODO Fix this
		float scale_factor = half_height / (newWorldPos.z() * (float) Math.tan(Math.toRadians(70)));//mc.gameRenderer..getFov(getRenderInfo(), partialTicks, true)  / 2)));

		int screenWidth = useScaledGuiSize ? mc.getWindow().getGuiScaledWidth() : mc.getWindow().getScreenWidth();
		int screenHeight = useScaledGuiSize ? mc.getWindow().getGuiScaledHeight() : mc.getWindow().getScreenHeight();

		return Pair.of(new Vec2((-newWorldPos.x() * scale_factor) + screenWidth/2F, (-newWorldPos.y() * scale_factor) + screenHeight/2F), newWorldPos.z() > 0);
	}
}
