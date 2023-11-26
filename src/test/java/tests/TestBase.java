package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import config.ProjectConfiguration;
import config.web.LaunchConfig;
import config.web.LaunchConfigReader;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

import java.util.Objects;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TestBase {

    private static final LaunchConfig CONFIG = LaunchConfigReader.Instance.read();

    @BeforeAll
    @Tag("ui")
    static void setUpUI() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        ProjectConfiguration projectConfiguration = new ProjectConfiguration(CONFIG);
        projectConfiguration.webConfig();
    }

    @AfterEach
    @Tag("ui")
    void tearDownUI() {
        Attach.screenshotAs("Screenshot");
        Attach.pageSource();
        if (Objects.equals(System.getProperty("browserName"), "chrome")) {
            Attach.browserConsoleLogs();
        }
        Attach.addVideo();

        getWebDriver().manage().deleteAllCookies();
    }

    @BeforeAll
    @Tag("api")
    static void setUpAPI() {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration(CONFIG);
        projectConfiguration.apiConfig();
    }
}
