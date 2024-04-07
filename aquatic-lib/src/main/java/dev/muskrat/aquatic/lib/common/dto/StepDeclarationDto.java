package dev.muskrat.aquatic.lib.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * DTO (Data transfer object) для класса {@link dev.muskrat.aquatic.lib.common.declaration.StepDeclaration}
 */
@Getter
@ToString
@AllArgsConstructor
public class StepDeclarationDto {

    private final String id;
    private String name;
    private String preCondition;
    private String postCondition;
    private String contextType;

}
