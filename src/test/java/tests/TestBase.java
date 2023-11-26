package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import config.ProjectConfiguration;
import config.web.BrowserName;
import config.web.LaunchConfig;
import config.web.LaunchConfigReader;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Objects;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Tag("test")
public class TestBase implements BeforeAllCallback, AfterEachCallback {

    private static final LaunchConfig CONFIG = LaunchConfigReader.Instance.read();

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        ProjectConfiguration projectConfiguration = new ProjectConfiguration(CONFIG);
        projectConfiguration.apiConfig();
        projectConfiguration.webConfig();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        Attach.screenshotAs("Screenshot");
        Attach.pageSource();
        if (Objects.equals(System.getProperty("browserName"), BrowserName.CHROME.toString())) {
            Attach.browserConsoleLogs();
        }
        Attach.addVideo();

        getWebDriver().manage().deleteAllCookies();
    }
}
