package dev.muskrat.aquatic.lib.common.mapper;

import dev.muskrat.aquatic.lib.common.execution.StepInstance;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.dto.StepDeclarationDto;
import dev.muskrat.aquatic.lib.common.dto.StepInstanceDto;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.dto.TestDeclarationDto;
import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import java.util.List;

public class MainMapperImpl implements MainMapper {

    @Override
    public TestInstanceDto map(TestInstance<?> instance) {
        List<StepInstanceDto> steps = instance.getSteps().stream()
                .map(this::map)
                .toList();

        return new TestInstanceDto(
                instance.getId(),
                instance.getStatus(),
                steps,
                map(instance.getDeclaration())
        );
    }

    @Override
    public StepInstanceDto map(StepInstance instance) {
        return new StepInstanceDto(
                instance.getStatus(),
                map(instance.getDeclaration())
        );
    }
    @Override
    public StepDeclarationDto map(StepDeclaration declaration) {
        return new StepDeclarationDto(
                declaration.getId(),
                declaration.getName(),
                declaration.getPreCondition(),
                declaration.getPostCondition(),
                declaration.getContextType().getSimpleName()
        );
    }

    @Override
    public TestDeclarationDto map(TestDeclaration declaration) {
        List<StepDeclarationDto> steps = declaration.getSteps().stream()
                .map(this::map)
                .toList();

        return new TestDeclarationDto(
                declaration.getId(),
                declaration.getName(),
                declaration.getDescription(),
                declaration.getContextType().getSimpleName(),
                steps
        );
    }
}
