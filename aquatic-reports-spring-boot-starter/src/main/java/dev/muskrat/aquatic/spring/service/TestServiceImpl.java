package dev.muskrat.aquatic.spring.service;

import dev.muskrat.aquatic.lib.common.dto.StepStatus;
import dev.muskrat.aquatic.lib.common.dto.TestDeclarationDto;
import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import dev.muskrat.aquatic.lib.common.dto.TestStatus;
import dev.muskrat.aquatic.spring.model.Step;
import dev.muskrat.aquatic.spring.model.StepResult;
import dev.muskrat.aquatic.spring.model.Test;
import dev.muskrat.aquatic.spring.model.TestResult;
import dev.muskrat.aquatic.spring.repository.TestRepository;
import dev.muskrat.aquatic.spring.repository.TestResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final StepService stepService;

    private final TestRepository testRepository;
    private final TestResultRepository testResultRepository;

    @Override
    public void createInstance(TestInstanceDto instance) {
        Test test = getActualTest(instance);

        test.getSteps().forEach(step -> step.setTest(test));

        List<StepResult> stepResults = test.getSteps().stream().map(step -> new StepResult(
                null,
                StepStatus.IN_QUEUE,
                null,
                step,
                null
        )).collect(Collectors.toList());

        TestResult result = new TestResult(
                instance.getExecutionId(),
                TestStatus.IN_QUEUE,
                null,
                test,
                stepResults
        );

        stepResults.forEach(stepResult -> stepResult.setTestResult(result));

        TestResult save = testResultRepository.save(result);
    }

    private Test getActualTest(TestInstanceDto instance) {
        TestDeclarationDto declaration = instance.getDeclaration();

        String code = declaration.getId();
        Test createdTestInstance = testRepository.findFirstByCodeOrderByVersionDesc(code).orElse(null);

        boolean isExistActualVersion = createdTestInstance != null
                && createdTestInstance.getHash() == declaration.hashCode();

        if (isExistActualVersion) {
            return createdTestInstance;
        }

        int newVersion = createdTestInstance == null ? 1 : createdTestInstance.getVersion() + 1;

        List<Step> steps = declaration.getSteps()
                .stream()
                .map(stepService::getOrCreate)
                .collect(Collectors.toList());

        Test test = new Test(
                null,
                declaration.getId(),
                declaration.hashCode(),
                newVersion,
                declaration.getName(),
                declaration.getDescription(),
                steps
        );

        steps.forEach(step -> step.setTest(test));
        return test;
    }
}
