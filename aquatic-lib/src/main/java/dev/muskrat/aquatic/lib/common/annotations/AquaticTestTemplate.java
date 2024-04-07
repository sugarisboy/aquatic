package dev.muskrat.aquatic.lib.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотирует методы, которые возвращают готовые тестовые шаблоны.
 * <p/>
 * Аннотированный метод должен возвращать объект типа {@link dev.muskrat.aquatic.lib.common.declaration.TestTemplate }
 * <p/>
 * Пример аннотации:
 * <pre>
 * @ {@code AquaticTestTemplate}(id = "SIMPLE_FIRST_TEST", name = "Поиск в гугле тортика", description = "Описание1")
 * public TestTemplate<GoogleTestContext> simpleFirstTest() {
 *     return TestTemplate.executeFor(GoogleTestContext.class)
 *         .then(googleTestSteps::openGoogle)
 *         .then(WriteToSearchGoogleStep::searchCake)
 *         .then(googleTestSteps::search);
 * }
 * </pre>
 *
 * @see dev.muskrat.aquatic.lib.common.declaration.TestTemplate
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AquaticTestTemplate {

    String id();

    String name();

    String description();
}

