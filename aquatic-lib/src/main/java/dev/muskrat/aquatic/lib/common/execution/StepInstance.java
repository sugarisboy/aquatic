package dev.muskrat.aquatic.lib.common.execution;

import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.dto.StepStatus;

public interface StepInstance {

    StepDeclaration getDeclaration();

    StepStatus getStatus();

    void inProgress();

    void failure();

    void success();

    void executeStep();

    void skipped();

    boolean isFinished();
}
