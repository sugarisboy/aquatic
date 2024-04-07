package dev.muskrat.aquatic.lib.common.events;

import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import lombok.Data;
import java.util.UUID;

/**
 * Событие о факте запуска теста из очереди
 */
@Data
public class StartedTestEvent implements AbstractTestEvent {

    private final TestInstanceDto instance;

    public StartedTestEvent(TestInstanceDto instance) {
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
