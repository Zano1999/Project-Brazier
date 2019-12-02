package net.dark_roleplay.crafter.objects.reload_listeners;

import net.minecraft.entity.player.PlayerEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OffThreadRecipeCollector {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void SyncRecipesToPlayer(PlayerEntity player){
        executor.submit(() -> {

        });
    }

}
