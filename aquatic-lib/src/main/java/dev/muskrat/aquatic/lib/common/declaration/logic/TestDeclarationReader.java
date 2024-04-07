package dev.muskrat.aquatic.lib.common.declaration.logic;

import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import java.util.List;

/**
 * Считывает и анализирует необходимые классы на наличие тестовых деклараций.
 * В случае необходимости порождает объекты декларации.
 */
public interface TestDeclarationReader {

    /**
     * Общий метод чтения объекта на декларацию тестов
     *
     * @param instanceOfClass созданный экземпляр класса
     * @return Список проинициализированных деклараций тестов
     */
    List<TestDeclaration> loadDeclaration(Object instanceOfClass, List<StepDeclaration> steps);
}
