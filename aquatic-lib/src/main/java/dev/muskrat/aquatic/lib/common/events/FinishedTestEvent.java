package dev.muskrat.aquatic.lib.common.events;

import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import java.util.UUID;

/**
 * Событие о факте завершения теста
 */
public class FinishedTestEvent implements AbstractTestEvent {

    private final TestInstanceDto instance;

    public FinishedTestEvent(TestInstanceDto instance) {
        this.instance = instance;
    }

    @Override
    public TestInstanceDto getInstance() {
        return instance;
    }

    @Override
    public UUID getTestExecutionId() {
        return instance.getExecutionId();
    }
}
