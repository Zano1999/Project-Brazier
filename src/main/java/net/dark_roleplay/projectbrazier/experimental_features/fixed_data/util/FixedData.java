package net.dark_roleplay.projectbrazier.experimental_features.fixed_data.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FixedData<T> {
	protected static final Gson GSON = new GsonBuilder().create();
	protected static final Logger LOGGER = LogManager.getLogger();
}
