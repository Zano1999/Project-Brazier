package net.dark_roleplay.medieval.objects.blocks.building.roofs.hacks;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GeneratedRoofModel implements IBakedModel {

    List<BakedQuad> quads = null;

    public GeneratedRoofModel(List<BakedQuad> quads){
        this.quads = quads;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return Minecraft.getInstance().getTextureMap().getAtlasSprite("drpmedieval:block/shingle_roofs/planks_treated_wood");
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }
}
