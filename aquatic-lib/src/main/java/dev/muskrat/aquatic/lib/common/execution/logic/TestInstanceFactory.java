package dev.muskrat.aquatic.lib.common.execution.logic;

import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import dev.muskrat.aquatic.lib.common.execution.TestInstanceImpl;

/**
 * Фабрика создания экземпляра конкретного запуска теста из деклараций
 * <p/>
 * Используется {@link TestExecutor} для создания объекта,
 * который будет менять статус в рамках исполнения теста.
 */
public interface TestInstanceFactory {

    /**
     * Создать экземпляр теста из декларации
     * @param testDeclaration декларация
     * @return пустой экземпляр теста
     */
    TestInstance<?> createInstance(TestDeclaration testDeclaration);
}
