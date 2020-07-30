/**
 * -------------------------------------------------------
 * @FileName：EmptyStringTypeAdapter.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class EmptyStringTypeAdapter extends TypeAdapter<String> {

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter jsonWriter, final String s) throws IOException {
		if (s == null || s.isEmpty()) {
			jsonWriter.nullValue();
		} else {
			jsonWriter.value(s);
		}
	}

	@Override
	public String read(final JsonReader jsonReader) throws IOException {
		final JsonToken token = jsonReader.peek();
		switch (token) {
		case NULL:
			return "";
		case STRING:
			return jsonReader.nextString();
		default:
			throw new IllegalStateException("Unexpected token: " + token);
		}
	}

}
