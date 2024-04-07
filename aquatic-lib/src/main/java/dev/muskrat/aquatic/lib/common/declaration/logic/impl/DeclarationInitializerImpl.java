package dev.muskrat.aquatic.lib.common.declaration.logic.impl;

import dev.muskrat.aquatic.lib.common.declaration.logic.DeclarationInitializer;
import dev.muskrat.aquatic.lib.common.declaration.logic.DeclarationStorage;
import dev.muskrat.aquatic.lib.common.declaration.logic.StepDeclarationReader;
import dev.muskrat.aquatic.lib.common.declaration.logic.TestDeclarationReader;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class DeclarationInitializerImpl implements DeclarationInitializer {

    private final StepDeclarationReader stepDeclarationReader;
    private final TestDeclarationReader testDeclarationReader;
    private final DeclarationStorage declarationStorage;

    @Override
    public void initStep(Object... instanceOfClasses) {
        Arrays.stream(instanceOfClasses)
                .map(stepDeclarationReader::loadDeclaration)
                .flatMap(Collection::stream)
                .forEach(declarationStorage::addStepDeclaration);
    }

    @Override
    public void initTest(Object... instanceOfClasses) {
        Arrays.stream(instanceOfClasses)
                .map(i -> testDeclarationReader.loadDeclaration(i, declarationStorage.getSteps()))
                .flatMap(Collection::stream)
                .forEach(declarationStorage::addTestDeclaration);
    }
}
