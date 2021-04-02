package net.dark_roleplay.projectbrazier.util.marg;

import net.dark_roleplay.marg.common.material.MaterialCondition;

import java.util.ArrayList;
import java.util.Arrays;

public class ConditionHelper {

	public static MaterialCondition createItemCondition(String material, String... items){
		return new MaterialCondition(material, Arrays.asList(items), new ArrayList<>(), new ArrayList<>());
	}
}
