package dev.muskrat.aquatic.lib.common.declaration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * Декларация шага с фабрикой готового параметризированного шага
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class ParameterizedStepDeclaration {

    /**
     * Ссылка на основную декларацию шага
     */
    private StepDeclaration stepDeclaration;

    /**
     * Консьюмер возвращающий параметризованный шаг
     */
    @EqualsAndHashCode.Exclude
    private Supplier<Object> consumerParametrisedStep;

}
