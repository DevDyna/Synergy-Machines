package com.synergy.machines.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
//TODO API : MOVE TO API
public class ClassUtils {
    public static <T> List<T> getAll(Class<?> clazz, Class<T> type) {
        List<T> result = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().equals(type)) {
                try {
                    result.add(type.cast(field.get(null)));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(
                            "Failed to access field: " + field.getName(), e);
                }
            }
        }

        return result;
    }
}