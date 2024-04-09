package dev.muskrat.aquatic.lib.common.execution.logic.impl;

import dev.muskrat.aquatic.lib.common.declaration.ParameterizedStepDeclaration;
import dev.muskrat.aquatic.lib.common.execution.StepInstance;
import dev.muskrat.aquatic.lib.common.execution.StepInstanceImpl;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import dev.muskrat.aquatic.lib.common.execution.logic.TestInstanceFactory;
import dev.muskrat.aquatic.lib.common.execution.TestInstanceImpl;
import dev.muskrat.aquatic.lib.common.exception.AquaticTestExecutionFailedException;
import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TestInstanceFactoryImpl implements TestInstanceFactory {

    @Override
    public TestInstance<?> createInstance(TestDeclaration declaration) {
        if (declaration == null) {
            throw new IllegalArgumentException("Декларация теста не может быть null");
        }

        try {
            Class<?> contextType = declaration.getContextType();
            Object context = createContext(contextType);

            List<StepInstance> stepInstances = declaration.getSteps()
                    .stream()
                    .map(stepDeclaration -> createStepInstance(stepDeclaration, context))
                    .toList();

            return new TestInstanceImpl<>(stepInstances, context, declaration);
        } catch (Exception e) {
            throw new AquaticTestExecutionFailedException("Can't be executed test: " + declaration.getId(), e);
        }
    }

    private Object createContext(Class<?> contexType) {
        try {
            Constructor<?> constructor = contexType.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Can't be found default constructor for context class: " + contexType.getSimpleName(), e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Can't be create instance of context: " + contexType.getSimpleName(), e);
        }
    }

    private StepInstance createStepInstance(ParameterizedStepDeclaration parameterizedStepDeclaration, Object context) {
        StepDeclaration stepDeclaration = parameterizedStepDeclaration.getStepDeclaration();
        Method executionMethod = stepDeclaration.getExecutionMethod();
        if (executionMethod == null) {
            throw new IllegalStateException("Execution method can't be null");
        }

        String stepId = stepDeclaration.getId();
        Object[] args;
        if (stepDeclaration.isAnnouncedAsClass() && parameterizedStepDeclaration.getConsumerParametrisedStep() != null) {
            Object stepParams = parameterizedStepDeclaration.getConsumerParametrisedStep().get();
            args = new Object[]{context, stepParams};
        } else {
            args = new Object[]{context};
        }

        Runnable runnable = invokeAsRunnable(stepId, stepDeclaration.getInstanceOfClass(), executionMethod, args);

        return new StepInstanceImpl(stepDeclaration, runnable);
    }

    // instance.method(args)
    private Runnable invokeAsRunnable(String stepId, Object instance, Method method, Object[] args) {
        return  () -> {
            try {
                method.invoke(instance, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new AquaticTestExecutionFailedException("Не может быть вызван корректно шаг " + stepId, e);
            } catch (Exception e) {
                log.error("Ошибка выполнения шага {} stepId", stepId, e);
                throw new AquaticTestExecutionFailedException("Ошибка выполнения шага " + stepId + " stepId", e);
            }
        };
    }
}
