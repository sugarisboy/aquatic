package dev.muskrat.aquatic.lib.common.events.logic.impl;

import dev.muskrat.aquatic.lib.common.dto.ScreenshotDto;
import dev.muskrat.aquatic.lib.common.events.AddInQueueTestEvent;
import dev.muskrat.aquatic.lib.common.events.FinishedStepEvent;
import dev.muskrat.aquatic.lib.common.events.FinishedTestEvent;
import dev.muskrat.aquatic.lib.common.events.StartedStepEvent;
import dev.muskrat.aquatic.lib.common.events.StartedTestEvent;
import dev.muskrat.aquatic.lib.common.events.logic.EventProducer;
import dev.muskrat.aquatic.lib.common.events.logic.EventService;
import dev.muskrat.aquatic.lib.common.execution.StepInstance;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import dev.muskrat.aquatic.lib.common.mapper.MainMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class EventProducerImpl implements EventProducer {

    private final EventService eventService;
    private final MainMapper mapper;

    @Override
    public void startTest(TestInstance<?> instance) {
        eventService.publish(
                new StartedTestEvent(
                        mapper.map(instance)
                )
        );
    }

    @Override
    public void finishTest(TestInstance<?> instance) {
        eventService.publish(
                new FinishedTestEvent(
                        mapper.map(instance)
                )
        );
    }

    @Override
    public void addTestInQueue(TestInstance<?> instance) {
        eventService.publish(
                new AddInQueueTestEvent(
                        mapper.map(instance)
                )
        );
    }

    @Override
    public void startStep(TestInstance<?> testInstance, StepInstance stepInstance, ScreenshotDto screenshot) {
        eventService.publish(
                new StartedStepEvent(
                        mapper.map(testInstance),
                        mapper.map(stepInstance),
                        screenshot
                )
        );
    }

    @Override
    public void finishStep(TestInstance<?> testInstance, StepInstance stepInstance, ScreenshotDto screenshot) {
        eventService.publish(
                new FinishedStepEvent(
                        mapper.map(testInstance),
                        mapper.map(stepInstance),
                        screenshot
                )
        );
    }

    @Override
    public void skipStep(TestInstance<?> testInstance, StepInstance step, ScreenshotDto screenshotDto) {
        this.finishStep(testInstance, step, screenshotDto);
    }
}
