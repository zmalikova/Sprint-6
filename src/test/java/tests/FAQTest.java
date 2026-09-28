package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pageobjects.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FAQTest extends BaseTest {

    private MainPage mainPage;

    @BeforeEach
    public void setUpFaqPage() {
        mainPage = new MainPage(driver);
        mainPage.clickCookieButton();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0|Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "1|Пока что у нас доступен прокат самокатов на 6, 12, 24 часа, а также на сутки, 3 дня или неделю.",
            "2|Самокат приезжает к вам с полной зарядкой аккумулятора. Этого хватает на 30-40 км пробега.",
            "3|Да, каждый час, независимо от того, арендуете вы самокат на сутки или на несколько часов.",
            "4|Да, обязательно. Но если забыли, можно и без неё — просто оплатите тогда, когда будет возможность.",
            "5|Да, но обязательно с взрослым другом.",
            "6|Только начиная с завтрашнего дня. Но если нужно прямо сегодня — обратитесь в поддержку, возможно получится.",
            "7|Да, всё возможно. Напишите в поддержку по контактному телефону, и мы что-нибудь придумаем."
    })
    public void checkFaq(int index, String expectedAnswer) {
        mainPage.clickQuestion(index);
        assertEquals(expectedAnswer, mainPage.getAnswer(index));
    }
}
