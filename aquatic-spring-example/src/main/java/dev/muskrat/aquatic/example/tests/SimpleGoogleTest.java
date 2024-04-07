package dev.muskrat.aquatic.example.tests;

import dev.muskrat.aquatic.example.cases.GoogleTestSteps;
import dev.muskrat.aquatic.example.contexts.GoogleTestContext;
import dev.muskrat.aquatic.example.steps.WriteToSearchGoogleStep;
import dev.muskrat.aquatic.lib.common.annotations.AquaticTestTemplate;
import dev.muskrat.aquatic.lib.common.declaration.TestTemplate;
import dev.muskrat.aquatic.spring.Aquatic;
import lombok.RequiredArgsConstructor;

@Aquatic
@RequiredArgsConstructor
public class SimpleGoogleTest {

    private final GoogleTestSteps googleTestSteps;

    @AquaticTestTemplate(id = "SIMPLE_FIRST_TEST", name = "Поиск в гугле тортика", description = "Описание1")
    public TestTemplate<GoogleTestContext> simpleFirstTest() {
        return TestTemplate.executeFor(GoogleTestContext.class)
                .then(googleTestSteps::openGoogle)
                .then(WriteToSearchGoogleStep::searchCake)
                .then(googleTestSteps::search);
    }


    @AquaticTestTemplate(
            id = "SIMPLE_SECOND_TEST",
            name = "Поиск в гугле пончика",
            description = "Описание2"
    )
    public TestTemplate<GoogleTestContext> simpleSecondTest() {
        return TestTemplate.executeFor(GoogleTestContext.class)
                .then(googleTestSteps::openGoogle)
                .then(WriteToSearchGoogleStep::searchDonat)
                .then(googleTestSteps::search);
    }
}
