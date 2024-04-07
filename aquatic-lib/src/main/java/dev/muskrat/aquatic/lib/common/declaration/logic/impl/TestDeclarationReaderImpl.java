package dev.muskrat.aquatic.lib.common.declaration.logic.impl;

import static dev.muskrat.aquatic.lib.utils.AnnotationUtils.findAnnotatedMethod;
import static dev.muskrat.aquatic.lib.utils.AnnotationUtils.findClassAnnotation;
import static dev.muskrat.aquatic.lib.utils.AnnotationUtils.findMethodsWithAnnotation;

import dev.muskrat.aquatic.lib.common.annotations.AquaticStep;
import dev.muskrat.aquatic.lib.common.annotations.AquaticStepExecution;
import dev.muskrat.aquatic.lib.common.annotations.AquaticTestTemplate;
import dev.muskrat.aquatic.lib.common.AquaticStepHandler;
import dev.muskrat.aquatic.lib.common.declaration.logic.TestDeclarationReader;
import dev.muskrat.aquatic.lib.common.exception.AquaticTestTemplateInitializingException;
import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.declaration.TestTemplate;
import dev.muskrat.aquatic.lib.utils.AnnotationUtils;
import dev.muskrat.aquatic.lib.utils.LambdaUtils;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TestDeclarationReaderImpl implements TestDeclarationReader {
    @Override
    public List<TestDeclaration> loadDeclaration(Object instanceOfClass, List<StepDeclaration> steps) {
        if (instanceOfClass == null) {
            return Collections.emptyList();
        }

        return findMethodsWithAnnotation(instanceOfClass, AquaticTestTemplate.class)
                .stream()
                .map(method -> declarate(instanceOfClass, method, steps))
                .collect(Collectors.toList());
    }

    private TestDeclaration declarate(Object instanceOfClass, AnnotationUtils.AnnotatedMethod<AquaticTestTemplate> annotatedMethod, List<StepDeclaration> steps) {
        Method method = annotatedMethod.method();
        AquaticTestTemplate annotation = annotatedMethod.annotation();

        if (!method.getReturnType().equals(TestTemplate.class)) {
            throw new AquaticTestTemplateInitializingException("Метод шаблона должен возвращать инстанс класса " + TestTemplate.class.getSimpleName());
        }

        int parametersCount = method.getParameters().length;
        if (parametersCount != 0) {
            throw new AquaticTestTemplateInitializingException("Метод " + method.getName() + " имеет лишние параметры");
        }

        TestTemplate<?> testTemplate = invoke(instanceOfClass, method);
        Class<?> contextType = testTemplate.getType();

        List<? extends AquaticStepHandler<?>> stepRunnables = testTemplate.getStepRunnables();

        int argNumber = 0;
        List<StepDeclaration> testStepDeclarations = new ArrayList<>();
        for (AquaticStepHandler<?> stepRunnable : stepRunnables) {
            argNumber++;

            Method stepMethod = LambdaUtils.unreferenceLambdaMethod(stepRunnable);
            if (stepMethod == null || stepMethod.getName().contains("lambda$")) {
                throw new AquaticTestTemplateInitializingException(
                        String.format(
                                "Ошибка инициализации шаблона теста %s. Вероятно %d вызов метода then(..) в качестве аргумента получил не methodReference",
                                annotation.id(), argNumber
                        )
                );
            }

            boolean isStatic = Modifier.isStatic(stepMethod.getModifiers());

            if (isStatic) {
                Class<?> stepClass = stepMethod.getDeclaringClass();
                AquaticStep aquaticStep = findClassAnnotation(stepClass, AquaticStep.class)
                        .orElseThrow(() -> new AquaticTestTemplateInitializingException("Ошибка инициализации шаблона теста: Статичный methodReference не помечен @" + AquaticStep.class.getSimpleName()));

                Method stepClassMethod = findAnnotatedMethod(stepClass, AquaticStepExecution.class)
                        .orElseThrow(() -> new AquaticTestTemplateInitializingException("Не найден метод обработки логики шага с @" + AquaticStepExecution.class.getSimpleName()))
                        .method();

                StepDeclaration declaration = steps.stream()
                        .filter(stepDeclaration -> stepDeclaration.getExecutionMethod().equals(stepClassMethod))
                        .findFirst()
                        .orElseThrow(() -> new AquaticTestTemplateInitializingException(
                                String.format(
                                        "Не найдена декларация шага: %s. Проверьте наличие @%s над методом шага для теста %s",
                                        stepMethod.getName(), AquaticStep.class.getSimpleName(), annotation.id()
                                )
                        ));
                testStepDeclarations.add(declaration);

            } else {
                StepDeclaration declaration = steps.stream()
                        .filter(stepDeclaration -> stepDeclaration.getExecutionMethod().equals(stepMethod))
                        .findFirst()
                        .orElseThrow(() -> new AquaticTestTemplateInitializingException(
                                String.format(
                                        "Не найдена декларация шага: %s. Проверьте наличие @%s над методом шага для теста %s",
                                        stepMethod.getName(), AquaticStep.class.getSimpleName(), annotation.id()
                                )
                        ));
                testStepDeclarations.add(declaration);
            }


        }

        return TestDeclaration.builder()
                .id(annotation.id())
                .name(annotation.name())
                .name(annotation.description())
                .contextType(contextType)
                .steps(testStepDeclarations)
                .build();
    }

    private TestTemplate<?> invoke(Object object, Method method) {
        try {
            return (TestTemplate<?>) method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AquaticTestTemplateInitializingException("Ошибка инициализации шаблона теста: " + e, e);
        }
    }
}
