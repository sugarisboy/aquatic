package dev.muskrat.aquatic.lib.common.mapper;

import dev.muskrat.aquatic.lib.common.execution.StepInstance;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.dto.StepDeclarationDto;
import dev.muskrat.aquatic.lib.common.dto.StepInstanceDto;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.dto.TestDeclarationDto;
import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;

/**
 * Маппинг объектов в DTO
 */
public interface MainMapper {

    TestInstanceDto map(TestInstance<?> instance);

    StepInstanceDto map(StepInstance instance);

    StepDeclarationDto map(StepDeclaration declaration);

    TestDeclarationDto map(TestDeclaration declaration);
}
