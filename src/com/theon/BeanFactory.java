package com.theon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BeanFactory {

    public static void main(String[] args) {
        Map<String,BeanDefination> beanMap=new HashMap<>();
        try {
            // 从配置文件中读取Bean和依赖关系
            Properties properties = loadProperties();
            Enumeration<?> propNames = properties.propertyNames();
            while (propNames.hasMoreElements()) {
                String name = (String)propNames.nextElement();
                String value = properties.getProperty(name);
//                System.out.println(name + ": " + value);
                // 判断是创建Bean还是注入依赖
                if (name.contains("dependency")) {
                    String[] arr = name.split("\\.");
                    String beanName=arr[0];
                    String dependName = value;
                    Dependency dependency = new Dependency();
                    dependency.setName(dependName);
                    dependency.setValue(beanMap.get(dependName));
                    if (beanMap.get(beanName).getDependencies() == null) {
                        List<Dependency> dependencyList = new ArrayList<>();
                        dependencyList.add(dependency);
                        beanMap.get(beanName).setDependencies(dependencyList);
                    } else {
                        beanMap.get(beanName).getDependencies().add(dependency);
                    }
                } else {
                    BeanDefination bean =  new BeanDefination();
                    bean.setBeanName(name);
                    bean.setBeanClass(Class.forName(value));
                    beanMap.put(name, bean);
                }
            }
        } catch (ClassNotFoundException e) {
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
        FileInputStream fileInputStream =
                new FileInputStream("src/com/theon/beans.properties");
        properties.load(fileInputStream);
        fileInputStream.close();
        return properties;
    }
}
