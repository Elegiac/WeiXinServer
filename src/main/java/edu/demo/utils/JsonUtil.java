package edu.demo.utils;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	public static Map<String, Object> json2Map(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Object> map = mapper.readValue(json,
					new TypeReference<Map<String, Object>>() {
					});
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	public static String object2JsonString(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return null;
		}
		return json;
	}
}
