package dev.muskrat.aquatic.lib.common.events;

import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import lombok.Data;
import java.util.UUID;

/**
 * Событие о факте добавления теста в очередь
 */
public class AddInQueueTestEvent implements AbstractTestEvent {

    private final TestInstanceDto instance;

    public AddInQueueTestEvent(TestInstanceDto instance) {
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
