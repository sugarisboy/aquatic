package dev.muskrat.aquatic.lib.common.events;

import dev.muskrat.aquatic.lib.common.dto.ScreenshotDto;
import dev.muskrat.aquatic.lib.common.dto.StepInstanceDto;
import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import lombok.Data;
import java.util.UUID;

/**
 * Событие о факте запуска шага внутри теста
 */
public class StartedStepEvent implements AbstractStepEvent {

    private final TestInstanceDto testInstance;
    private final StepInstanceDto instance;
    private final ScreenshotDto screenshot;

    public StartedStepEvent(TestInstanceDto testInstance, StepInstanceDto instance, ScreenshotDto screenshot) {
        this.testInstance = testInstance;
        this.instance = instance;
        this.screenshot = screenshot;
    }

    @Override
    public StepInstanceDto getStep() {
        return instance;
    }

    @Override
    public TestInstanceDto getTest() {
        return testInstance;
    }

    @Override
    public UUID getTestExecutionId() {
        return testInstance.getExecutionId();
    }
}
