package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTest {
    protected WebDriver driver;
    protected static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @Before
    public void setUp() {
        driver = initializeDriver(getBrowserType());
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        logger.info("Открыт браузер {} и загружен URL {}", getBrowserType(), BASE_URL);
        setUpTestSpecific();
    }

    protected abstract String getBrowserType(); // Для получения типа браузера в каждом тесте

    protected abstract void setUpTestSpecific(); // Установка специфичных для теста шагов

    private WebDriver initializeDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                return new FirefoxDriver();
            case "chrome":
            default:
                return new ChromeDriver();
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Браузер закрыт.");
        }
    }
}
