package dev.muskrat.aquatic.lib.common.declaration.logic;

import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import java.util.List;
import java.util.Optional;

/**
 * Является хранилищем всех проинициализированных деклараций шагов и тестов.
 * Позволяет добавлять свои декларации шагов и тестов.
 * <p/>
 * Гарантирует, чтл в рамках хранилища все ID деклараций будут уникальны.
 */
public interface DeclarationStorage {


    /**
     * Добавляет декларацию в хранилище
     * @param declaration декларация теста
     */
    void addTestDeclaration(TestDeclaration declaration);

    /**
     * Добавляет декларацию в хранилище
     * @param declaration декларация шага
     */
    void addStepDeclaration(StepDeclaration declaration);

    /**
     * Возвращает все известные на данный момент декларации
     * @return Список деклараций шагов
     */
    List<StepDeclaration> getSteps();

    /**
     * Возвращает все известные на данный момент декларации
     * @return Список деклараций тестов
     */
    List<TestDeclaration> getTests();

    /**
     * Поиск декларации теста по ID
     * @param id ID декларации теста
     * @return возможная найденная декларация
     */
    Optional<TestDeclaration> findTestDeclarationById(String id);
}
