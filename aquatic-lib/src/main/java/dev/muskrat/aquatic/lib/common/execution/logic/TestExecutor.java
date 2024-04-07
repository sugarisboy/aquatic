package dev.muskrat.aquatic.lib.common.execution.logic;

import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;

/**
 * Исполнитель запуска теста
 * <p/>
 * Не требуется ручной вызов, вызывается через {@link TestPoolExecutor}
 */
public interface TestExecutor {

    /**
     * Метод запуска теста
     * @param testDeclaration декларация теста
     */
    void execute(TestDeclaration testDeclaration);
}
