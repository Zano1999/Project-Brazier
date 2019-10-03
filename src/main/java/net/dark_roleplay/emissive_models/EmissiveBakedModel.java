package net.dark_roleplay.emissive_models;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmissiveBakedModel implements IBakedModel {
    IBakedModel emissiveModel = null;
    IBakedModel nonEmissiveModel = null;

    public EmissiveBakedModel(IBakedModel emissive, IBakedModel nonEmissive){
        this.emissiveModel = emissive;
        this.nonEmissiveModel = nonEmissive;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        List<BakedQuad> emissive = emissiveModel.getQuads(state, side, rand);
        List<BakedQuad> combinedQuads = new ArrayList<>();

        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(DefaultVertexFormats.BLOCK);
        final IVertexConsumer consumer = new VertexTransformer(builder) {
            @Override
            public void put(int element, float... data) {
                VertexFormatElement formatElement = DefaultVertexFormats.BLOCK.getElement(element);
                switch(formatElement.getUsage()) {
                    case UV: {
                        float[] newData = new float[2];
                        newData[0] = 1f;
                        newData[1] = 1f;
                        parent.put(element, newData);
                        break;
                    }
                    default: {
                        //parent.put(element, data);
                        break;
                    }
                }
            }
        };

        for(BakedQuad quad : emissive){
            quad.pipe(consumer);
            combinedQuads.add(builder.build());
        }

        combinedQuads.addAll(nonEmissiveModel.getQuads(state, side, rand));

        return combinedQuads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return nonEmissiveModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return nonEmissiveModel.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return nonEmissiveModel.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data){
        return nonEmissiveModel.getParticleTexture(data);
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return nonEmissiveModel.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return nonEmissiveModel.getOverrides();
    }
}
