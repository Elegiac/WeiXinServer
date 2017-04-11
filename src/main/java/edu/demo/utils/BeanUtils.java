package edu.demo.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BeanUtils {
	public static <T> T beanFromMap(Class<T> clz, Map<String, Object> params)
			throws InstantiationException, IllegalAccessException {
		T t = clz.newInstance();
		for (Entry<String, Object> entry : params.entrySet()) {
			try {
				Method setMethod = clz.getMethod("set" + StringUtils.upperCaseFirstLetter(entry.getKey()),
						String.class);
				setMethod.invoke(t, entry.getValue());
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return t;
	}

	public static Map<String, Object> beanToMap(Object obj)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<? extends Object> clz = obj.getClass();
		Map<String, Object> result = new HashMap<>();
		for (Method method : clz.getMethods()) {
			if (method.getName().startsWith("get") && !"getClass".equals(method.getName())) {
				result.put(method.getName().replaceAll("get", ""), method.invoke(obj));
			}
		}

		return result;
	}
	
	
}
