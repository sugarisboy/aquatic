package dev.muskrat.aquatic.lib.common.execution.logic;

import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;

/**
 * Исполнитель запуска теста
 * <p/>
 * Не требуется ручной вызов, вызывается через {@link TestPoolExecutor}
 */
public interface TestExecutor {

    /**
     * Метод запуска теста
     * @param testInstance инстанс теста из очереди
     */
    void execute(TestInstance<?> testInstance);
}
