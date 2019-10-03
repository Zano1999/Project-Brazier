package net.dark_roleplay.tertiary_interactor.listeners;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.handler.KeybindHandler;
import net.dark_roleplay.tertiary_interactor.RunningTertiaryInteraction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

public class PlayerServerTickListener {

    private static Map<PlayerEntity, RunningTertiaryInteraction> interactions = new HashMap<PlayerEntity, RunningTertiaryInteraction>();

    @SubscribeEvent
    public static void tickServer(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT) return;

        for(Map.Entry<PlayerEntity, RunningTertiaryInteraction> entry : interactions.entrySet()){
            entry.getValue().tick();
        }
    }
}