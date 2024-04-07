package dev.muskrat.aquatic.lib.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.Selenide;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

@Slf4j
public class DriverFactory {

    private ChromeOptions options = options();

    public ChromeDriver newWindow() {
        Boolean driver = null;

        if (isWindows()) {
            return new ChromeDriver(options);
        } else {
            for (int i = 0; i < 3; i++) {
                if (driver != null)
                    break;

                log.info("Попытка запустить браузер {}", i + 1);
                try {
                    //driver = new ChromeDriver(options);
                    Selenide.open("https://www.google.com/");
                    driver = true;
                } catch (Exception e) {
                    log.error("Ошибка запуска браузера", e);
                }
            }

            if (driver == null) {
                throw new RuntimeException("Ошибка запуска браузера");
            }
        }
        return null;
    }

    private ChromeOptions options() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--log-level=3");
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--remote-allow-origins=*");

        if (isWindows()) {
            //options.addArguments("--auto-open-devtools-for-tabs");

            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            if (System.getProperty("background") != null) {
                Configuration.headless = true;
            } else {
                Configuration.headless = false;
                Configuration.holdBrowserOpen = true;
            }
            options.addArguments("--disable-gpu"); // applicable to windows os only
        } else {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
            Configuration.headless = true;
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability("ie.ensureCleanSession", true);
        capabilities.setCapability("ie.usePerProcessProxy", true);
        capabilities.setCapability("ie.browserCommandLineSwitches", "-private");
        capabilities.setCapability("requireWindowFocus", false);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setAcceptInsecureCerts(true);
        //capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

        Configuration.browser = "chrome";
        Configuration.browserCapabilities = capabilities;
        Configuration.timeout = 15 * 1000L;
        Configuration.downloadsFolder = "build/reports/tests";
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        Configuration.browserSize = "1920x1080";

        return options;
    }

    private boolean isWindows() {
        return System.getProperty("windows") != null || System.getProperty("os.name").toLowerCase().contains("windows");
    }
}
