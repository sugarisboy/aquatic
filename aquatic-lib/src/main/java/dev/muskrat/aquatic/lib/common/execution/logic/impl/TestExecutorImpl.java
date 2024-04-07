package dev.muskrat.aquatic.lib.common.execution.logic.impl;

import com.codeborne.selenide.Selenide;
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
    private final TestInstanceFactory testInstanceFactory;
    private final EventProducer eventProducer;

    @Override
    public void execute(TestDeclaration testDeclaration) {
        TestInstanceImpl<?> instance = (TestInstanceImpl<?>) testInstanceFactory.createInstance(testDeclaration);

        log.info("[TEST::{}::{}] Запуск теста", instance.getId(), testDeclaration.getName());
        ChromeDriver chromeDriver = null;

        try {
            ChromeDriver driver = driverFactory.newWindow();
            if (driver != null) {
                WebDriverRunner.setWebDriver(driver);
            }
            chromeDriver = (ChromeDriver) WebDriverRunner.getAndCheckWebDriver();

            instance.inProgress();

            eventProducer.startTest(instance);

            boolean isSuccess = true;
            for (int i = 1; i <= instance.getSteps().size(); i++) {
                StepInstance step = instance.getSteps().get(i - 1);
                StepStatus stepStatus = executeStep(i, instance, step);
                if (stepStatus != StepStatus.SUCCESS) {
                    log.warn("[TEST::{}::{}] Тест провален на шаге {}. {}", instance.getId(), testDeclaration.getName(),
                            i, step.getDeclaration().getName());

                    instance.failure();
                    for (int q = i + 1; q <= instance.getSteps().size(); q++) {
                        log.info("[TEST::{}::{}] Шаг помечен как пропущенный {}. {}", instance.getId(), testDeclaration.getName(),
                                i, step.getDeclaration().getName());
                        instance.getSteps().get(i - 1).skipped();
                    }

                    isSuccess = false;
                    break;
                }
            }

            if (isSuccess) {
                instance.success();
                log.info("[TEST::{}::{}] Тест успешно завершен!", instance.getId(), testDeclaration.getName());
            }

            eventProducer.finishTest(instance);

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
