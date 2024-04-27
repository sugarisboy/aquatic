package dev.muskrat.aquatic.spring.service;

import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;

public interface TestService {

    void createInstance(TestInstanceDto instance);
}
