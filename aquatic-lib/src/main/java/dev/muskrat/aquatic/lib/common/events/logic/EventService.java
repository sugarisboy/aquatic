package dev.muskrat.aquatic.lib.common.events.logic;

import dev.muskrat.aquatic.lib.common.events.AbstractTestEvent;
import dev.muskrat.aquatic.lib.common.events.AquaticBaseEvent;
import java.util.function.Consumer;

/**
 * Простая Event система для реализации дополнительной логики по обработке событий.
 * <p/>
 * Пример использования: Реализация хранения результатов теста в MongoDB с последующим анализом.
 * <p/>
 * Возможные события можно посмотреть в наследниках {@link AquaticBaseEvent},
 * для добавления собственных событий необходимо наследоваться от этого класса
 */
public interface EventService {

    /**
     * Публикует событие для событийной системы
     * @param event событие
     */
    void publish(AquaticBaseEvent event);

    /**
     * Добавляет обработчик события
     * @param eventType тип обрабатываемого события
     * @param handler обработчик
     * @param <T> тип события
     */
    <T extends AquaticBaseEvent> void  registerHandler(Class<T> eventType, Consumer<T> handler);
}
