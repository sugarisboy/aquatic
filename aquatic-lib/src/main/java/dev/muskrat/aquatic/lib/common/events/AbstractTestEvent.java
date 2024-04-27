package dev.muskrat.aquatic.lib.common.events;

import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;

/**
 * Базовый класс события для тестов
 */
public interface AbstractTestEvent extends AquaticBaseEvent {

    /**
     * @return экземпляр теста для конкретного запуска
     */
    TestInstanceDto getInstance();
}
