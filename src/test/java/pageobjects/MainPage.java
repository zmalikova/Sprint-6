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
            WebElement button = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(cookieButton)
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    button
            );

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
        List<WebElement> questionsList = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(questions)
        );

        WebElement question = questionsList.get(index);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                question
        );

        wait.until(ExpectedConditions.elementToBeClickable(question)).click();
    }


    // Получить текст открытого ответа FAQ
    public String getAnswer(int index) {
        List<WebElement> answersList = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(answers)
        );

        return wait.until(driver -> {
            WebElement answer = answersList.get(index);

            if (answer.isDisplayed()) {
                return answer.getText();
            }

            return null;
        });
    }
}
