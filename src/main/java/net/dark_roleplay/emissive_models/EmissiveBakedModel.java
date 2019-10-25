package net.dark_roleplay.emissive_models;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import net.minecraftforge.client.model.pipeline.VertexTransformer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmissiveBakedModel implements IBakedModel {
    private static final VertexFormat ITEM_FORMAT_WITH_LIGHTMAP = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

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

        for(BakedQuad quad : emissive){
            combinedQuads.add(transformQuad(quad, 0.007F));

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

    private static BakedQuad transformQuad(BakedQuad quad, float light) {

        VertexFormat format = getFormatWithLightMap(quad.getFormat());

        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);

        VertexLighterFlat trans = new VertexLighterFlat(Minecraft.getInstance().getBlockColors()) {
            @Override
            protected void updateLightmap(float[] normal, float[] lightmap, float x, float y, float z) {
                lightmap[0] = light;
                lightmap[1] = light;
            }

            @Override
            public void setQuadTint(int tint) {}
        };

        trans.setParent(builder);

        quad.pipe(trans);

        builder.setQuadTint(quad.getTintIndex());
        builder.setQuadOrientation(quad.getFace());
        builder.setTexture(quad.getSprite());
        builder.setApplyDiffuseLighting(false);

        return builder.build();
    }

    public static VertexFormat getFormatWithLightMap(VertexFormat format) {

        if (format == DefaultVertexFormats.BLOCK) {
            return DefaultVertexFormats.BLOCK;
        } else if (format == DefaultVertexFormats.ITEM) {
            return ITEM_FORMAT_WITH_LIGHTMAP;
        } else if (!format.hasUv(1)) {
            VertexFormat result = new VertexFormat(format);

            result.addElement(DefaultVertexFormats.TEX_2S);

            return result;
        }

        return format;
    }
}
