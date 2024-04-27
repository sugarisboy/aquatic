package dev.muskrat.aquatic.lib.common.events.logic;

import dev.muskrat.aquatic.lib.common.dto.ScreenshotDto;
import dev.muskrat.aquatic.lib.common.execution.StepInstance;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;

/**
 * Порождает события для {@link EventService} из объектов исполнения теста
 */
public interface EventProducer {

    void startTest(TestInstance<?> instance);
    void finishTest(TestInstance<?> instance);

    void addTestInQueue(TestInstance<?> instance);

    void startStep(TestInstance<?> testInstance, StepInstance stepInstance, ScreenshotDto screenshot);

    void finishStep(TestInstance<?> testInstance, StepInstance stepInstance, ScreenshotDto screenshot);

    void skipStep(TestInstance<?> testInstance, StepInstance step, ScreenshotDto screenshotDto);
}
