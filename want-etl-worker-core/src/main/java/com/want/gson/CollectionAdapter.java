/**
 * -------------------------------------------------------
 * @FileName：CollectionAdapter.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.gson;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CollectionAdapter implements JsonSerializer<Collection<?>> {

	@Override
	public JsonElement serialize(Collection<?> src, Type typeOfSrc, JsonSerializationContext context) {
		if (src == null || src.isEmpty()) // exclusion is made here
			return null;

		JsonArray array = new JsonArray();

		for (Object child : src) {
			JsonElement element = context.serialize(child);
			array.add(element);
		}
		return array;
	}

}
