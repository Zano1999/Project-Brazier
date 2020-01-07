package net.dark_roleplay.medieval.networking.dodge;

public class DodgePacket {

    private float radianDirection = 0f;

    public float getRadianDirection() {
        return radianDirection;
    }

    public DodgePacket setRadianDirection(float radianDirection) {
        this.radianDirection = radianDirection;
        return this;
    }
}
