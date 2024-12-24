package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobjects.HomePageScooter;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FAQTest extends BaseTest {

    private HomePageScooter homePage;

    @Parameterized.Parameter(0)
    public String browserType;

    @Parameterized.Parameter(1)
    public int questionIndex;

    @Parameterized.Parameter(2)
    public String expectedAnswer;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"chrome", 0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"firefox", 1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"chrome", 2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"firefox", 3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"chrome", 4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"firefox", 5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"chrome", 6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"firefox", 7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        });
    }

    @Override
    protected String getBrowserType() {
        return browserType;
    }

    @Override
    protected void setUpTestSpecific() {
        homePage = new HomePageScooter(driver);
        homePage.closeCookieBanner();
        logger.info("Баннер с cookies закрыт.");
    }

    @Test
    public void testFAQSection() {
        homePage.scrollToFAQ();
        homePage.clickQuestion(questionIndex);
        assertTrue(homePage.isAnswerVisible(questionIndex)
                && homePage.getAnswerText(questionIndex).contains(expectedAnswer));
        logger.info("Ответ на вопрос {} успешно проверен.", questionIndex);
    }
}
