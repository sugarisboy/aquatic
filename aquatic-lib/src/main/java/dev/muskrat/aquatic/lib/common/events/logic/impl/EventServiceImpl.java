package dev.muskrat.aquatic.lib.common.events.logic.impl;

import dev.muskrat.aquatic.lib.common.events.AbstractTestEvent;
import dev.muskrat.aquatic.lib.common.events.AquaticBaseEvent;
import dev.muskrat.aquatic.lib.common.events.logic.EventService;
import dev.muskrat.aquatic.lib.common.exception.AquaticEventPublishingException;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Slf4j
public class EventServiceImpl implements EventService {

    private final ConcurrentHashMap<Class<? extends AquaticBaseEvent>, List<Consumer<AquaticBaseEvent>>> handlers = new ConcurrentHashMap<>();

    @Override
    public void publish(AquaticBaseEvent event) {
        if (event == null) {
            throw new NullPointerException("Event can't be is null");
        }

        getAllSuperClassesAndInterfaces(event.getClass()).forEach(tClass -> callForClass(tClass, event));
    }

    private void callForClass(Class<?> eventClass, AquaticBaseEvent event) {
        log.debug("Call event handlers for event = {} and class = {}", event.getClass().getSimpleName(), eventClass.getSimpleName());
        for (Consumer<AquaticBaseEvent> consumer : handlers.getOrDefault(eventClass, Collections.emptyList())) {
            try {
                consumer.accept(event);
            } catch (Exception e) {
                throw new AquaticEventPublishingException(String.format("Failed publish event for testId = %s", event.getTestExecutionId()), e);
            }
        }
    }

    @Override
    public <T extends AquaticBaseEvent> void registerHandler(Class<T> eventType, Consumer<T> handler) {
        List<Consumer<AquaticBaseEvent>> consumers;
        if (handlers.containsKey(eventType)) {
            consumers = handlers.get(eventType);
        } else {
            consumers = new CopyOnWriteArrayList<>();
            handlers.put(eventType, consumers);
        }

        consumers.add((Consumer<AquaticBaseEvent>) handler);

        log.info("Зарегистрирован обработчик для {}", eventType);
    }

    private Set<Class<?>> getAllSuperClassesAndInterfaces(Class<?> tClass) {
        Set<Class<?>> result = new HashSet<>();
        Set<Class<?>> currentLevel = new HashSet<>();
        currentLevel.add(tClass);

        while (!currentLevel.isEmpty()) {
            Set<Class<?>> nextLevel = new HashSet<>();
            for (Class<?> currentClass : currentLevel) {
                result.add(currentClass);
                Type[] interfaces = currentClass.getGenericInterfaces();
                for (Type type : interfaces) {
                    if (type instanceof Class) {
                        nextLevel.add((Class<?>) type);
                    }
                }

                Class<?> superClass = currentClass.getSuperclass();
                if (superClass != null) {
                    nextLevel.add(superClass);
                }
            }
            currentLevel = nextLevel;
        }

        return result;
    }
}
