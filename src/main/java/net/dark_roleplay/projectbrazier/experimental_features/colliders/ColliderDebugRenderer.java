package net.dark_roleplay.projectbrazier.experimental_features.colliders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dark_roleplay.justutilities.physics.collision.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ColliderDebugRenderer implements DebugRenderer.SimpleDebugRenderer {

	private OrientedBB boxA = new OrientedBB(new AABB(-0.5, 0, -0.5, .5, 2, .5), new Vec3(0, 0, 1));
	private OrientedBB boxB = new OrientedBB(new AABB(-2.5, -1, 0, 2.5, 0, 9), new Vec3(0, 0, 0));

	private double dir = 0.01;

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, double p_113509_, double p_113510_, double p_113511_) {
		VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.lines());

		poseStack.pushPose();
		poseStack.translate(-p_113509_, -p_113510_, -p_113511_);

		float delta = ((Minecraft.getInstance().level.getGameTime() % 200) + 1)/200F;
		float delta2 = ((Minecraft.getInstance().level.getGameTime() % 50) + 1)/50F;
		float delta3 = ((Minecraft.getInstance().level.getGameTime() % 25) + 1)/25F;

		//boxB.move(new Vec3(0, dir, 0));
		boxB.rotate((float) dir, 0, 0);
		if(boxB.getRotX() > 0)
			dir = -0.5;
		else if(boxB.getRotX() < -90)
			dir = 0.5;
		boxA.setPos(new Vec3(0, 0, 0));

		long start = System.nanoTime();
		CollissionInformation collision = CollisionChecker.checkCollision(boxA, boxB);
		System.out.println(System.nanoTime() - start);

		int color = collision != null ? 0xFF0000FF : 0x0000FFFF;

		if(collision != null){
			Vec3 axis = collision.axis().normalize().multiply(collision.distance(), collision.distance(), collision.distance());
			boxA.move(axis);
			renderOverlap(poseStack, vertexconsumer, collision);
		}

		renderBB(poseStack, vertexconsumer, boxA, 0xFFFF00FF);
		renderBB(poseStack, vertexconsumer, boxB, color);

		poseStack.popPose();
	}

	private void renderBB(PoseStack poseStack, VertexConsumer vc, IBoundingBox box, int color){
		Vec3[] verts = box.getVerts();

		renderEdge(poseStack, vc, verts[0], verts[1], color);
		renderEdge(poseStack, vc, verts[2], verts[3], color);
		renderEdge(poseStack, vc, verts[4], verts[5], color);
		renderEdge(poseStack, vc, verts[6], verts[7], color);

		renderEdge(poseStack, vc, verts[0], verts[2], color);
		renderEdge(poseStack, vc, verts[1], verts[3], color);
		renderEdge(poseStack, vc, verts[4], verts[6], color);
		renderEdge(poseStack, vc, verts[5], verts[7], color);

		renderEdge(poseStack, vc, verts[0], verts[4], color);
		renderEdge(poseStack, vc, verts[1], verts[5], color);
		renderEdge(poseStack, vc, verts[2], verts[6], color);
		renderEdge(poseStack, vc, verts[3], verts[7], color);
	}

	private void renderOverlap(PoseStack poseStack, VertexConsumer cons, CollissionInformation collissionInformation){
		Vec3 axis = collissionInformation.axis().normalize();
		Vec3 dist = axis.multiply(collissionInformation.distance(), collissionInformation.distance(), collissionInformation.distance());

		cons
				.vertex(poseStack.last().pose(), 0, 0, 0)
				.color(255, 255, 255, 255)
				.normal(poseStack.last().normal(), (float)(dist.x), (float)(dist.y), (float)(dist.z))
				.endVertex();
		cons
				.vertex(poseStack.last().pose(), (float) dist.x, (float) dist.y, (float) dist.z)
				.color(255, 255, 255, 255)
				.normal(poseStack.last().normal(), (float) (-dist.x), (float)(-dist.y), (float)(-dist.z))
				.endVertex();
	}

	private void renderEdge(PoseStack poseStack, VertexConsumer cons, Vec3 a, Vec3 b, int color){
		cons
				.vertex(poseStack.last().pose(), (float) a.x, (float) a.y, (float) a.z)
				.color(color >> 24 & 0xFF, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF)
				.normal(poseStack.last().normal(), (float)(b.x - a.x), (float)(b.y - a.y), (float)(b.z - a.z))
				.endVertex();
		cons
				.vertex(poseStack.last().pose(), (float) b.x, (float) b.y, (float) b.z)
				.color(color >> 24 & 0xFF, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF)
				.normal(poseStack.last().normal(), (float)(a.x - b.x), (float)(a.y - b.y), (float)(a.z - b.z))
				.endVertex();
	}
}
