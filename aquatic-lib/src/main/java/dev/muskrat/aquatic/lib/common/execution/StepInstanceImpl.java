package dev.muskrat.aquatic.lib.common.execution;

import dev.muskrat.aquatic.lib.common.execution.StepInstance;
import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.dto.StepStatus;
import lombok.Getter;

public class StepInstanceImpl implements StepInstance {

    @Getter
    private final StepDeclaration declaration;
    private final Runnable runnable;

    @Getter
    private StepStatus status;

    public StepInstanceImpl(StepDeclaration declaration, Runnable runnable) {
        this.declaration = declaration;
        this.runnable = runnable;
        this.status = StepStatus.IN_QUEUE;
    }

    @Override
    public void inProgress() {
        if (status != StepStatus.IN_QUEUE) {
            throw new IllegalStateException("Статус IN_PROGRESS может быть запущен только из статуса IN_QUEUE");
        }
        this.status = StepStatus.IN_PROGRESS;
    }

    @Override
    public void failure() {
        if (status != StepStatus.IN_PROGRESS) {
            throw new IllegalStateException("Статус IN_PROGRESS может быть запущен только из статуса FAILURE");
        }
        this.status = StepStatus.FAILURE;
    }

    @Override
    public void success() {
        if (status != StepStatus.IN_PROGRESS) {
            throw new IllegalStateException("Статус IN_PROGRESS может быть запущен только из статуса SUCCESS");
        }
        this.status = StepStatus.SUCCESS;
    }

    @Override
    public void skipped() {
        if (status != StepStatus.IN_QUEUE) {
            throw new IllegalStateException("Статус SKIPPED может быть запущен только из статуса IN_QUEUE");
        }
        this.status = StepStatus.SKIPPED;
    }

    @Override
    public void executeStep() {
        runnable.run();
    }
}
