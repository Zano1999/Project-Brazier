package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

public final class RoadSignHelper {

    public static final RoadSignHelper INSTANCE = new RoadSignHelper();

    private boolean displayRoadSignHud = false;
    private boolean isRight = false;

    private RoadSignHelper(){}

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public boolean shouldDisplayRoadSignHud() {
        boolean old = displayRoadSignHud;
        displayRoadSignHud = false;
        return old;
    }

    public void displayRoadSignHud() {
        this.displayRoadSignHud = true;
    }
}
