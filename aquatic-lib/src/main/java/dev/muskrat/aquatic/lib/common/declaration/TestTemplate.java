package dev.muskrat.aquatic.lib.common.declaration;


import dev.muskrat.aquatic.lib.common.AquaticStepHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Шаблон теста из цепочек вызовов шагов {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}.
 * Имеет строгую типизацию контекста теста. Может быть применим данный шаблон только для него.
 * <p/>
 *
 * @param <T> Тип контекста, для которого будет применим данный тест
 *            <p/>
 * @see dev.muskrat.aquatic.lib.common.annotations.AquaticStep
 * @see dev.muskrat.aquatic.lib.common.annotations.AquaticTestTemplate
 */
@Getter
@RequiredArgsConstructor
public class TestTemplate<T> {

    private final Class<T> type;
    public List<AquaticStepHandler<T>> stepRunnables = new ArrayList<>();

    /**
     * Добавляет к шаблону в цепочку вызов один из шагов.
     * В качестве аргумента может быть использован только method reference
     * <p/>
     * Ссылка на метод может быть статической или нестатической.
     * <li>
     * {@code SomeStepClass::execution}
     * <p/>
     * В случае статической ссылки на метод класса:
     * Класс должен быть аннотирован аннотацией {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}
     * согласно всем необходимым правилам данной аннотации
     * </li>
     * <p/>
     * <li>
     * {@code ClassWithSteps::someStep}
     * <p/>
     * В случае нестатической ссылки на метод объекта:
     * Метод, который был передан в качестве аргумента, должен быть аннотирован {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}
     * согласно всем необходимым правилам данной аннотации
     * </li>
     * <p/>
     * @param runnable ссылка на метод для шага.
     * @return шаблон теста, ответ использовать никак не требуется
     * @see dev.muskrat.aquatic.lib.common.annotations.AquaticStep
     * @see <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html">method referance</a>
     */
    public TestTemplate<T> then(AquaticStepHandler<T> runnable) {
        this.stepRunnables.add(runnable);
        return this;
    }

    /**
     * Указывает для какого типа контекста будет использоваться в рамках данного теста
     * @param tClass ссылка на класс контекста
     * @return шаблон теста, ответ использовать никак не требуется
     * @param <T> тип контекста
     */
    public static <T> TestTemplate<T> executeFor(Class<T> tClass) {
        return new TestTemplate<>(tClass);
    }
}
