package net.dark_roleplay.projectbrazier.feature_client.model_loaders.block_specific.roof_model_loader;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.block_specific.roof_model_loader.util.RoofModelGenerator;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.*;
import java.util.function.Function;

public class RoofModel implements IModelGeometry<RoofModel> {

	private final float height, width, length, verticalOffset, horizontalOffset;
	private final int steps;
	private Map<String, String> textures = new HashMap<>();
	private Map<String, Material> compiledTextures = new HashMap<>();
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
	public Collection<Material> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		if(this.compiledTextures.isEmpty())
			for(Map.Entry<String, String> texture : this.textures.entrySet())
				this.compiledTextures.put(texture.getKey(), new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(texture.getValue())));

		return this.compiledTextures.values();
	}

	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
		Vector3f translation = modelTransform.getRotation().getTranslation();
		Vector3f scale = modelTransform.getRotation().getScale();
		Quaternion rotation = modelTransform.getRotation().getLeftRotation();
		PoseStack stack = new PoseStack();
		stack.scale(scale.x(), scale.y(), scale.z());
		stack.translate(translation.x(), translation.y(), translation.z());
		stack.mulPose(rotation);

		RoofModelGenerator generator = new RoofModelGenerator(width, height, steps, new Vector3f(0, this.verticalOffset, this.horizontalOffset), this.tileOffset, stack, spriteGetter, this.compiledTextures);
		Map<Direction, List<BakedQuad>> model = new EnumMap<>(Direction.class);
		for(Direction dir : Direction.values())
			model.put(dir, new ArrayList());

		return new SimpleBakedModel(
				generator.getFull(),
				model, false, owner.isSideLit(), owner.isShadedInGui(),
				spriteGetter.apply(this.compiledTextures.get("particle")),
				owner.getCameraTransforms(), ItemOverrides.EMPTY);
	}
}
