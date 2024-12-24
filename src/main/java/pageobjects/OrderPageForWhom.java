package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class OrderPageForWhom {

    private final WebDriver driver;
    private final WebDriverWait wait;
    public static final Logger logger = LoggerFactory.getLogger(OrderPageForWhom.class);

    // Локаторы для элементов формы заказа
    private static final By NAME_FIELD = By.xpath("//input[@placeholder='* Имя']");
    private static final By SURNAME_FIELD = By.xpath("//input[@placeholder='* Фамилия']");
    private static final By ADDRESS_FIELD = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private static final By METRO_STATION_FIELD = By.xpath("//input[@placeholder='* Станция метро']");
    private static final By PHONE_NUMBER_FIELD = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private static final By NEXT_BUTTON = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM')]");

    // Конструктор
    public OrderPageForWhom(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Метод для ввода текста в поле
    private void enterText(By locator, String text) {
        try {
            WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            inputField.clear();
            inputField.sendKeys(text);
            logger.info("Текст '{}' введен в поле с локатором '{}'.", text, locator);
        } catch (Exception e) {
            logger.error("Ошибка при вводе текста '{}' в поле с локатором '{}'.", text, locator, e);
            throw e;
        }
    }

    // Ввод имени
    public void enterName(String name) {
        enterText(NAME_FIELD, name);
    }

    // Ввод фамилии
    public void enterSurname(String surname) {
        enterText(SURNAME_FIELD, surname);
    }

    // Ввод адреса
    public void enterAddress(String address) {
        enterText(ADDRESS_FIELD, address);
    }

    // Ввод станции метро
    public void enterMetroStation(String metroStation) {
        try {
            WebElement metroStationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(METRO_STATION_FIELD));
            metroStationInput.clear();
            metroStationInput.sendKeys(metroStation);
            metroStationInput.sendKeys(Keys.DOWN, Keys.ENTER);
            logger.info("Станция метро '{}' выбрана.", metroStation);
        } catch (Exception e) {
            logger.error("Ошибка при выборе станции метро '{}'.", metroStation, e);
            throw e;
        }
    }

    // Ввод телефона
    public void enterPhoneNumber(String phoneNumber) {
        enterText(PHONE_NUMBER_FIELD, phoneNumber);
    }

    // Нажатие кнопки "Далее"
    public void clickNextButton() {
        try {
            WebElement nextButtonElement = wait.until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON));
            nextButtonElement.click();
            logger.info("Кнопка 'Далее' нажата.");
        } catch (Exception e) {
            logger.error("Ошибка при нажатии кнопки 'Далее'.", e);
            throw e;
        }
    }

    // Метод для ввода всех пользовательских данных
    public void enterUserData(String name, String surname, String address, String metroStation, String phoneNumber) {
        logger.info("Ввод данных пользователя: Имя='{}', Фамилия='{}', Адрес='{}', Станция метро='{}', Телефон='{}'.",
                name, surname, address, metroStation, phoneNumber);
        enterName(name);
        enterSurname(surname);
        enterAddress(address);
        enterMetroStation(metroStation);
        enterPhoneNumber(phoneNumber);
    }

    // Проверка кликабельности кнопки "Далее"
    public boolean isNextButtonClickable() {
        try {
            boolean clickable = wait.until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON)).isDisplayed();
            logger.info("Кнопка 'Далее' кликабельна: {}.", clickable);
            return clickable;
        } catch (Exception e) {
            logger.error("Ошибка при проверке кликабельности кнопки 'Далее'.", e);
            return false;
        }
    }
}
