package dev.muskrat.aquatic.lib.common.annotations;

import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Помечает метод в классе исполняем для сценария шага.
 * <p/>
 * Должен быть размешен один и только один внутри класса, который был аннотирован {@link AquaticStep}.
 * В случае нескольких методов с данной аннотацией в одном классе будет вызван случайный.
 * <p/>
 * Метод должен принимать объект контекста, который характерен для соответствующего теста.
 * При вызове метода контекст передастся из {@link TestInstance#getContext() }
 *
 * @see AquaticStep
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AquaticStepExecution {
}
