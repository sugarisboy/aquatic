package dev.muskrat.aquatic.lib.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

/**
 * DTO (Data transfer object) для класса {@link dev.muskrat.aquatic.lib.common.declaration.TestDeclaration}
 */
@Getter
@ToString
@AllArgsConstructor
public class TestDeclarationDto {

    private final String id;
    private String name;
    private String description;
    private String contextType;
    private List<StepDeclarationDto> steps;

}
