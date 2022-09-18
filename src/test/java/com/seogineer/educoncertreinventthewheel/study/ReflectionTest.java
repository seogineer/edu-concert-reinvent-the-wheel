package com.seogineer.educoncertreinventthewheel.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("요구사항 1 - 클래스 정보 출력")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());
        logger.debug("=======================================================");

        //모든 필드 출력
        Arrays.stream(carClass.getDeclaredFields())
                .forEach(field -> logger.debug("Car class field : " + field));
        logger.debug("=======================================================");

        //모든 생성자 출력
        Arrays.stream(carClass.getConstructors())
                .forEach(constructor -> logger.debug("Car class constructor : " + constructor));
        logger.debug("=======================================================");

        //모든 메소드 출력
        Arrays.stream(carClass.getDeclaredMethods())
                .forEach(method-> logger.debug("Car class method " + method));
    }

    @Test
    @DisplayName("요구사항 2 - test로 시작하는 메소드 실행")
    void testMethodRun() {
        Class<Car> clazz = Car.class;
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().contains("test"))
                .forEach(method -> {
                    try {
                        method.invoke(clazz.getDeclaredConstructor().newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @DisplayName("요구사항 3 - @PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        Class<Car> clazz = Car.class;
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(PrintView.class)) {
                declaredMethod.invoke(clazz.getDeclaredConstructor().newInstance());
            }
        }
    }

    @Test
    @DisplayName("요구사항 4 - private field에 값 할당")
    void privateFieldAccess() throws Exception {
        Class<Car> clazz = Car.class;

        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);

        Field price = clazz.getDeclaredField("price");
        price.setAccessible(true);

        Car car = clazz.getDeclaredConstructor().newInstance();
        name.set(car, "Model 3");
        price.set(car, 10000);

        logger.debug("Car name : " + car.getName());
        logger.debug("Car price : " + car.getPrice());
    }

    @Test
    @DisplayName("요구사항 5 - 인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception {
        Class<Car> clazz = Car.class;
        Constructor<Car> constructor = clazz.getDeclaredConstructor();
        Car car = constructor.newInstance();
        logger.debug(car.toString());
    }

    @Test
    void testScanClass() {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage("com.seogineer.educoncertreinventthewheel.example")));

        final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
        classes.forEach(clazz -> logger.debug("controller annotation class : {}", clazz.getName()));
    }
}
