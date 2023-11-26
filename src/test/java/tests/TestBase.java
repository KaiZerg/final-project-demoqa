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
import java.util.Arrays;
import java.util.Objects;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
@Tag("test")
@ExtendWith(TestBase.TestBaseExtension.class)

public class TestBase {

    private static final LaunchConfig CONFIG = LaunchConfigReader.Instance.read();

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        ProjectConfiguration projectConfiguration = new ProjectConfiguration(CONFIG);
        if (isUiTest()) {
            projectConfiguration.webConfig();
        } else if (isApiTest()) {
            projectConfiguration.apiConfig();
        }
    }

    @AfterEach
    void tearDown() {
        if (isUiTest()) {
            Attach.screenshotAs("Screenshot");
            Attach.pageSource();
            if (Objects.equals(System.getProperty("browserName"), "chrome")) {
                Attach.browserConsoleLogs();
            }
            Attach.addVideo();

            getWebDriver().manage().deleteAllCookies();
        }
    }

    private static boolean isUiTest() {
        return Arrays.stream(TestBase.class.getAnnotationsByType(Tag.class))
                .anyMatch(tag -> tag.value().equals("ui"));
    }

    private static boolean isApiTest() {
        return Arrays.stream(TestBase.class.getAnnotationsByType(Tag.class))
                .anyMatch(tag -> tag.value().equals("api"));
    }

    public static class TestBaseExtension implements BeforeAllCallback, AfterEachCallback {
        @Override
        public void beforeAll(ExtensionContext extensionContext) throws Exception {
            // Implement the setup logic
        }

        @Override
        public void afterEach(ExtensionContext extensionContext) throws Exception {
            // Implement the teardown logic
        }
    }
}
