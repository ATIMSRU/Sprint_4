package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobjects.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @Parameterized.Parameter(10)
    public String buttonType;

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws IOException {
        // Чтение данных из JSON-файла
        ObjectMapper mapper = new ObjectMapper();
        List<TestData> testDataList = Arrays.asList(mapper.readValue(new File("src/test/resources/orderTestData.json"), TestData[].class));
        // Преобразование данных в список параметров
        return testDataList.stream()
                .map(data -> new Object[]{
                        data.browserType, data.name, data.surname, data.address, data.metroStation, data.phoneNumber,
                        data.deliveryDate, data.rentalTerm, data.color, data.comment, data.buttonType
                })
                .collect(Collectors.toList());
    }

    @Override
    protected String getBrowserType() {
        return browserType;
    }

    @Override
    protected void setUpTestSpecific() {
        // Инициализация объектов страниц
        homePage = new HomePageScooter(driver);
        orderPageForWhom = new OrderPageForWhom(driver);
        orderPageRent = new OrderPageRent(driver);
        orderConfirmationStep = new OrderConfirmationStep(driver);
        orderPageConfirmInfo = new OrderPageConfirmInfo(driver);
        // Закрытие баннера cookies
        homePage.closeCookieBanner();
        logger.info("Баннер с cookies закрыт.");
    }

    @Test
    public void testOrderProcess() {
        logger.info("Начало теста процесса оформления заказа.");

        // Нажатие на указанную кнопку "Заказать"
        homePage.clickOrderButton(buttonType);

        // Заполнение данных на странице "Для кого самокат"
        orderPageForWhom.enterUserData(name, surname, address, metroStation, phoneNumber);
        orderPageForWhom.clickNextButton();

        // Заполнение данных на странице "Параметры аренды"
        orderPageRent.enterDeliveryDate(deliveryDate);
        orderPageRent.selectRentalTerm(rentalTerm);
        orderPageRent.selectColor(color);
        orderPageRent.enterCourierComment(comment);
        orderPageRent.clickOrderButton();

        // Подтверждение заказа
        orderConfirmationStep.waitForModalToBeVisible();
        orderConfirmationStep.clickButton("Да");

        // Проверка успешного оформления заказа
        assertTrue(orderPageConfirmInfo.getModalHeaderText().contains("Заказ оформлен"));
        logger.info("Заказ успешно оформлен.");
        orderPageConfirmInfo.clickViewStatusButton();
    }

    // Класс для представления данных из JSON
    static class TestData {
        public String browserType;
        public String name;
        public String surname;
        public String address;
        public String metroStation;
        public String phoneNumber;
        public String deliveryDate;
        public String rentalTerm;
        public String color;
        public String comment;
        public String buttonType;
    }
}
