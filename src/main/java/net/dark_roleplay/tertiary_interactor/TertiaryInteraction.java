package net.dark_roleplay.tertiary_interactor;

import net.dark_roleplay.tertiary_interactor.api.IInteractorPredicate;
import net.dark_roleplay.tertiary_interactor.api.IInteractionConsumer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.HashMap;
import java.util.Map;

public class TertiaryInteraction {

    public static Map<Block, TertiaryInteraction> interactions = new HashMap<>();

    private int requiredTicks;
    private IInteractorPredicate predicate;
    private IInteractionConsumer ticker;
    private IInteractionConsumer action;

    protected TertiaryInteraction(int requiredTicks, IInteractorPredicate predicate, IInteractionConsumer ticker, IInteractionConsumer action){
        this.requiredTicks = requiredTicks;
        this.predicate = predicate;
        this.ticker = ticker;
        this.action = action;
    }

    public boolean isValid(World world, BlockPos pos, BlockState state, PlayerEntity player){
        return this.predicate == null || predicate.test(world, pos, state, player);
    }

    public void tick(World world, BlockPos pos, BlockState state, PlayerEntity player){
        if(this.ticker == null) return;
        this.ticker.apply(world, pos, state, player);
    }

    public void apply(World world, BlockPos pos, BlockState state, PlayerEntity player){
        if(this.action == null) return;
        this.action.apply(world, pos, state, player);
    }

    public static class Builder{

        private Block validBlock;
        private int requiredTicks = 20;
        private IInteractorPredicate predicate = null;
        private IInteractionConsumer ticker = null;
        private IInteractionConsumer action = null;

        private Builder(Block block){
            this.validBlock = block;
        }

        public static Builder create(Block validBlock){
            return new Builder(validBlock);
        }

        public Builder setInteractionTicks(int ticks){
            this.requiredTicks = ticks;
            return this;
        }

        public Builder setPredicate(IInteractorPredicate predicate){
            this.predicate = predicate;
            return this;
        }

        public Builder setTicker(IInteractionConsumer ticker){
            this.ticker = ticker;
            return this;
        }

        public Builder setAction(IInteractionConsumer action){
            this.action = action;
            return this;
        }

        public TertiaryInteraction build(){
            TertiaryInteraction interaction = new TertiaryInteraction(requiredTicks, this.predicate, this.ticker, this.action);
            interactions.put(this.validBlock, interaction);
            return interaction;
        }
    }



    public static TertiaryInteraction getInteraction(World world, BlockRayTraceResult rayTrace, PlayerEntity player){
        BlockState state = world.getBlockState(rayTrace.getPos());
        Block b = state.getBlock();
        TertiaryInteraction interaction = TertiaryInteraction.interactions.get(b);
        if(interaction != null && interaction.isValid(world, rayTrace.getPos(), state, player)) return interaction;
        return null;
    }
}
