package net.dark_roleplay.projectbrazier.feature_client.model_loaders.pane_connected_model;

import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;

public class PaneCornerData implements IModelData {

	public static final ModelProperty<PaneCornerType>[] CONNECTION_PROPS = new ModelProperty[]{
			new ModelProperty<>(),
			new ModelProperty<>(),
			new ModelProperty<>(),
			new ModelProperty<>()
	};

	private PaneCornerType[] connectionTypes = new PaneCornerType[]{
			PaneCornerType.INNER_CORNER,
			PaneCornerType.INNER_CORNER,
			PaneCornerType.INNER_CORNER,
			PaneCornerType.INNER_CORNER
	};

	@Override
	public boolean hasProperty(ModelProperty<?> prop) {
		for(int i = 0; i < CONNECTION_PROPS.length; i++)
			if(prop == CONNECTION_PROPS[i])
				return true;
		return false;
	}

	@Nullable
	@Override
	public <T> T getData(ModelProperty<T> prop) {
		for(int i = 0; i < CONNECTION_PROPS.length; i++)
			if(prop == CONNECTION_PROPS[i])
				return (T)connectionTypes[i];
		return null;
	}

	@Nullable
	@Override
	public <T> T setData(ModelProperty<T> prop, T data) {
		for(int i = 0; i < CONNECTION_PROPS.length; i++)
			if(prop == CONNECTION_PROPS[i])
				connectionTypes[i] = (PaneCornerType) data;
		return null;
	}
}
