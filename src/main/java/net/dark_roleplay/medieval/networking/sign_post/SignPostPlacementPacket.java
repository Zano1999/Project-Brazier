package net.dark_roleplay.medieval.networking.sign_post;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class SignPostPlacementPacket {

    private static Map<PlayerEntity, Boolean> players = new HashMap<>();

    private boolean isRight = false;


    public static boolean getPlayer(PlayerEntity player) {
        return players.getOrDefault(player, true);
    }

    public static void setPlayer(PlayerEntity player, boolean value) {
        SignPostPlacementPacket.players.put(player, value);
    }

    public boolean isRight() {
        return isRight;
    }

    public SignPostPlacementPacket setRight(boolean right) {
        isRight = right;
        return this;
    }
}
