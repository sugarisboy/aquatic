package dev.muskrat.aquatic.lib.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Экземпляр теста в для конкретного запуска
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class TestInstanceDto {

    public UUID executionId;
    public TestStatus status;

    @Builder.Default
    public List<StepInstanceDto> steps = new ArrayList<>();

    private TestDeclarationDto declaration;
}
