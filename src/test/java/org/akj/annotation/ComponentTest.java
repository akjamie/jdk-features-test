package org.akj.annotation;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ComponentTest {

	private Map<String, Object> beans = new HashMap<String, Object>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	final void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class componentClass = Class.forName("org.akj.annotation.MockTarget");
		if (componentClass.isAnnotationPresent(Component.class)) {

			Component component = (Component) componentClass.getAnnotation(Component.class);
			String identifier = component.identifier();

			if (identifier == null || identifier.equals("")) {
				String className = componentClass.getName().substring(componentClass.getName().lastIndexOf(".") + 1);
				identifier = className.substring(0, 1).toLowerCase() + className.substring(1);
			}

			// TODO to consider the bean dependencies
			Object instance = componentClass.getConstructor().newInstance();

			beans.put(identifier, instance);
		}

		Assertions.assertEquals(1, beans.size());

		beans.forEach((k, v) -> {
			System.out.println("bean name:" + k + ", " + v);
		});
	}

}
