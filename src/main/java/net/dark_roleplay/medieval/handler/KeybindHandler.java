package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.crafter.objects.guis.CraftingScreen;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignHelper;
import net.dark_roleplay.medieval.objects.guis.fourteen.TimberedArea;
import net.dark_roleplay.medieval.objects.guis.fourteen.TimberedScreen;
import net.dark_roleplay.medieval.testing.Keybinds;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
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

            PlayerEntity p = Minecraft.getInstance().player;
            double radianYaw = (offset + p.getRotationYawHead()) * (Math.PI / 180);

            Vec3d dodgeVec = new Vec3d(-Math.sin(radianYaw), 0, Math.cos(radianYaw)).normalize();

            Vec3d motion = p.getMotion();
            double speedMult = p.collidedVertically ? p.isSprinting() ? 0.3 : 0.5 : p.isSprinting() ? 0.05 : 0.3;
            System.out.println(p.collidedVertically);

            p.setVelocity(motion.x + dodgeVec.x * speedMult, 0.3, motion.z + dodgeVec.z * speedMult);
            //TODO Send packet to server, remove fall damage add config for this.
        }
    }

}
