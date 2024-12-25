package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobjects.HomePageScooter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public static List<Object[]> data() throws IOException {
        // Чтение данных из JSON-файла
        ObjectMapper mapper = new ObjectMapper();
        List<TestData> testDataList = List.of(mapper.readValue(new File("src/test/resources/faqTestData.json"), TestData[].class));

        // Генерация параметров
        return testDataList.stream()
                .map(data -> new Object[]{
                        data.browserType, data.questionIndex, data.expectedAnswer
                })
                .collect(Collectors.toList());
    }

    @Override
    protected String getBrowserType() {
        return browserType;
    }

    @Override
    protected void setUpTestSpecific() {
        // Инициализация страницы
        homePage = new HomePageScooter(driver);
        homePage.closeCookieBanner();
        logger.info("Баннер с cookies закрыт.");
    }

    @Test
    public void testFAQSection() {
        logger.info("Начало теста FAQ для браузера: " + browserType);

        // Скролл до секции FAQ
        homePage.scrollToFAQ();

        // Клик по вопросу и проверка ответа
        homePage.clickQuestion(questionIndex);
        assertTrue("Ответ не совпадает с ожидаемым!",
                homePage.isAnswerVisible(questionIndex) &&
                        homePage.getAnswerText(questionIndex).contains(expectedAnswer)
        );

        logger.info("Ответ на вопрос {} успешно проверен в браузере {}.", questionIndex, browserType);
    }

    // Класс для представления данных из JSON
    static class TestData {
        public String browserType;
        public int questionIndex;
        public String expectedAnswer;
    }
}
