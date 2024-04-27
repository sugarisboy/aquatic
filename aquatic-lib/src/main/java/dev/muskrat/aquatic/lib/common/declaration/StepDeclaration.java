package dev.muskrat.aquatic.lib.common.declaration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.lang.reflect.Method;

/**
 * Модель декларации шага теста
 * <p/>
 * Используется для хранения в {@link dev.muskrat.aquatic.lib.common.declaration.logic.DeclarationStorage}
 * и последующего интерпретирования в {@link dev.muskrat.aquatic.lib.common.execution.StepInstance} через
 * {@link dev.muskrat.aquatic.lib.common.execution.logic.TestInstanceFactory}
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
public class StepDeclaration {

    /**
     * ID декларации шага, задается в {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}
     */
    private final String id;

    /**
     * Человеческое имя декларации шага, задается в {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}
     */
    private String name;

    /**
     * Условие начала шага, задается в {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}
     */
    private String preCondition;

    /**
     * Условие окончания шага, задается в {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}
     */
    private String postCondition;

    /**
     * Хранится значение {@code true} если шаг был объявлен в обособленном классе,
     * с аннотацией {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}
     */
    @EqualsAndHashCode.Exclude
    private boolean isAnnouncedAsClass;

    /**
     * Тип контекста
     */
    @EqualsAndHashCode.Exclude
    private Class<?> contextType;

    /**
     * Ссылка на исполняемый метод данного шага
     */
    @EqualsAndHashCode.Exclude
    private transient Method executionMethod;

    /**
     * Ссылка на созданный инстанс класса, где размещен метод {@link #executionMethod}
     */
    @EqualsAndHashCode.Exclude
    private transient Object instanceOfClass;

}
