package dev.muskrat.aquatic.lib.common.events.logic.impl;

import dev.muskrat.aquatic.lib.common.events.AbstractTestEvent;
import dev.muskrat.aquatic.lib.common.events.logic.EventService;
import dev.muskrat.aquatic.lib.common.exception.AquaticEventPublishingException;
import lombok.extern.slf4j.Slf4j;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Slf4j
public class EventServiceImpl implements EventService {

    private final ConcurrentHashMap<Class<? extends AbstractTestEvent>, List<Consumer<AbstractTestEvent>>> handlers = new ConcurrentHashMap<>();

    @Override
    public void publish(AbstractTestEvent event) {
        if (event == null) {
            throw new NullPointerException("Event can't be is null");
        }

        Class<?> eventClass = event.getClass();

        while (eventClass != null) {
            callForClass(eventClass, event);

            if (eventClass != AbstractTestEvent.class) {
                eventClass = eventClass.getSuperclass();
            }
        }

        Class<?>[] interfaces = event.getClass().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            callForClass(anInterface, event);
        }
    }

    private void callForClass(Class<?> eventClass, AbstractTestEvent event) {
        log.debug("Call event handlers for event = {} and class = {}", event.getClass().getSimpleName(), eventClass.getSimpleName());
        for (Consumer<AbstractTestEvent> consumer : handlers.getOrDefault(eventClass, Collections.emptyList())) {
            try {
                consumer.accept(event);
            } catch (Exception e) {
                throw new AquaticEventPublishingException(String.format("Failed publish event for testId = %s", event.getTestExecutionId()), e);
            }
        }
    }

    @Override
    public <T extends AbstractTestEvent> void registerHandler(Class<T> eventType, Consumer<T> handler) {
        List<Consumer<AbstractTestEvent>> consumers;
        if (handlers.containsKey(eventType)) {
            consumers = handlers.get(eventType);
        } else {
            consumers = new CopyOnWriteArrayList<>();
            handlers.put(eventType, consumers);
        }

        consumers.add((Consumer<AbstractTestEvent>) handler);

        log.info("Зарегистрирован обработчик для {}", eventType);
    }
}
