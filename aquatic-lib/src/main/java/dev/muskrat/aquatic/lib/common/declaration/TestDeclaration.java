package dev.muskrat.aquatic.lib.common.declaration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.List;
import java.util.Objects;

/**
 * Модель декларации теста
 * <p/>
 * Используется для хранения в {@link dev.muskrat.aquatic.lib.common.declaration.logic.DeclarationStorage}
 * и последующего интерпретирования в {@link dev.muskrat.aquatic.lib.common.execution.TestInstance} через
 * {@link dev.muskrat.aquatic.lib.common.execution.logic.TestInstanceFactory}
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class TestDeclaration {


    /**
     * ID декларации теста
     */
    private final String id;

    /**
     * Имя декларации теста
     */
    private String name;

    /**
     * Описание теста
     */
    private String description;

    /**
     * Тип контекста
     */
    @EqualsAndHashCode.Exclude
    private Class<?> contextType;

    /**
     * Декларация последовательности шагов данного теста
     */
    private List<ParameterizedStepDeclaration> steps;

}
