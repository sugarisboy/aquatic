package dev.muskrat.aquatic;

import static org.junit.Assert.assertNotNull;

import dev.muskrat.aquatic.common.AbstractIT;
import dev.muskrat.aquatic.lib.AquaticApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AquaticApplicationTests extends AbstractIT {

	@Autowired
	private AquaticApi aquaticApi;

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

}
