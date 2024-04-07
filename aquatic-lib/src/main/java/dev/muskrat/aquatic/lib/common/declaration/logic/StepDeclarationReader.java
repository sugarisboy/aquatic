package dev.muskrat.aquatic.lib.common.declaration.logic;

import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import java.util.List;

/**
 * Считывает и анализирует необходимые классы на наличие шагов.
 * В случае необходимости порождает объекты декларации.
 */
public interface StepDeclarationReader {

    /**
     * Общий метод чтения объекта на декларации шагов
     *
     * @param instanceOfClass созданный экземпляр класса
     * @return Список проинициализированных деклараций шагов
     */
    List<StepDeclaration> loadDeclaration(Object instanceOfClass);

    /**
     * Метод чтения класса-шага на декларацию
     *
     * @param instanceOfClass созданный экземпляр класса
     * @return инициированная декларация шага
     */
    StepDeclaration loadDeclarationSomeSelf(Object instanceOfClass);

    /**
     * Метод чтения методов класса на декларацию шагов
     *
     * @param instanceOfClass созданный экземпляр класса
     * @return инициированная декларация шагов
     */
    List<StepDeclaration> loadDeclarationsFromClassMethods(Object instanceOfClass);
}
