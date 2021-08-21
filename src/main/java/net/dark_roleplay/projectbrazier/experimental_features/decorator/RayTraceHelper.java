package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RayTraceHelper {

	public static Triple<DecorChunk, DecorState, Vector3d> rayTraceForDecor(World world, PlayerEntity player){
		Vector3d raytraceStart = player.getEyePosition(0);
		Vector3d raytraceEnd = player.getEyePosition(0).add(player.getLookVec().mul(4, 4, 4));

		double delta = raytraceEnd.x - raytraceStart.x;
		DecorChunk hitChunk = null;
		DecorState hitState = null;
		Vector3d hitPos = null;
		double hitDistance = 1000;

		Set<DecorChunk> chunks = getChunksForRay(world, raytraceStart, raytraceEnd);

		for(DecorChunk chunk : chunks){
			Set<DecorState> decors = chunk.getDecors();

			for (DecorState decor : decors) {
				AxisAlignedBB aabb = decor.getBoundingBox();
				Optional<Vector3d> hit = aabb.rayTrace(raytraceStart, raytraceEnd);

				if(hit.isPresent()) {
					double hitDist = hit.get().getX() / delta;
					if (hitDist < hitDistance) {
						hitDistance = hitDist;
						hitPos = hit.get();
						hitChunk = chunk;
						hitState = decor;
					}
				}
			}
		}

		return hitState == null ? null : Triple.of(hitChunk, hitState, hitPos);
	}

	private static Set<DecorChunk> getChunksForRay(World world, Vector3d start, Vector3d end){
		int x1 = (int) Math.floor(start.x) >> 4, x2 = (int) Math.floor(end.x) >> 4;
		int y1 = (int) Math.floor(start.y) >> 4, y2 = (int) Math.floor(end.y) >> 4;
		int z1 = (int) Math.floor(start.z) >> 4, z2 = (int) Math.floor(end.z) >> 4;
		Set<DecorChunk> chunks = new HashSet<>();

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		int dz = Math.abs(z2 - z1);

		for(int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++)
			for(int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++){
				LazyOptional<DecorContainer> cap = world.getChunk(x, z).getCapability(DecorRegistrar.DECOR);
				if(!cap.isPresent()) continue;
				DecorContainer cont = cap.orElse(null);
				for(int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++){
					DecorChunk chunk = cont.getDecorChunk(y, false);
					if(chunk != null) chunks.add(chunk);
				}
			}

		return chunks;
	}
}