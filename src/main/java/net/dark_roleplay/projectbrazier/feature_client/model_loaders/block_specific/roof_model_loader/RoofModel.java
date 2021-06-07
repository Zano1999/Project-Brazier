package net.dark_roleplay.projectbrazier.feature_client.model_loaders.block_specific.roof_model_loader;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.block_specific.roof_model_loader.util.RoofModelGenerator;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.*;
import java.util.function.Function;

public class RoofModel implements IModelGeometry<RoofModel> {

	private final float height, width, length, verticalOffset, horizontalOffset;
	private final int steps;
	private Map<String, String> textures = new HashMap<>();
	private Map<String, RenderMaterial> compiledTextures = new HashMap<>();
	private final int tileOffset;

	public RoofModel(float height, float width, float length, int steps, float verticalOffset, float horizontalOffset, int tileOffset) {
		this.height = height;
		this.width = width;
		this.length = length;
		this.steps = steps;
		this.verticalOffset = verticalOffset;
		this.horizontalOffset = horizontalOffset;
		this.tileOffset = tileOffset;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	public float getLength() {
		return length;
	}

	public float getVerticalOffset() {
		return verticalOffset;
	}

	public float getHorizontalOffset() {
		return horizontalOffset;
	}

	public int getTileOffset() {
		return tileOffset;
	}

	public int getSteps() {
		return steps;
	}

	public void addTexture(String key, String value){
		this.textures.put(key, value);
	}

	@Override
	public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		if(this.compiledTextures.isEmpty())
			for(Map.Entry<String, String> texture : this.textures.entrySet())
				this.compiledTextures.put(texture.getKey(), new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(texture.getValue())));

		return this.compiledTextures.values();
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		Vector3f translation = modelTransform.getRotation().getTranslation();
		Vector3f scale = modelTransform.getRotation().getScale();
		Quaternion rotation = modelTransform.getRotation().getRotationLeft();
		MatrixStack stack = new MatrixStack();
		stack.scale(scale.getX(), scale.getY(), scale.getZ());
		stack.translate(translation.getX(), translation.getY(), translation.getZ());
		stack.rotate(rotation);

		RoofModelGenerator generator = new RoofModelGenerator(width, height, steps, new Vector3f(0, this.verticalOffset, this.horizontalOffset), this.tileOffset, stack, spriteGetter, this.compiledTextures);
		Map<Direction, List<BakedQuad>> model = new EnumMap<>(Direction.class);
		for(Direction dir : Direction.values())
			model.put(dir, new ArrayList());

		return new SimpleBakedModel(
				generator.getFull(),
				model, false, owner.isSideLit(), owner.isShadedInGui(),
				spriteGetter.apply(this.compiledTextures.get("particle")),
				owner.getCameraTransforms(), ItemOverrideList.EMPTY);
	}
}
