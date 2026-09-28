package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pageobjects.MainPage;
import pageobjects.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("orderData")
    public void orderScooterTest(
            boolean useTopButton,
            String name,
            String surname,
            String address,
            String metro,
            String phone,
            String deliveryDate,
            String rentalPeriod,
            String comment
    ) {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        if (useTopButton) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        // Первая часть формы
        orderPage.setName(name);
        orderPage.setSurname(surname);
        orderPage.setAddress(address);
        orderPage.setMetro(metro);
        orderPage.setPhone(phone);

        orderPage.clickNextButton();

        // Вторая часть формы
        orderPage.setDeliveryDate(deliveryDate);
        orderPage.setRentalPeriod(rentalPeriod);
        orderPage.selectBlackColor();
        orderPage.setComment(comment);

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

    private static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of(
                        true,
                        "Иван",
                        "Иванов",
                        "Москва, ул. Тверская, 1",
                        "Черкизовская",
                        "+79991234567",
                        "30.09.2026",
                        "сутки",
                        "Позвонить за 10 минут до доставки"
                ),
                Arguments.of(
                        false,
                        "Петр",
                        "Петров",
                        "Санкт-Петербург, Невский проспект, 10",
                        "Спортивная",
                        "+79997654321",
                        "01.10.2026",
                        "неделя",
                        "Позвонить заранее"
                )
        );
    }
}
