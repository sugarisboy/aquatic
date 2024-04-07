package dev.muskrat.aquatic.lib.common.events;

import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import java.util.UUID;

/**
 * Базовый класс события для тестов
 */
public interface AbstractTestEvent {

    /**
     * @return экземпляр теста для конкретного запуска
     */
    TestInstanceDto getInstance();

    /**
     * @return ID теста для конкретного запуска
     */
    UUID getTestExecutionId();
}
