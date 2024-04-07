package dev.muskrat.aquatic.lib.common.declaration.logic.impl;

import dev.muskrat.aquatic.lib.common.declaration.logic.DeclarationStorage;
import dev.muskrat.aquatic.lib.common.exception.AquaticDefinitionException;
import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DeclarationStorageImpl implements DeclarationStorage {

    private final Map<String, StepDeclaration> stepDeclarations = new ConcurrentHashMap<>();
    private final Map<String, TestDeclaration> testDeclarations = new ConcurrentHashMap<>();

    @Override
    public void addStepDeclaration(StepDeclaration declaration) {
        log.info("Добавление декларации шага {}", declaration.getId());

        if (stepDeclarations.containsKey(declaration.getId())) {
            throw new AquaticDefinitionException("Duplicate ID step: " + declaration.getId());
        }

        stepDeclarations.put(declaration.getId(), declaration);
    }

    @Override
    public void addTestDeclaration(TestDeclaration declaration) {
        log.info("Добавление декларации теста {}", declaration.getId());

        if (testDeclarations.containsKey(declaration.getId())) {
            throw new AquaticDefinitionException("Duplicate ID test: " + declaration.getId());
        }

        testDeclarations.put(declaration.getId(), declaration);
    }

    @Override
    public List<StepDeclaration> getSteps() {
        return new ArrayList<>(stepDeclarations.values());
    }

    @Override
    public List<TestDeclaration> getTests() {
        return new ArrayList<>(testDeclarations.values());
    }

    @Override
    public Optional<TestDeclaration> findTestDeclarationById(String id) {
        return Optional.ofNullable(testDeclarations.get(id));
    }
}
