package dev.muskrat.aquatic.spring.listener;

import dev.muskrat.aquatic.lib.common.events.AddInQueueTestEvent;
import dev.muskrat.aquatic.lib.common.events.AquaticBaseEvent;
import dev.muskrat.aquatic.lib.common.events.FinishedStepEvent;
import dev.muskrat.aquatic.lib.common.events.FinishedTestEvent;
import dev.muskrat.aquatic.lib.common.events.StartedStepEvent;
import dev.muskrat.aquatic.lib.common.events.StartedTestEvent;
import dev.muskrat.aquatic.spring.service.StepService;
import dev.muskrat.aquatic.spring.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AquaticEventListener {

    private final TestService testService;
    private final StepService stepService;

    @EventListener
    public void handleAddInQueue(AddInQueueTestEvent event) {
        testService.createInstance(event.getInstance());
    }

    @EventListener
    public void handleFinishTest(FinishedTestEvent event) {

    }

    @EventListener
    public void handleStartEvent(StartedTestEvent event) {

    }

    @EventListener
    public void handleStartStepEvent(StartedStepEvent event) {
        stepService.start(event.getStep(), event.getTest());
    }

    @EventListener
    public void handleFinishStepEvent(FinishedStepEvent event) {
        stepService.finish(event.getStep(), event.getTest());
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @EventListener
    public void handleFinishStepEvent(AquaticBaseEvent event) {
        log.info("Spring handle event: {}", event);
    }
}
