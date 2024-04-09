package dev.muskrat.aquatic.lib.common.declaration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

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
    private Class<?> contextType;

    /**
     * Декларация последовательности шагов данного теста
     */
    private List<ParameterizedStepDeclaration> steps;

}
