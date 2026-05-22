package com.theon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Properties;

public class BeanFactory {

    public static void main() {
        try {
            // 从配置文件中读取Bean和依赖关系
            Properties properties = loadProperties();
            Enumeration<?> names = properties.propertyNames();
            while (names.hasMoreElements()) {
                String name = (String)names.nextElement();
                String value = properties.getProperty(name);
                System.out.println(name + ": " + value);
            }
            // 反射实例化对象
            Class<?> clazz = Class.forName("com.theon.User");
            BeanDefination bean = new BeanDefination();
            Object person = clazz.getConstructor().newInstance();
            // 依赖注入
            // 读取配置

        } catch (ClassNotFoundException | NoSuchMethodException
                 | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static Properties loadProperties() throws IOException {
        // 从文件加载配置
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("beans.properties");
        properties.load(fileInputStream);
        fileInputStream.close();
        return properties;
    }
}
