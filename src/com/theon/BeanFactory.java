package com.theon;

import java.lang.reflect.InvocationTargetException;

public class BeanFactory {

    static void main() {
        try {
            Class<?> clazz = Class.forName("com.theon.Person");
            BeanDefination bean = new BeanDefination();

            Object person = clazz.getConstructor().newInstance();
            System.out.println(person.toString());
        } catch (ClassNotFoundException | NoSuchMethodException
                 | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
