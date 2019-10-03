package net.dark_roleplay.tertiary_interactor.network;

import net.dark_roleplay.library.networking.SimplePacket;
import net.dark_roleplay.tertiary_interactor.TertiaryInteraction;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class TertiaryInteractionStarted extends SimplePacket<TertiaryInteractionStarted> {

    private static Logger LOGGER = LogManager.getLogger();

    public TertiaryInteractionStarted(){}

    @Override
    public void encode(TertiaryInteractionStarted roadSignEditSignPacket, PacketBuffer packetBuffer) { }

    @Override
    public TertiaryInteractionStarted decode(PacketBuffer packetBuffer) {
        return new TertiaryInteractionStarted();
    }

    @Override
    public void onMessage(TertiaryInteractionStarted obj, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        PlayerEntity player = context.getSender();
        World world = player.getEntityWorld();

        Vec3d vec3d = new Vec3d(player.getPosition().getX(), player.getPosition().getY() + player.getEyeHeight(), player.getPosition().getZ());
        Vec3d vec3d1 = player.getLook(0F);
        Vec3d vec3d2 = vec3d.add(vec3d1.x * 6, vec3d1.y * 6, vec3d1.z * 6);

        RayTraceResult rayTrace = world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        if(rayTrace.getType() != RayTraceResult.Type.BLOCK){
            printWarning(player);
            return;
        }

        BlockRayTraceResult blockRayTrace = (BlockRayTraceResult) rayTrace;

        TertiaryInteraction interaction = TertiaryInteraction.getInteraction(Minecraft.getInstance().world, blockRayTrace, Minecraft.getInstance().player);
        if(interaction == null || !interaction.isValid(world, blockRayTrace.getPos(), world.getBlockState(blockRayTrace.getPos()), player)){
            printWarning(player);
            return;
        }


    }

    private void printWarning(PlayerEntity player){
        LOGGER.warn("Player '{}' send a TertiaryInteraction Packet in an Invalid Position. This may be caused by Lag or the player might be cheating", player.getName());
    }
}
