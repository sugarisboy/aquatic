package dev.muskrat.aquatic.lib.common.declaration.logic;

/**
 * Инициирует классы на нужные объекты и добавляет инициированные сущности в контекст приложения
 */
public interface DeclarationInitializer {

    /**
     * Инициализирует переданные классы и создает необходимые декларации для шагов
     * <p/>
     * Для поиска по умолчанию использует аннотацию {@link dev.muskrat.aquatic.lib.common.annotations.AquaticStep}
     * @param instanceOfClasses Список классов для инициализации
     */
    void initStep(Object... instanceOfClasses);

    /**
     * Инициализирует переданные классы и создает необходимые декларации для тестов
     * <p/>
     * Для поиска по умолчанию использует аннотацию {@link dev.muskrat.aquatic.lib.common.annotations.AquaticTestTemplate}
     * @param instanceOfClasses Список классов для инициализации
     */
    void initTest(Object... instanceOfClasses);
}
