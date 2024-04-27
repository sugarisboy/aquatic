package dev.muskrat.aquatic.lib.common.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Экземпляр шага в конкретном запуске теста
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class StepInstanceDto {

    private StepStatus status;
    private StepDeclarationDto declaration;
}
