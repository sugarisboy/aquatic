package dev.muskrat.aquatic.lib.utils;

import static java.util.Objects.requireNonNull;

import lombok.experimental.UtilityClass;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class AnnotationUtils {

    public static <T> Optional<T> findClassAnnotation(Class<?> aClass, Class<T> tClass) {
        return (Optional<T>) Arrays.stream(ultimateTargetClass(aClass).getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(tClass))
                .findFirst();
    }

    public static <T> Optional<T> findClassAnnotation(Object object, Class<T> tClass) {
        if (object == null) {
            return Optional.empty();
        }

        return findClassAnnotation(object.getClass(), tClass);
    }

    public static <T> Optional<AnnotatedField<T>> findAnnotatedField(Object object, Class<T> tClass) {
        if (object == null) {
            return Optional.empty();
        }

        Class<?> aClass = object.getClass();
        return Arrays.stream(aClass.getDeclaredFields())
                .filter(field -> findFieldAnnotation(field, tClass).isPresent())
                .map(field -> new AnnotatedField<>(field, findFieldAnnotation(field, tClass).get()))
                .findFirst();
    }

    public static <T> Optional<AnnotatedMethod<T>> findAnnotatedMethod(Object object, Class<T> tClass) {
        if (object == null) {
            return Optional.empty();
        }

        Class<?> aClass = object.getClass();
        return findAnnotatedMethod(aClass, tClass);
    }

    public static <T> Optional<AnnotatedMethod<T>> findAnnotatedMethod(Class<?> aClass, Class<T> tClass) {
        return Arrays.stream(ultimateTargetClass(aClass).getDeclaredMethods())
                .filter(method -> findMethodAnnotation(method, tClass).isPresent())
                .map(method -> new AnnotatedMethod<>(method, findMethodAnnotation(method, tClass).get()))
                .findFirst();
    }

    public static <T> Optional<T> findMethodAnnotation(Method method, Class<T> tClass) {
        if (method == null) {
            return Optional.empty();
        }

        return (Optional<T>) Arrays.stream(method.getDeclaredAnnotations())
                .filter(annotation -> annotation.annotationType().equals(tClass))
                .findFirst();
    }

    public static <T> Optional<T> findFieldAnnotation(Field field, Class<T> tClass) {
        if (field == null) {
            return Optional.empty();
        }

        return (Optional<T>) Arrays.stream(field.getDeclaredAnnotations())
                .filter(annotation -> annotation.annotationType().equals(tClass))
                .findFirst();
    }

    public static <T> List<AnnotatedMethod<T>> findMethodsWithAnnotation(Object instanceOfClass, Class<T> annotation) {
        if (instanceOfClass == null) {
            return Collections.emptyList();
        }

        Class<?> aClass = ultimateTargetClass(instanceOfClass.getClass());
        return Arrays.stream(aClass.getDeclaredMethods())
                .filter(method -> findMethodAnnotation(method, annotation).isPresent())
                .map(method -> new AnnotatedMethod<>(method, findMethodAnnotation(method, annotation).get()))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает исходный класс в случае проксирования
     * @param tClass Исходный класс или прокси
     * @return Исходный класс
     */
    private static Class<?> ultimateTargetClass(Class<?> tClass) {
        while (tClass != null && tClass.getName().contains("$$")) {
            tClass = tClass.getSuperclass();
        }

        return tClass;
    }

    public record AnnotatedMethod<T>(Method method, T annotation) {

        public AnnotatedMethod(Method method, T annotation) {
            this.method = requireNonNull(method);
            this.annotation = requireNonNull(annotation);
        }
    }

    public record AnnotatedField<T>(Field field, T annotation) {

        public AnnotatedField(Field field, T annotation) {
            this.field = requireNonNull(field);
            this.annotation = requireNonNull(annotation);
        }
    }
}
