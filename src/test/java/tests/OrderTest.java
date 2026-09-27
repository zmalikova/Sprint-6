package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.OrderPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    private WebDriver driver;

    @Test
    public void orderScooterTest() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(
                "https://qa-scooter.praktikum-services.ru/"
        );

        OrderPage orderPage = new OrderPage(driver);

        // Нажимаем «Заказать» на главной странице
        driver.findElement(
                By.xpath("//button[normalize-space()='Заказать']")
        ).click();

        // Первая часть формы
        orderPage.setName("Иван");
        orderPage.setSurname("Иванов");
        orderPage.setAddress("Москва, ул. Тверская, 1");
        orderPage.setMetro("Черкизовская");
        orderPage.setPhone("+79991234567");

        orderPage.clickNextButton();

        // Вторая часть формы
        orderPage.setDeliveryDate("30.09.2026");
        orderPage.setRentalPeriod("сутки");
        orderPage.selectBlackColor();
        orderPage.setComment(
                "Позвонить за 10 минут до доставки"
        );

        // Оформление заказа
        orderPage.clickOrderButton();

        // Подтверждение
        orderPage.confirmOrder();

        // Проверка
        assertTrue(
                orderPage.isOrderCreated(),
                "Сообщение об успешном создании заказа не появилось"
        );
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}