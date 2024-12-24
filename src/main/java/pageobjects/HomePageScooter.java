package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class HomePageScooter {
    private final WebDriver driver;
    private final WebDriverWait wait; // Явные ожидания

    // Локаторы для элементов страницы
    private final By faqSection = By.className("accordion"); // Секция FAQ
    private final By cookieButton = By.id("rcc-confirm-button"); // Кнопка для закрытия куки

    // Локаторы для кнопок "Заказать"
    private final By orderButtonTop = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and not(contains(@class, 'Button_UltraBig__UU3Lp'))]"); // Верхняя кнопка "Заказать"
    private final By orderButtonBottom = By.xpath("//button[contains(@class, 'Button_Button__ra12g Button_UltraBig__UU3Lp')]"); // Нижняя кнопка "Заказать"

    // Локаторы для вопросов и ответов
    private final String questionButtonTemplate = "accordion__heading-%d"; // Динамический ID для вопроса
    private final String answerPanelTemplate = "accordion__panel-%d"; // Динамический ID для ответа

    // Конструктор
    public HomePageScooter(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Установить время ожидания
    }

    // Закрытие баннера куки, если он существует.
    public void closeCookieBanner() {
        try {
            driver.findElement(cookieButton).click();
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already closed.");
        }
    }

    // Скролл до секции FAQ.
    public void scrollToFAQ() {
        WebElement faqElement = wait.until(ExpectedConditions.visibilityOfElementLocated(faqSection));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", faqElement);
    }

    // Нажатие на вопрос в FAQ.
    public void clickQuestion(int questionIndex) {
        By questionLocator = By.id(String.format(questionButtonTemplate, questionIndex));
        WebElement questionElement = wait.until(ExpectedConditions.elementToBeClickable(questionLocator));
        questionElement.click();
    }

    // Проверка, что текст ответа отображается.
    public boolean isAnswerVisible(int questionIndex) {
        By answerLocator = By.id(String.format(answerPanelTemplate, questionIndex));
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answerElement.isDisplayed();
    }

    // Получение текста ответа.
    public String getAnswerText(int questionIndex) {
        By answerLocator = By.id(String.format(answerPanelTemplate, questionIndex));
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answerElement.getText();
    }

    // Нажатие на верхнюю кнопку "Заказать"
    public void clickOrderButtonTop() {
        WebElement topOrderButton = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        topOrderButton.click();
    }

    // Нажатие на нижнюю кнопку "Заказать"
    public void clickOrderButtonBottom() {
        WebElement bottomOrderButton = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        bottomOrderButton.click();
    }
}
