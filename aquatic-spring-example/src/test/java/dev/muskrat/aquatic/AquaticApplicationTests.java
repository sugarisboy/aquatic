package dev.muskrat.aquatic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;

import dev.muskrat.aquatic.common.AbstractIT;
import dev.muskrat.aquatic.example.service.SomeService;
import dev.muskrat.aquatic.lib.AquaticApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

class AquaticApplicationTests extends AbstractIT {

	@Autowired
	AquaticApi aquaticApi;

/*	@MockBean
	SomeService someService;*/

	@Test
	void simpleTest() {
		System.setProperty("webdriver.chrome.driver", "../chromedriver.exe");
		assertNotNull(aquaticApi.getAllTestDeclarationIds());

		String testId = aquaticApi.getAllTestDeclarationIds().get(0);

		aquaticApi.addToQueueTestByDeclarationId(testId);

		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
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
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}


}
