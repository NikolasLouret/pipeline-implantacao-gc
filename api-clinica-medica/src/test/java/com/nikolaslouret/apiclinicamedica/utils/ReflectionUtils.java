package com.nikolaslouret.apiclinicamedica.utils;

import java.lang.reflect.Field;
import java.util.Objects;

public class ReflectionUtils {
    public static void assertFieldsEqual(Object expected, Object actual) {
        Field[] expectedFields = expected.getClass().getDeclaredFields();

        for (Field expectedField : expectedFields) {
            expectedField.setAccessible(true);

            try {
                Field actualField = actual.getClass().getDeclaredField(expectedField.getName());
                actualField.setAccessible(true);

                if(!expectedField.getName().equals("cpf")) {
                    Object expectedValue = expectedField.get(expected);
                    Object actualValue = actualField.get(actual);

                    if (!Objects.equals(expectedValue, actualValue)) {
                        throw new AssertionError(String.format("Field %s doesn't match. Expected: %s, Actual: %s",
                                expectedField.getName(), expectedValue, actualValue));
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new AssertionError("Error accessing field: " + expectedField.getName(), e);
            }
        }
    }
}

