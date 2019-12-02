package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.holders.MedievalConfigs;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignHelper;
import net.dark_roleplay.medieval.objects.packets.DodgePacket;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class KeybindHandler {

    public static KeyBinding BLOCK_INTERACTOR = new KeyBinding("keybind.drpmedieval.interactor", GLFW.GLFW_KEY_GRAVE_ACCENT, "keybind.category.drpmedieval");
    public static KeyBinding DODGE = new KeyBinding("keybind.drpmedieval.movement.dodge", GLFW.GLFW_KEY_B, "keybind.category.drpmedieval");

    @SubscribeEvent
    public static void KeyInput(InputEvent.KeyInputEvent event) {
        if (BLOCK_INTERACTOR.isPressed()) {
            //Minecraft.getInstance().displayGuiScreen(new CraftingScreen());
            //Minecraft.getInstance().displayGuiScreen(new TimberedScreen(new TimberedArea(Minecraft.getInstance().world, new BlockPos(-1031, 70, 821), new BlockPos(-1031, 72, 815))));
            RoadSignHelper.INSTANCE.setRight(!RoadSignHelper.INSTANCE.isRight());
        }else if(DODGE.isPressed()){
            PlayerEntity player = Minecraft.getInstance().player;

            if(!MedievalConfigs.SKILL_CONFIG.ALLOOW_AIRBORNE.get() && !player.onGround) return;

            GameSettings binds = Minecraft.getInstance().gameSettings;

            boolean f = binds.keyBindForward.isKeyDown(), b = binds.keyBindBack.isKeyDown(), r = binds.keyBindRight.isKeyDown(), l = binds.keyBindLeft.isKeyDown();
            boolean fb = f == b, lr = l == r;

            int offset = !fb && b ? 180 : 0;
            if(!lr){
               if(!fb && f){
                   offset += l ? -45 : 45;
               }else if(!fb){
                   offset += l ? 45 : -45;
               }else{
                   offset += l ? -90 : 90;
               }
            }

            double radianDirection = (offset + player.getRotationYawHead()) * (Math.PI / 180);

            DarkRoleplayMedieval.MOD_CHANNEL.sendToServer(new DodgePacket((float) radianDirection));

            Vec3d dodgeVec = new Vec3d(-Math.sin(radianDirection), 0, Math.cos(radianDirection)).normalize();

            Vec3d motion = player.getMotion();
            double speedMult = player.collidedVertically ? player.isSprinting() ? 0.3 : 0.5 : player.isSprinting() ? 0.05 : 0.3;

            player.setVelocity(motion.x + dodgeVec.x * speedMult, 0.3, motion.z + dodgeVec.z * speedMult);
        }
    }

}
