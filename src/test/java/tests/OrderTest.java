package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobjects.*;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private HomePageScooter homePage;
    private OrderPageForWhom orderPageForWhom;
    private OrderPageRent orderPageRent;
    private OrderConfirmationStep orderConfirmationStep;
    private OrderPageConfirmInfo orderPageConfirmInfo;

    @Parameterized.Parameter(0)
    public String browserType;

    @Parameterized.Parameter(1)
    public String name;

    @Parameterized.Parameter(2)
    public String surname;

    @Parameterized.Parameter(3)
    public String address;

    @Parameterized.Parameter(4)
    public String metroStation;

    @Parameterized.Parameter(5)
    public String phoneNumber;

    @Parameterized.Parameter(6)
    public String deliveryDate;

    @Parameterized.Parameter(7)
    public String rentalTerm;

    @Parameterized.Parameter(8)
    public String color;

    @Parameterized.Parameter(9)
    public String comment;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"chrome", "Иван", "Иванов", "Ленина 10", "Маяковская", "+79161234567", "10.12.2024", "сутки", "чёрный жемчуг", "Нет комментариев"},
                {"firefox", "Мария", "Петрова", "Тверская 15", "Киевская", "+79169876543", "11.12.2024", "трое суток", "серая безысходность", "Пожалуйста, будьте осторожны!"}
        });
    }

    @Override
    protected String getBrowserType() {
        return browserType;
    }

    @Override
    protected void setUpTestSpecific() {
        homePage = new HomePageScooter(driver);
        orderPageForWhom = new OrderPageForWhom(driver);
        orderPageRent = new OrderPageRent(driver);
        orderConfirmationStep = new OrderConfirmationStep(driver);
        orderPageConfirmInfo = new OrderPageConfirmInfo(driver);
        homePage.closeCookieBanner();
        logger.info("Баннер с cookies закрыт.");
    }

    @Test
    public void testOrderProcess() {
        logger.info("Начало теста процесса оформления заказа.");
        homePage.clickOrderButtonTop();
        orderPageForWhom.enterUserData(name, surname, address, metroStation, phoneNumber);
        orderPageForWhom.clickNextButton();
        orderPageRent.enterDeliveryDate(deliveryDate);
        orderPageRent.selectRentalTerm(rentalTerm);
        orderPageRent.selectColor(color);
        orderPageRent.enterCourierComment(comment);
        orderPageRent.clickOrderButton();
        orderConfirmationStep.waitForModalToBeVisible();
        orderConfirmationStep.clickButton("Да");
        assertTrue(orderPageConfirmInfo.getModalHeaderText().contains("Заказ оформлен"));
        logger.info("Заказ успешно оформлен.");
    }
}
