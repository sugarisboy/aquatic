package dev.muskrat.aquatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.muskrat.aquatic.common.AbstractIT;
import dev.muskrat.aquatic.lib.AquaticApi;
import dev.muskrat.aquatic.lib.common.dto.StepStatus;
import dev.muskrat.aquatic.lib.common.events.FinishedTestEvent;
import dev.muskrat.aquatic.spring.dto.StepResultDto;
import dev.muskrat.aquatic.spring.dto.TestResultDto;
import dev.muskrat.aquatic.spring.model.StepResult;
import dev.muskrat.aquatic.spring.model.TestResult;
import dev.muskrat.aquatic.spring.repository.StepResultRepository;
import dev.muskrat.aquatic.spring.repository.TestResultRepository;
import dev.muskrat.aquatic.spring.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

class AquaticApplicationTests extends AbstractIT {

	@Autowired
	AquaticApi aquaticApi;

/*	@MockBean
	SomeService someService;*/

	@Autowired
	TestService testService;

	@Autowired
	StepResultRepository stepResultRepository;

	@Autowired
	TestResultRepository testResultRepository;

	@Test
	void simpleTest() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		aquaticApi.registerEventHandler(FinishedTestEvent.class, event -> {
			countDownLatch.countDown();
		});

		System.setProperty("webdriver.chrome.driver", "../chromedriver");
		assertNotNull(aquaticApi.getAllTestDeclarationIds());

		String testId = aquaticApi.getAllTestDeclarationIds().get(0);

		UUID executionId = aquaticApi.addToQueueTestByDeclarationId(testId);

		boolean await = countDownLatch.await(400, TimeUnit.SECONDS);
		assertTrue(await);

		TestResultDto testResult = testService.getResultByExecutionId(executionId);
		assertNotNull(testResult);

		List<StepResultDto> stepResults = testResult.getStepResults();

		List<StepResultDto> successStepResults = stepResults.stream()
				.filter(result -> result.getStatus() == StepStatus.SUCCESS)
				.collect(Collectors.toList());

		assertEquals(3, successStepResults.size());
	}

	@Test
	void shouldRunTestExecutionAndFailureOn2Step() {
		System.setProperty("webdriver.chrome.driver", "../chromedriver.exe");


/*		doThrow(new RuntimeException("Mock exception"))
				.when(someService).call();*/

		assertNotNull(aquaticApi.getAllTestDeclarationIds());

		String testId = aquaticApi.getAllTestDeclarationIds().get(0);

		aquaticApi.addToQueueTestByDeclarationId(testId);

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}


}
