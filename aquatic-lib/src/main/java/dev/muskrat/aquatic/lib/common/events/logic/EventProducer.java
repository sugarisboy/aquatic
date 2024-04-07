package dev.muskrat.aquatic.lib.common.events.logic;

import dev.muskrat.aquatic.lib.common.execution.TestInstance;

/**
 * Порождает события для {@link EventService} из объектов исполнения теста
 */
public interface EventProducer {

    void startTest(TestInstance<?> instance);
    void finishTest(TestInstance<?> instance);
}
