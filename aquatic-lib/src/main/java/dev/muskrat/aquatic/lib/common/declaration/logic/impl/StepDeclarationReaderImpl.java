package dev.muskrat.aquatic.lib.common.declaration.logic.impl;

import static dev.muskrat.aquatic.lib.utils.AnnotationUtils.findAnnotatedMethod;
import static dev.muskrat.aquatic.lib.utils.AnnotationUtils.findClassAnnotation;

import dev.muskrat.aquatic.lib.common.annotations.AquaticStep;
import dev.muskrat.aquatic.lib.common.annotations.AquaticStepExecution;
import dev.muskrat.aquatic.lib.common.declaration.logic.StepDeclarationReader;
import dev.muskrat.aquatic.lib.common.exception.AquaticStepInitializingException;
import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.utils.AnnotationUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StepDeclarationReaderImpl implements StepDeclarationReader {

    @Override
    public List<StepDeclaration> loadDeclaration(Object instanceOfClass) {
        // Шаг может быть объявлен в двух вариантах, как отдельный метод и самостоятельный класс

        return isStepClass(instanceOfClass)
                ? List.of(loadDeclarationSomeSelf(instanceOfClass))
                : loadDeclarationsFromClassMethods(instanceOfClass);
    }

    @Override
    public StepDeclaration loadDeclarationSomeSelf(Object instanceOfClass) {
        AquaticStep step = findClassAnnotation(instanceOfClass, AquaticStep.class).orElseThrow();

        Optional<AnnotationUtils.AnnotatedMethod<AquaticStepExecution>> executionMethod = findAnnotatedMethod(instanceOfClass, AquaticStepExecution.class);

        if (executionMethod.isEmpty()) {
            throw new AquaticStepInitializingException("Не найден метод, отмеченный @" + AquaticStepExecution.class.getSimpleName());
        }

        Method execution = executionMethod.get().method();
        Parameter[] parameters = execution.getParameters();
        if (parameters.length == 0) {
            throw new AquaticStepInitializingException("Не передан в метод параметр контекста");
        }
        Parameter contextParameter = parameters[0];
        Class<?> contextParameterType = contextParameter.getType();

        return new StepDeclaration(
                step.id(),
                step.name(),
                step.preCondition(),
                step.postCondition(),
                true,
                contextParameterType,
                executionMethod.get().method(),
                instanceOfClass
        );
    }

    @Override
    public List<StepDeclaration> loadDeclarationsFromClassMethods(Object instanceOfClass) {
        return AnnotationUtils.findMethodsWithAnnotation(instanceOfClass, AquaticStep.class).stream()
                .map(method -> fromAnnotatedMethod(method, instanceOfClass))
                .collect(Collectors.toList());
    }

    private StepDeclaration fromAnnotatedMethod(AnnotationUtils.AnnotatedMethod<AquaticStep> m, Object instanceOfClass) {
        AquaticStep annotation = m.annotation();
        return new StepDeclaration(
                annotation.id(),
                annotation.name(),
                annotation.preCondition(),
                annotation.postCondition(),
                false,
                m.method().getReturnType(),
                m.method(),
                instanceOfClass
        );
    }

    /**
     * Проверка на самостоятельность данного класса как шага
     *
     * @return Вернет True, если инстанс данного класса является самостоятельным шагом
     */
    private boolean isStepClass(Object object) {
        return findClassAnnotation(object, AquaticStep.class).isPresent();
    }
}
