package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Кнопка «Заказать» в верхней части страницы
    private By orderButtonTop = By.xpath(
            "//div[contains(@class, 'Header_Nav')]//button[contains(text(), 'Заказать')]"
    );

    // Кнопка «Заказать» в нижней части страницы
    private By orderButtonBottom = By.xpath(
            "//div[contains(@class, 'Home_FinishButton')]//button[contains(text(), 'Заказать')]"
    );

    // Вопросы в разделе «Вопросы о важном»
    private By questions = By.className("accordion__button");

    // Ответы на вопросы
    private By answers = By.className("accordion__panel");

    // Кнопка принятия cookie
    private By cookieButton = By.id("rcc-confirm-button");


    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    // Закрыть уведомление о cookie
    public void clickCookieButton() {
        try {
            wait.until(
                    ExpectedConditions.elementToBeClickable(cookieButton)
            ).click();

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(cookieButton)
            );

        } catch (TimeoutException e) {
            // Cookie-баннер не появился
        }
    }



    // Нажать верхнюю кнопку «Заказать»
    public void clickOrderButtonTop() {
        wait.until(
                ExpectedConditions.elementToBeClickable(orderButtonTop)
        ).click();
    }


    // Нажать нижнюю кнопку «Заказать»
    public void clickOrderButtonBottom() {
        wait.until(
                ExpectedConditions.elementToBeClickable(orderButtonBottom)
        ).click();
    }


    // Нажать на вопрос FAQ
    public void clickQuestion(int index) {
        By question = By.id("accordion__heading-" + index);

        WebElement questionElement = wait.until(
                ExpectedConditions.elementToBeClickable(question)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                questionElement
        );
        questionElement.click();
    }

    // Получить текст открытого ответа FAQ
    public String getAnswer(int index) {
        By questionLocator = By.id("accordion__heading-" + index);
        WebElement question = wait.until(
                ExpectedConditions.visibilityOfElementLocated(questionLocator)
        );
        String answerId = question.getAttribute("aria-controls");
        WebElement answer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id(answerId)
                )
        );

        return answer.getText();
    }

}
