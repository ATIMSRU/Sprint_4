package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class OrderConfirmationStep {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(OrderConfirmationStep.class);

    // Локаторы элементов
    private static final By MODAL_HEADER_LOCATOR = By.xpath("//div[contains(@class, 'Order_ModalHeader__3FDaJ')]");
    private static final String BUTTON_LOCATOR_TEMPLATE = "//button[contains(@class, 'Button_Button__ra12g') and text()='%s']";

    public OrderConfirmationStep(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Получение текста заголовка модального окна
    public String getModalHeaderText() {
        try {
            WebElement modalHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_HEADER_LOCATOR));
            String headerText = modalHeader.getText();
            logger.info("Текст заголовка модального окна: {}", headerText);
            return headerText;
        } catch (Exception e) {
            logger.error("Ошибка при получении текста заголовка модального окна.", e);
            throw e;
        }
    }

    // Ожидание видимости модального окна
    public void waitForModalToBeVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_HEADER_LOCATOR));
            logger.info("Модальное окно успешно отображается.");
        } catch (Exception e) {
            logger.error("Ошибка: модальное окно не отображается.", e);
            throw e;
        }
    }

    // Проверка кликабельности кнопки
    public boolean isButtonClickable(String buttonText) {
        By buttonLocator = By.xpath(String.format(BUTTON_LOCATOR_TEMPLATE, buttonText));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
            logger.info("Кнопка '{}' доступна для клика.", buttonText);
            return true;
        } catch (Exception e) {
            logger.error("Кнопка '{}' не доступна для клика.", buttonText, e);
            return false;
        }
    }

    // Клик по кнопке
    public void clickButton(String buttonText) {
        By buttonLocator = By.xpath(String.format(BUTTON_LOCATOR_TEMPLATE, buttonText));
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
            logger.info("Нажимаем кнопку '{}'.", buttonText);
            button.click();
        } catch (Exception e) {
            logger.error("Ошибка при нажатии кнопки '{}'.", buttonText, e);
            throw e;
        }
    }
}
