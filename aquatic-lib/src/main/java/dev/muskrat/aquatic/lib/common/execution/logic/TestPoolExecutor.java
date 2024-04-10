package dev.muskrat.aquatic.lib.common.execution.logic;

import com.google.common.collect.ImmutableList;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import java.util.UUID;

/**
 * Набор потоков, которые способны исполнять тесты.
 * <p/>
 * Реализует очередь из готовых к запуску тестов,
 * позволяет регулировать нагрузку на тесты
 */
public interface TestPoolExecutor {

    /**
     * Добавляет декларацию теста в очередь запуска тестов
     *
     * @param testDeclaration декларация
     * @return ID тест рана для дальнейшего отслеживания
     */
    UUID addToQueue(TestDeclaration testDeclaration);

    /**
     * Возвращает текущую очередь из тестов, которые еще не были запущены и ожидают начала исполнения
     *
     * @return неизменяемый список деклараций тестов
     */
    ImmutableList<TestInstance<?>> getQueue();

    /**
     * Возвращает текущий размер очереди из тестов, которые еще не были запущены и ожидают начала исполнения
     * @return размер очереди
     */
    int getQueueSize();
}
