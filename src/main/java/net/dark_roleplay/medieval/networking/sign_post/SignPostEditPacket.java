package net.dark_roleplay.medieval.networking.sign_post;

import net.minecraft.util.math.BlockPos;

public class SignPostEditPacket {

    private int signID = 0;
    private BlockPos tileEntityPosition = new BlockPos(0, 0, 0);
    private String newText = "";

    public int getSignID() {
        return signID;
    }

    public SignPostEditPacket setSignID(int signID) {
        this.signID = signID;
        return this;
    }

    public BlockPos getTileEntityPosition() {
        return tileEntityPosition;
    }

    public SignPostEditPacket setTileEntityPosition(BlockPos tileEntityPosition) {
        this.tileEntityPosition = tileEntityPosition;
        return this;
    }

    public String getNewText() {
        return newText;
    }

    public SignPostEditPacket setNewText(String newText) {
        this.newText = newText;
        return this;
    }
}
