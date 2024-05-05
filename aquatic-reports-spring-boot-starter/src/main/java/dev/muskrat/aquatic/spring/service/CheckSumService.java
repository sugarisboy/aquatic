package dev.muskrat.aquatic.spring.service;

import dev.muskrat.aquatic.lib.common.dto.StepDeclarationDto;
import dev.muskrat.aquatic.lib.common.dto.StepInstanceDto;
import dev.muskrat.aquatic.lib.common.dto.TestDeclarationDto;

public abstract class CheckSumService {

    public abstract int calculateCheckSum(TestDeclarationDto declaration);

    public abstract int calculateCheckSum(StepDeclarationDto declaration);
}
