package dev.muskrat.aquatic.lib.common.events;

import java.util.UUID;

/**
 * Базовый класс события для событий фреймворка
 */
public interface AquaticBaseEvent {

    /**
     * @return ID теста для конкретного запуска
     */
    UUID getTestExecutionId();
}
