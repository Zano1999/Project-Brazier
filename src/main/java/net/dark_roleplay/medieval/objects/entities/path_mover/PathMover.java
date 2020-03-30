package net.dark_roleplay.medieval.objects.entities.path_mover;

import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathMover extends Entity {

    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;

    public PathMover(EntityType type, World world) {
        super(type, world);
        this.noClip = true;
    }

    @Override
    protected void registerData() {}

    public void tick() {
        if (this.world.isRemote || (this.getRidingEntity() == null || !this.getRidingEntity().isAlive()) && this.world.isBlockLoaded(new BlockPos(this))) {
            super.tick();

            Vec3d vec3d = this.getMotion();
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);
            float f = 1F;//this.getMotionFactor();

            this.setMotion(vec3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale((double)f));
            //this.setPosition(this.getPosX(), this.posY, this.posZ);
        } else {
            this.remove();
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() { return new SSpawnObjectPacket(this);}

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public float getCollisionBorderSize() {
        return 0.0001F;
    }
}
