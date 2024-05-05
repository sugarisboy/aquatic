package dev.muskrat.aquatic.spring.service;

import dev.muskrat.aquatic.lib.common.dto.StepInstanceDto;
import dev.muskrat.aquatic.lib.common.dto.StepStatus;
import dev.muskrat.aquatic.lib.common.dto.TestDeclarationDto;
import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import dev.muskrat.aquatic.lib.common.dto.TestStatus;
import dev.muskrat.aquatic.spring.dto.StepDto;
import dev.muskrat.aquatic.spring.dto.StepResultDto;
import dev.muskrat.aquatic.spring.dto.TestDto;
import dev.muskrat.aquatic.spring.dto.TestResultDto;
import dev.muskrat.aquatic.spring.model.Step;
import dev.muskrat.aquatic.spring.model.StepResult;
import dev.muskrat.aquatic.spring.model.Test;
import dev.muskrat.aquatic.spring.model.TestResult;
import dev.muskrat.aquatic.spring.repository.TestRepository;
import dev.muskrat.aquatic.spring.repository.TestResultRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final StepService stepService;

    private final TestRepository testRepository;
    private final TestResultRepository testResultRepository;
    private final CheckSumService checkSumService;

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

        int actualCheckSum = checkSumService.calculateCheckSum(instance.getDeclaration());

        boolean isExistActualVersion = createdTestInstance != null
                && createdTestInstance.getHash() == actualCheckSum;

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
                actualCheckSum,
                newVersion,
                declaration.getName(),
                declaration.getDescription(),
                steps
        );

        steps.forEach(step -> step.setTest(test));

        log.info("Инициализирован новый тест: {}", test);

        return test;
    }

    @Override
    public TestResultDto getResultByExecutionId(UUID executionId) {
        TestResult testResult = testResultRepository.findById(executionId).orElseThrow(() -> new EntityNotFoundException("Not found test result with id " + executionId));

        return convert(testResult);
    }

    @Override
    public void start(TestInstanceDto test) {
        UUID executionId = test.getExecutionId();

        TestResult testResult = testResultRepository.findById(executionId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Не найден TestResult для [%s]", executionId)));

        testResult.setStatus(test.getStatus());
        testResultRepository.save(testResult);
    }

    @Override
    public void finish(TestInstanceDto test) {
        UUID executionId = test.getExecutionId();

        TestResult testResult = testResultRepository.findById(executionId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Не найден TestResult для [%s]", executionId)));

        testResult.setStatus(test.getStatus());
        testResultRepository.save(testResult);
    }

    // TODO: use mapstruct
    private StepDto convert(Step entity) {
        return new StepDto(
                entity.getId(),
                entity.getCode(),
                entity.getHash(),
                entity.getVersion(),
                entity.getName(),
                entity.getPreCondition(),
                entity.getPostCondition()
        );
    }

    private TestDto convert(Test entity) {
        return new TestDto(
                entity.getId(),
                entity.getCode(),
                entity.getHash(),
                entity.getVersion(),
                entity.getName(),
                entity.getDescription(),
                entity.getSteps().stream().map(this::convert).toList()
        );
    }


    private StepResultDto convert(StepResult entity) {
        return new StepResultDto(
                entity.getId(),
                entity.getStatus(),
                convert(entity.getStep())
        );
    }
    private TestResultDto convert(TestResult entity) {
        return new TestResultDto(
                entity.getId(),
                entity.getStatus(),
                convert(entity.getTest()),
                entity.getStepResults().stream().map(this::convert).toList()
        );
    }
}
