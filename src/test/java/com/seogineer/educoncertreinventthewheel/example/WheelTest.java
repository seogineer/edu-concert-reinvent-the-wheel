package com.seogineer.educoncertreinventthewheel.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class WheelTest {
    private static final Logger logger = LoggerFactory.getLogger(WheelTest.class);

    @DisplayName("요구사항 1 - 클래스의 Entity 애노테이션을 확인 후 Column 애노테이션이 있는 필드 이름만 가져온다.")
    @Test
    void getAllFieldByColumnAnnotation() {
        Class<Wheel> wheelClass = Wheel.class;

        List<String> test = List.of("name", "size", "price");

        List<String> fields = Arrays.stream(wheelClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(Field::getName)
                .collect(Collectors.toList());

        assertThat(fields).isEqualTo(test);
    }

    @DisplayName("요구사항 2 - 클래스의 Entity 애노테이션을 확인 후 Column 애노테이션에 설정된 name 값으로 필드정보를 가져온다.")
    @Test
    void getFieldByColumnAnnotationNameValue() {
        Class<Wheel> wheelClass = Wheel.class;

        Arrays.stream(wheelClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach(field -> {
                    try {
                        logger.debug(String.valueOf(wheelClass.getDeclaredField(field.getName())));
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                });
    }

    @DisplayName("요구사항 3 - Transient 애노테이션이 있는 경우 필드 값에서 제외를 한다.")
    @Test
    void getFieldByTransient() {
        Class<Wheel> wheelClass = Wheel.class;

        Arrays.stream(wheelClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .forEach(field -> {
                    try {
                        logger.debug(String.valueOf(wheelClass.getDeclaredField(field.getName())));
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                });
    }

    @DisplayName("요구사항 4 - 위의 정보를 바탕으로 select 쿼리 만들어보기")
    @Test
    public void getSelectQuery() throws Exception {
        Class<Wheel> wheelClass = Wheel.class;
        Constructor<Wheel> constructor = wheelClass.getDeclaredConstructor();
        Wheel wheel = constructor.newInstance();

        Arrays.stream(wheelClass.getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                logger.debug(field.getName() + " : " + field.get(wheel));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
