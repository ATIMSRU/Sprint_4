package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class OrderPageConfirmInfo {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(OrderPageConfirmInfo.class);

    // Локаторы
    private static final By MODAL_HEADER_LOCATOR = By.xpath("//div[contains(@class, 'Order_ModalHeader__3FDaJ') and text()='Заказ оформлен']");
    private static final By VIEW_STATUS_BUTTON_LOCATOR = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Посмотреть статус']");

    // Конструктор
    public OrderPageConfirmInfo(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Получить текст заголовка модального окна
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

    // Нажать кнопку "Посмотреть статус"
    public void clickViewStatusButton() {
        try {
            WebElement viewStatusButton = wait.until(ExpectedConditions.elementToBeClickable(VIEW_STATUS_BUTTON_LOCATOR));
            logger.info("Нажимаем кнопку 'Посмотреть статус'.");
            viewStatusButton.click();
        } catch (Exception e) {
            logger.error("Ошибка при нажатии кнопки 'Посмотреть статус'.", e);
            throw e;
        }
    }
}
