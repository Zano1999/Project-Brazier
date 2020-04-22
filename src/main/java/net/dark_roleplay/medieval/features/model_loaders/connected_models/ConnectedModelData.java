package net.dark_roleplay.medieval.features.model_loaders.connected_models;

import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;

public class ConnectedModelData implements IModelData {

	public static final ModelProperty<ConnectionType> CONNECTION = new ModelProperty();
	private ConnectionType connection;

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
		if(prop == CONNECTION && data instanceof ConnectionType) connection = (ConnectionType) data;
		return null;
	}

	public enum ConnectionType {
		SINGLE,
		LEFT,
		RIGHT,
		CENTER;

		public ConnectionType addLeft() {
			if (this == SINGLE)
				return LEFT;
			else if (this == RIGHT)
				return CENTER;
			return this;
		}

		public ConnectionType addRight() {
			if (this == SINGLE)
				return RIGHT;
			else if (this == LEFT)
				return CENTER;
			return this;
		}
	}
}
