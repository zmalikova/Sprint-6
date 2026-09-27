package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FAQTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();

        driver.get("https://qa-scooter.praktikum-services.ru/");

        MainPage mainPage = new MainPage(driver);
        mainPage.clickCookieButton();
    }

    @Test
    public void checkFaq() {

        MainPage mainPage = new MainPage(driver);

        mainPage.clickQuestion(0);

        assertEquals(
                "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
                mainPage.getAnswer(0)
        );
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
