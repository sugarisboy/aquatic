package dev.muskrat.aquatic.lib.common.execution.logic.impl;

import com.codeborne.selenide.WebDriverRunner;
import dev.muskrat.aquatic.lib.common.execution.StepInstance;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import dev.muskrat.aquatic.lib.common.execution.TestInstanceImpl;
import dev.muskrat.aquatic.lib.common.execution.logic.TestExecutor;
import dev.muskrat.aquatic.lib.common.execution.logic.TestInstanceFactory;
import dev.muskrat.aquatic.lib.common.exception.AquaticTestExecutionFailedException;
import dev.muskrat.aquatic.lib.common.dto.StepStatus;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.events.logic.EventProducer;
import dev.muskrat.aquatic.lib.selenide.DriverFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;

@Slf4j
@RequiredArgsConstructor
public class TestExecutorImpl implements TestExecutor {

    private final DriverFactory driverFactory;
    private final EventProducer eventProducer;

    @Override
    public void execute(TestInstance<?> testInstance) {
        TestDeclaration testDeclaration = testInstance.getDeclaration();

        log.info("[TEST::{}::{}] Запуск теста", testInstance.getId(), testDeclaration.getName());
        ChromeDriver chromeDriver = null;

        try {
            ChromeDriver driver = driverFactory.newWindow();
            if (driver != null) {
                WebDriverRunner.setWebDriver(driver);
            }
            chromeDriver = (ChromeDriver) WebDriverRunner.getAndCheckWebDriver();

            testInstance.inProgress();

            eventProducer.startTest(testInstance);

            boolean isSuccess = true;

            for (int index = 0, stepIndex = 1; index < testInstance.getSteps().size(); index++, stepIndex++) {
                StepInstance step = testInstance.getSteps().get(index);
                StepStatus stepStatus = executeStep(stepIndex, testInstance, step);
                if (stepStatus != StepStatus.SUCCESS) {
                    log.warn("[TEST::{}::{}] Тест провален на шаге {}. {}", testInstance.getId(), testDeclaration.getName(),
                            stepIndex, step.getDeclaration().getName());

                    testInstance.failure();
                    isSuccess = false;
                    break;
                }
            }

            for (int index = 0, stepIndex = 1; index < testInstance.getSteps().size(); index++, stepIndex++) {
                StepInstance step = testInstance.getSteps().get(index);

                if (!step.isFinished()) {
                    log.info("[TEST::{}::{}] Шаг помечен как пропущенный {}. {}",
                            testInstance.getId(),
                            testDeclaration.getName(),
                            stepIndex,
                            step.getDeclaration().getName()
                    );
                    step.skipped();
                }
            }

            if (isSuccess) {
                testInstance.success();
                log.info("[TEST::{}::{}] Тест успешно завершен!", testInstance.getId(), testDeclaration.getName());
            }

            eventProducer.finishTest(testInstance);

        } catch (Exception e) {
            throw new AquaticTestExecutionFailedException("Неизвестная ошибка при исполнении шагов теста", e);
        } finally {
            if (chromeDriver != null) {
                chromeDriver.quit();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private StepStatus executeStep(int seqNumber, TestInstance<?> instance, StepInstance stepInstance) {
        try {
            log.info("[TEST::{}::{}::{}] Запуск шага", instance.getId(), stepInstance.getDeclaration().getName(), seqNumber);
            stepInstance.inProgress();
            stepInstance.executeStep();
            log.info("[TEST::{}::{}::{}] Шаг успешно выполнен", instance.getId(), stepInstance.getDeclaration().getName(), seqNumber);
            stepInstance.success();
        } catch (Exception e) {
            log.error("[TEST::{}::{}::{}] Шаг провален", instance.getId(), stepInstance.getDeclaration().getName(), seqNumber, e);
            stepInstance.failure();
        }

        return stepInstance.getStatus();
    }
}
