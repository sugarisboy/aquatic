package dev.muskrat.aquatic.spring.service;

import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.dto.StepDeclarationDto;
import dev.muskrat.aquatic.lib.common.dto.StepInstanceDto;
import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import dev.muskrat.aquatic.spring.model.Step;

public interface StepService {
    Step getOrCreate(StepDeclarationDto stepDeclaration);

    void start(StepInstanceDto step, TestInstanceDto test);

    void finish(StepInstanceDto step, TestInstanceDto test);
}
