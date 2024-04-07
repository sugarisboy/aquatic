package dev.muskrat.aquatic.lib.common.execution;

import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.dto.TestStatus;
import java.util.List;
import java.util.UUID;

public interface TestInstance<CONTEXT_TYPE> {

    UUID getId();

    CONTEXT_TYPE getContext();

    List<StepInstance> getSteps();

    TestStatus getStatus();

    void inProgress();

    void failure();

    void success();

    TestDeclaration getDeclaration();
}
