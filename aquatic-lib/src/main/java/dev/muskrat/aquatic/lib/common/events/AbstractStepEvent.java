package dev.muskrat.aquatic.lib.common.events;

import dev.muskrat.aquatic.lib.common.dto.StepInstanceDto;
import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import java.util.UUID;

/**
 * Базовый класс события для шагов тестов
 */
public interface AbstractStepEvent extends AquaticBaseEvent {

    /**
     * @return экземпляр шага для конкретного запуска
     */
    StepInstanceDto getStep();

    /**
     * @return экземпляр теста для конкретного запуска
     */
    TestInstanceDto getTest();

}
