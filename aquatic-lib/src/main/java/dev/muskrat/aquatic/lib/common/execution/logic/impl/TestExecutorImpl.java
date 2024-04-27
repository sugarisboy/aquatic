package dev.muskrat.aquatic.lib.common.execution.logic.impl;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import dev.muskrat.aquatic.lib.common.dto.ScreenshotDto;
import dev.muskrat.aquatic.lib.common.dto.TestStatus;
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
import org.openqa.selenium.OutputType;
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

            int stepNumber = 1;
            boolean isSkippedNext = false;
            for (StepInstance step : testInstance.getSteps()) {
                if (isSkippedNext) {
                    log.info("[TEST::{}::{}] Шаг помечен как пропущенный {}. {}",
                            testInstance.getId(), testDeclaration.getName(), stepNumber, step.getDeclaration().getName());
                    step.skipped();
                    eventProducer.skipStep(testInstance, step, new ScreenshotDto(Selenide.screenshot(OutputType.BYTES)));
                } else {
                    StepStatus stepStatus = executeStep(stepNumber, testInstance, step);
                    if (stepStatus != StepStatus.SUCCESS) {
                        log.warn("[TEST::{}::{}] Тест провален на шаге {}. {}", testInstance.getId(), testDeclaration.getName(),
                                stepNumber, step.getDeclaration().getName());


                        isSkippedNext = true;
                    }
                }

                stepNumber++;
            }

            if (testInstance.getStatus() != TestStatus.SUCCESS) {
                testInstance.success();
                log.info("[TEST::{}::{}] Тест успешно завершен!", testInstance.getId(), testDeclaration.getName());
            } else {
                testInstance.failure();
            }

            eventProducer.finishTest(testInstance);

        } catch (Exception e) {
            throw new AquaticTestExecutionFailedException("Неизвестная ошибка при исполнении шагов теста", e);
        } finally {
            if (chromeDriver != null) {
                log.info("Закрытие браузера");
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
            eventProducer.startStep(instance, stepInstance, new ScreenshotDto(Selenide.screenshot(OutputType.BYTES)));


            stepInstance.executeStep();
            log.info("[TEST::{}::{}::{}] Шаг успешно выполнен", instance.getId(), stepInstance.getDeclaration().getName(), seqNumber);
            stepInstance.success();
        } catch (Exception e) {
            log.error("[TEST::{}::{}::{}] Шаг провален", instance.getId(), stepInstance.getDeclaration().getName(), seqNumber, e);
            stepInstance.failure();
        } finally {
            eventProducer.finishStep(instance, stepInstance, new ScreenshotDto(Selenide.screenshot(OutputType.BYTES)));
        }

        return stepInstance.getStatus();
    }
}
