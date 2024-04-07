package dev.muskrat.aquatic.lib.common.execution;

import com.google.common.collect.ImmutableList;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.dto.TestStatus;
import java.util.List;
import java.util.UUID;

public class TestInstanceImpl<C> implements TestInstance<C> {

    private final C context;
    private TestStatus status;
    private final List<StepInstance> stepInstances;
    private UUID id;
    private final TestDeclaration declaration;

    public TestInstanceImpl(List<StepInstance> stepInstances, C context, TestDeclaration declaration) {
        this.id = UUID.randomUUID();
        this.stepInstances = stepInstances;
        this.context = context;
        this.declaration = declaration;
        this.status = TestStatus.IN_QUEUE;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public C getContext() {
        return context;
    }

    @Override
    public List<StepInstance> getSteps() {
        return ImmutableList.copyOf(stepInstances);
    }

    @Override
    public TestStatus getStatus() {
        return status;
    }

    @Override
    public TestDeclaration getDeclaration() {
        return declaration;
    }

    @Override
    public void inProgress() {
        if (status != TestStatus.IN_QUEUE) {
            throw new IllegalStateException("Статус IN_PROGRESS может быть запущен только из статуса IN_QUEUE");
        }
        this.status = TestStatus.IN_PROGRESS;
    }

    @Override
    public void failure() {
        if (status != TestStatus.IN_PROGRESS) {
            throw new IllegalStateException("Статус IN_PROGRESS может быть запущен только из статуса FAILURE");
        }
        this.status = TestStatus.FAILURE;
    }

    @Override
    public void success() {
        if (status != TestStatus.IN_PROGRESS) {
            throw new IllegalStateException("Статус IN_PROGRESS может быть запущен только из статуса SUCCESS");
        }
        this.status = TestStatus.SUCCESS;
    }
}
