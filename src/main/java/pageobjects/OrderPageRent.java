package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class OrderPageRent {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(OrderPageRent.class);

    // Локаторы для элементов страницы
    private static final By DATE_PICKER_INPUT = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private static final By RENTAL_TERM_DROPDOWN = By.xpath("//div[@class='Dropdown-control']//div[@class='Dropdown-placeholder' and text()='* Срок аренды']");
    private static final By COMMENT_INPUT = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private static final By ORDER_BUTTON = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']");
    private static final By BACK_BUTTON = By.xpath("//button[text()='Назад']");
    private static final String COLOR_CHECKBOX_TEMPLATE = "//label[contains(text(),'%s')]";

    public OrderPageRent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Метод для ввода даты доставки самоката
    public void enterDeliveryDate(String date) {
        try {
            WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(DATE_PICKER_INPUT));
            dateInput.clear();
            dateInput.sendKeys(date);
            dateInput.sendKeys(Keys.ENTER);
            logger.info("Дата доставки '{}' была успешно введена.", date);
        } catch (Exception e) {
            logger.error("Ошибка при вводе даты доставки '{}'.", date, e);
            throw e;
        }
    }

    // Метод для выбора срока аренды
    public void selectRentalTerm(String rentalTerm) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(RENTAL_TERM_DROPDOWN));
            dropdown.click();
            By optionLocator = By.xpath("//div[contains(@class, 'Dropdown-option') and text()='" + rentalTerm + "']");
            WebElement dropdownOption = wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));
            dropdownOption.click();
            logger.info("Срок аренды '{}' был успешно выбран.", rentalTerm);
        } catch (Exception e) {
            logger.error("Ошибка при выборе срока аренды '{}'.", rentalTerm, e);
            throw e;
        }
    }

    // Метод для выбора цвета
    public void selectColor(String color) {
        By colorCheckboxLocator = By.xpath(String.format(COLOR_CHECKBOX_TEMPLATE, color));
        try {
            WebElement colorCheckbox = wait.until(ExpectedConditions.elementToBeClickable(colorCheckboxLocator));
            colorCheckbox.click();
            logger.info("Цвет '{}' был успешно выбран.", color);
        } catch (Exception e) {
            logger.error("Ошибка при выборе цвета '{}'.", color, e);
            throw e;
        }
    }

    // Метод для ввода комментария для курьера
    public void enterCourierComment(String comment) {
        try {
            WebElement commentField = wait.until(ExpectedConditions.elementToBeClickable(COMMENT_INPUT));
            commentField.clear();
            commentField.sendKeys(comment);
            logger.info("Комментарий для курьера '{}' был успешно введён.", comment);
        } catch (Exception e) {
            logger.error("Ошибка при вводе комментария для курьера '{}'.", comment, e);
            throw e;
        }
    }

    // Метод для нажатия на кнопку "Заказать"
    public void clickOrderButton() {
        try {
            WebElement orderBtn = wait.until(ExpectedConditions.elementToBeClickable(ORDER_BUTTON));
            orderBtn.click();
            logger.info("Кнопка 'Заказать' была успешно нажата.");
        } catch (Exception e) {
            logger.error("Ошибка при нажатии на кнопку 'Заказать'.", e);
            throw e;
        }
    }
    public boolean isOrderButtonClickable() {
        try {
            WebElement orderBtn = wait.until(ExpectedConditions.elementToBeClickable(ORDER_BUTTON));
            return orderBtn.isEnabled();
        } catch (TimeoutException e) {
            logger.warn("Кнопка 'Заказать' не доступна для клика в течение времени ожидания.");
            return false;
        } catch (Exception e) {
            logger.error("Ошибка при проверке доступности кнопки 'Заказать'.", e);
            throw e;
        }
    }

}
