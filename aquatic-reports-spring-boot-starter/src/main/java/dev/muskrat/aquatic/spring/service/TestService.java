package dev.muskrat.aquatic.spring.service;

import dev.muskrat.aquatic.lib.common.dto.StepInstanceDto;
import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import dev.muskrat.aquatic.spring.dto.TestResultDto;
import java.util.UUID;

public interface TestService {

    void createInstance(TestInstanceDto instance);

    TestResultDto getResultByExecutionId(UUID executionId);

    void start(TestInstanceDto test);

    void finish(TestInstanceDto test);
}
