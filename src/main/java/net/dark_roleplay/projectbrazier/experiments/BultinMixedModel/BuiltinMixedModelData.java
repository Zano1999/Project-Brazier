package net.dark_roleplay.projectbrazier.experiments.BultinMixedModel;

import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;
import java.util.List;

public class BuiltinMixedModelData implements IModelData {

	public static final ModelProperty<List<IQuadProvider>> PROP = new ModelProperty<>();

	private List<IQuadProvider> quadProvider;

	public BuiltinMixedModelData(List<IQuadProvider> quadProvider){
		this.quadProvider = quadProvider;
	}

	@Override
	public boolean hasProperty(ModelProperty<?> prop) {
		return prop == PROP;
	}

	@Nullable
	@Override
	public <T> T getData(ModelProperty<T> prop) {
		if(prop == PROP)
			return (T) quadProvider;
		return null;
	}

	@Nullable
	@Override
	public <T> T setData(ModelProperty<T> prop, T data) {
		if(prop == PROP) this.quadProvider = (List<IQuadProvider>) data;
		return data;
	}
}
