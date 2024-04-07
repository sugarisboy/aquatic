package dev.muskrat.aquatic.lib.common.execution.logic;

import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import java.util.List;
import java.util.Optional;

/**
 * Набор потоков, которые способны исполнять тесты.
 * <p/>
 * Реализует очередь из готовых к запуску тестов,
 * позволяет регулировать нагрузку на тесты
 */
public interface TestPoolExecutor {

    /**
     * Добавляет декларацию теста в очередь запуска тестов
     * @param testDeclaration декларация
     */
    void addToQueue(TestDeclaration testDeclaration);

    /**
     * Возвращает текущую очередь из тестов, которые еще не были запущены и ожидают начала исполнения
     * @return неизменяемый список деклараций тестов
     */
    List<TestDeclaration> getQueue();

    /**
     * Возвращает текущий размер очереди из тестов, которые еще не были запущены и ожидают начала исполнения
     * @return размер очереди
     */
    int getQueueSize();
}
