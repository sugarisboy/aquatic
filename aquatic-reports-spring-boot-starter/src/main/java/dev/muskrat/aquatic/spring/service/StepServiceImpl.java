package dev.muskrat.aquatic.spring.service;

import dev.muskrat.aquatic.lib.common.dto.StepDeclarationDto;
import dev.muskrat.aquatic.lib.common.dto.StepInstanceDto;
import dev.muskrat.aquatic.lib.common.dto.TestInstanceDto;
import dev.muskrat.aquatic.spring.model.Step;
import dev.muskrat.aquatic.spring.model.StepResult;
import dev.muskrat.aquatic.spring.repository.StepRepository;
import dev.muskrat.aquatic.spring.repository.StepResultRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;
    private final StepResultRepository stepResultRepository;
    private final CheckSumService checkSumService;

    @Override
    public Step getOrCreate(StepDeclarationDto stepDeclaration) {
        Step actualStep = getActualStep(stepDeclaration);


        return actualStep;
    }

    @Override
    public void start(StepInstanceDto step, TestInstanceDto test) {
        String stepCode = step.getDeclaration().getId();
        UUID executionId = test.getExecutionId();

        StepResult stepResult = stepResultRepository.findByCodeAndTestExecutionId(stepCode, executionId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Не найден StepResult для [%s, %s]", step, executionId)));

        stepResult.setStatus(step.getStatus());
        stepResultRepository.save(stepResult);
    }

    @Override
    public void finish(StepInstanceDto step, TestInstanceDto test) {
        String stepCode = step.getDeclaration().getId();
        UUID executionId = test.getExecutionId();

        StepResult stepResult = stepResultRepository.findByCodeAndTestExecutionId(stepCode, executionId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Не найден StepResult для [%s, %s]", step, executionId)));

        stepResult.setStatus(step.getStatus());
        stepResultRepository.save(stepResult);
    }



    private Step getActualStep(StepDeclarationDto declaration) {
        String code = declaration.getId();
        Step createdStepInstance = stepRepository.findFirstByCodeOrderByVersionDesc(code).orElse(null);

        int actualCheckSum = checkSumService.calculateCheckSum(declaration);

        boolean isExistActualVersion = createdStepInstance != null
                && createdStepInstance.getHash() == actualCheckSum;

        if (isExistActualVersion) {
            return createdStepInstance;
        }

        int newVersion = createdStepInstance == null ? 1 : createdStepInstance.getVersion() + 1;

        Step step = new Step(
                null,
                declaration.getId(),
                declaration.hashCode(),
                newVersion,
                declaration.getName(),
                declaration.getPreCondition(),
                declaration.getPostCondition(),
                null
        );

        log.info("Инициализирован новый шаг: {}", step);

        return step;
    }
}
