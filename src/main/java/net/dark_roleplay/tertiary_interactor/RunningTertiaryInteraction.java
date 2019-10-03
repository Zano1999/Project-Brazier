package net.dark_roleplay.tertiary_interactor;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RunningTertiaryInteraction {

    private TertiaryInteraction interaction;
    private World world;
    private BlockPos startingPos;
    private BlockState startedState;
    private int passedTicks;
    private PlayerEntity player;

    public RunningTertiaryInteraction(TertiaryInteraction interaction, World world, BlockPos started, BlockState startedState, PlayerEntity player){
        this.interaction = interaction;
        this.world = world;
        this.startingPos = started;
        this.startedState = startedState;
        this.passedTicks = 0;
        this.player = player;
    }

    public boolean isValid(BlockPos currentPos, BlockState currentState){
        return startingPos.equals(currentPos) && currentState == startedState;
    }

    public void tick(){
        interaction.tick(world, startingPos, startedState, player);
    }
}
