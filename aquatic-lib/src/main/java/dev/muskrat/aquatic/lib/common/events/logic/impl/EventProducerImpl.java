package dev.muskrat.aquatic.lib.common.events.logic.impl;

import dev.muskrat.aquatic.lib.common.events.StartedTestEvent;
import dev.muskrat.aquatic.lib.common.events.logic.EventProducer;
import dev.muskrat.aquatic.lib.common.events.logic.EventService;
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
                new StartedTestEvent(
                        mapper.map(instance)
                )
        );
    }
}
