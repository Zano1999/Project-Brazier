package net.dark_roleplay.projectbrazier.experimental_features.BultinMixedModel;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.core.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public interface IQuadProvider {
	List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand);
}
