package net.dark_roleplay.medieval.objects.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

import net.dark_roleplay.library.DRPLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.profiler.EmptyProfiler;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

/**
 * modified from code made by Draco18s. Information in <a href= "http://www.minecraftforge.net/forum/topic/42318-1102-water-liquid-block-model/?tab=comments#comment-228067">this thread</a>
 * @author Elix_x (August 2016), modified by Draco18s (October 2016) and then Cadiboo (August 2018)
 */
public class ModelsCache implements ISelectiveResourceReloadListener {

	public static final ModelsCache INSTANCE = new ModelsCache();

	public static final IModelState										DEFAULTMODELSTATE		= part -> java.util.Optional.empty();
	public static final VertexFormat									DEFAULTVERTEXFORMAT		= DefaultVertexFormats.ITEM;
	public static final Function<ResourceLocation, TextureAtlasSprite>	DEFAULTTEXTUREGETTER	= texture -> Minecraft.getInstance().getTextureMap().getAtlasSprite(texture.toString());

	private ModelBakery bakery;
	
	private final Map<ResourceLocation, IModel>			modelCache	= new HashMap<>();
	private final Map<ResourceLocation, IBakedModel>	bakedCache	= new HashMap<>();

	public IModel<?> getModel(final ResourceLocation location) {
		IModel<?> model = this.modelCache.get(location);
		if (model == null) {
			try {
				model = ModelLoaderRegistry.getModel(location);
			} catch (final Exception e) {
				e.printStackTrace();
				model = ModelLoaderRegistry.getMissingModel();
			}
			this.modelCache.put(location, model);
		}
		return model;
	}

	public IBakedModel getBakedModel(final ResourceLocation location) {
		return this.getBakedModel(location, DEFAULTMODELSTATE, DEFAULTVERTEXFORMAT, DEFAULTTEXTUREGETTER);
	}

	public IBakedModel getBakedModel(final ResourceLocation location, final IModelState state, final VertexFormat format, final Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
		IBakedModel bakedModel = this.bakedCache.get(location);
		if (bakedModel == null) {
			IModel<?> model = this.getModel(location);
			bakedModel = model.bake(this.bakery, textureGetter, null, format);
			this.bakedCache.put(location, bakedModel);
		}
		return bakedModel;
	}
	
	public IBakedModel getRetexturedBakedModel(final ResourceLocation location, String textureKey, String newTexture) {
		return this.getRetexturedBakedModel(location, DEFAULTMODELSTATE, DEFAULTVERTEXFORMAT, DEFAULTTEXTUREGETTER, textureKey, newTexture);
	}

	public IBakedModel getRetexturedBakedModel(final ResourceLocation location, final IModelState state, final VertexFormat format, final Function<ResourceLocation, TextureAtlasSprite> textureGetter, String textureKey, String newTexture) {
		ResourceLocation fakeLoc = new ResourceLocation(location.toString() + "." + textureKey.replace("#", "") + "_" + newTexture.replace(":", "_"));
		IBakedModel bakedModel = this.bakedCache.get(fakeLoc);
		if (bakedModel == null) {
			IModel<?> model = this.getModel(location);
			model = model.retexture(ImmutableMap.of(textureKey, newTexture));
			bakedModel = model.bake(this.bakery, textureGetter, null, format);;
			this.bakedCache.put(fakeLoc, bakedModel);
		}
		return bakedModel;
	}

	@Override
	public void onResourceManagerReload(final IResourceManager resourceManager, final Predicate<IResourceType> resourcePredicate) {
		this.modelCache.clear();
		this.bakedCache.clear();
		
		//this.bakery = new ModelBakery(resourceManager, Minecraft.getInstance().getTextureMap(), EmptyProfiler.INSTANCE);
	}

}