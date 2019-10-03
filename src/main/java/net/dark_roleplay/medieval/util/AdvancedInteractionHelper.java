package net.dark_roleplay.medieval.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.TriPredicate;
import org.apache.logging.log4j.util.TriConsumer;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class AdvancedInteractionHelper {

    private static Set<Interactor> interactors = new HashSet<Interactor>();

    public static void register(TriPredicate<World, BlockPos, BlockState> predicate, String tooltip, int requiredTicks, TriConsumer<World, BlockPos, BlockState> action){
        interactors.add(new Interactor(predicate, tooltip, requiredTicks * 50, action));
    }

    @Nullable
    public static Interactor getInteractor(World world, BlockPos pos, BlockState state){
        for(Interactor interactor : interactors){
            if(interactor.getPredicate().test(world, pos, state))
                return interactor;
        }
        return null;
    }

    public static class Interactor{
        private TriPredicate<World, BlockPos, BlockState> predicate;
        private long requiredMS = 1;
        private TriConsumer<World, BlockPos, BlockState> action;
        private String tooltip;

        public Interactor(TriPredicate<World, BlockPos, BlockState> predicate, String tooltip, long requiredMS, TriConsumer<World, BlockPos, BlockState> action){
            this.predicate = predicate;
            this.requiredMS = requiredMS;
            this.tooltip = tooltip;
            this.action = action;
        }


        public TriPredicate<World, BlockPos, BlockState> getPredicate() {
            return predicate;
        }


        public long getRequiredMS() {
            return requiredMS;
        }

        public TriConsumer<World, BlockPos, BlockState> getAction() {
            return action;
        }

        public String getTooltip(){
            return tooltip;
        }
    }

}
