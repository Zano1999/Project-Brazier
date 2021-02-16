package net.dark_roleplay.projectbrazier.objects.model_loaders.simple_pane_conneted_model;

import net.dark_roleplay.projectbrazier.objects.model_loaders.axis_connected_models.AxisConnectionType;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;

public class SimplePaneModelData implements IModelData {

	public static final ModelProperty<AxisConnectionType> PRIMARY_CONNECTION = new ModelProperty();
	public static final ModelProperty<AxisConnectionType> SECONDARY_CONNECTION = new ModelProperty();
	private AxisConnectionType primaryConnection, secondaryConnection;

	@Override
	public boolean hasProperty(ModelProperty<?> prop) {
		if(prop == PRIMARY_CONNECTION || prop == SECONDARY_CONNECTION) return true;
		return false;
	}

	@Nullable
	@Override
	public <T> T getData(ModelProperty<T> prop) {
		if(prop == PRIMARY_CONNECTION) return (T)this.primaryConnection;
		if(prop == SECONDARY_CONNECTION) return (T)this.secondaryConnection;
		return null;
	}

	@Nullable
	@Override
	public <T> T setData(ModelProperty<T> prop, T data) {
		if(prop == PRIMARY_CONNECTION && data instanceof AxisConnectionType) primaryConnection = (AxisConnectionType) data;
		if(prop == SECONDARY_CONNECTION && data instanceof AxisConnectionType) secondaryConnection = (AxisConnectionType) data;
		return null;
	}

}
