package dev.muskrat.aquatic.example.listener;

import dev.muskrat.aquatic.lib.common.events.AbstractTestEvent;
import dev.muskrat.aquatic.lib.common.events.FinishedTestEvent;
import dev.muskrat.aquatic.lib.common.events.StartedTestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AquaticTestExampleListener {

    @EventListener
    public void handleStartTest(StartedTestEvent event) {
        log.info("--- START TEST {} ---", event.getInstance().getDeclaration().getName());
    }

    @EventListener
    public void handleFinishTest(FinishedTestEvent event) {
        log.info("--- FINISH TEST {} ---", event.getInstance().getDeclaration().getName());
    }

    @EventListener
    public void handleFinishTest(AbstractTestEvent event) {
        log.info("Example test event for event: {}", event.getInstance().getDeclaration().getName());
    }
}
