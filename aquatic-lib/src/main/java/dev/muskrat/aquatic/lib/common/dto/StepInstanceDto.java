package dev.muskrat.aquatic.lib.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Экземпляр шага в конкретном запуске теста
 */
@Getter
@AllArgsConstructor
public class StepInstanceDto {

    private StepStatus status;
    private StepDeclarationDto declaration;
}
