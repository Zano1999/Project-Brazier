package net.dark_roleplay.medieval.features.model_loaders.connected_models;

import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;

public class AxisConnectedModelData implements IModelData {

	public static final ModelProperty<AxisConnectionType> CONNECTION = new ModelProperty();
	private AxisConnectionType connection;

	@Override
	public boolean hasProperty(ModelProperty<?> prop) {
		if(prop == CONNECTION) return true;
		return false;
	}

	@Nullable
	@Override
	public <T> T getData(ModelProperty<T> prop) {
		if(prop == CONNECTION) return (T)this.connection;
		return null;
	}

	@Nullable
	@Override
	public <T> T setData(ModelProperty<T> prop, T data) {
		if(prop == CONNECTION && data instanceof AxisConnectionType) connection = (AxisConnectionType) data;
		return null;
	}

}
