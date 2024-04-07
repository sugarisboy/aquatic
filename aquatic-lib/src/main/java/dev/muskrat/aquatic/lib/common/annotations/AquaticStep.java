package dev.muskrat.aquatic.lib.common.annotations;

import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Используется для аннотирования объекта как автоматизированный шаг какого-либо теста.
 * Необходим для формирования полезной информации о шаге и подробном логирования и дружелюбного использования фреймворка.
 * <p/>
 * Объект помеченный данной аннотацией должен будет быть исполняемым.
 * В случае запуска инстанса теста шаг получит текущий контекст теста {@link TestInstance#getContext() }
 * и должен будет выполнить соответствующий действия для данного шага.
 * <p/>
 * Может быть использован для аннотирования метода или класса.
 * <p/>
 * В случае аннотирования шага через класс необходимо не реализовать в нем метод,
 * который будет помечен аннотацией {@link AquaticStepExecution} для понимания какой метод в классе вызвать в случае
 * передачи управления от теста к шагу.
 * Пример такого класса приведен ниже:
 * <pre>
 * @ {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}(
 * id = "OPEN_GOOGLE",
 * name = "Открытие гугла",
 * preCondition = "Opened browser",
 * postCondition = "Opened google"
 * )
 * public void openGoogle(GoogleTestContext context) {
 * Selenide.open("https://www.google.ru/");
 * }
 * </pre>
 * <p>
 * Пример аннотированного класса:
 * <pre>
 * @ {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}(
 * id = "WRITE_TO_SEARCH_GOOGLE_STEP",
 * name = "Набор текста в поисковое окно гугла",
 * preCondition = "Opened google",
 * postCondition = "Open google and in search line printed 'learn java 1 hours video'"
 * )
 * public class WriteToSearchGoogleStep {
 * @ {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStepExecution}
 * public void execution(GoogleTestContext context) {
 * WebElement element = $x("//textarea");
 * element.sendKeys("learn java 1 hours video");
 * context.setSearch("learn java 1 hours video");
 * }
 * }
 * </pre>
 *
 * @see AquaticStepExecution
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface AquaticStep {

    String id();

    String name();

    String preCondition();

    String postCondition();
}
