package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    //ПЕрвый экран

    private final By nameInput =
            By.xpath("//input[@placeholder='* Имя']");

    private final By surnameInput =
            By.xpath("//input[@placeholder='* Фамилия']");

    private final By addressInput =
            By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");

    private final By metroInput =
            By.xpath("//input[@placeholder='* Станция метро']");

    private final By phoneInput =
            By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    private final By nextButton =
            By.xpath("//button[normalize-space()='Далее']");


    // Второй экран

    private final By deliveryDateInput =
            By.xpath("//input[contains(@placeholder,'Когда привезти')]");

    private final By datePicker =
            By.cssSelector(".react-datepicker");

    private final By rentalPeriodDropdown =
            By.cssSelector(".Dropdown-control");

    private final By blackColorCheckbox =
            By.id("black");

    private final By greyColorCheckbox =
            By.id("grey");

    private final By commentInput =
            By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопка "Заказать" на форме
    private final By orderButton =
            By.xpath("//button[normalize-space()='Заказать']");

    // Кнопка "Да" в модальном окне подтверждения
    private final By confirmOrderButton =
            By.xpath("//button[normalize-space()='Да']");

    // Сообщение после успешного оформления
    private final By successMessage =
            By.xpath("//*[contains(normalize-space(),'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void setName(String name) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nameInput)
        ).sendKeys(name);
    }

    public void setSurname(String surname) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(surnameInput)
        ).sendKeys(surname);
    }

    public void setAddress(String address) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(addressInput)
        ).sendKeys(address);
    }

    public void setMetro(String metro) {

        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(metroInput)
        );

        input.click();
        input.sendKeys(metro);

        By metroOption = By.xpath(
                "//li[contains(@class,'select-search__row')]" +
                        "[contains(normalize-space(.),'" + metro + "')]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(metroOption)
        ).click();
    }

    public void setPhone(String phone) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(phoneInput)
        ).sendKeys(phone);
    }

    public void clickNextButton() {

        wait.until(
                ExpectedConditions.elementToBeClickable(nextButton)
        ).click();
    }

    public void setDeliveryDate(String date) {

        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(
                        deliveryDateInput
                )
        );

        input.click();
        input.clear();
        input.sendKeys(date);

        input.sendKeys(Keys.ESCAPE);


        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        datePicker
                )
        );
    }

    public void setRentalPeriod(String period) {

        closeCalendar();

        WebElement dropdown = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        rentalPeriodDropdown
                )
        );

        // Прокручиваем к выпадающему списку
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                dropdown
        );

        WebElement clickableDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        rentalPeriodDropdown
                )
        );

        clickableDropdown.click();

        By option = By.xpath(
                "//div[contains(@class,'Dropdown-option')]" +
                        "[normalize-space()='" + period + "']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(option)
        ).click();
    }

    public void selectBlackColor() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        blackColorCheckbox
                )
        ).click();
    }

    public void selectGreyColor() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        greyColorCheckbox
                )
        ).click();
    }

    public void setComment(String comment) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        commentInput
                )
        ).sendKeys(comment);
    }

    // Оформление заказа

    public void clickOrderButton() {

        closeCalendar();

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(
                        orderButton
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                button
        );

        button.click();
    }

    // Подтверждение заказа

    public void confirmOrder() {

        WebElement yesButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        confirmOrderButton
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                yesButton
        );

        yesButton.click();
    }


    public boolean isOrderCreated() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        successMessage
                )
        ).isDisplayed();
    }


    private void closeCalendar() {

        if (driver.findElements(datePicker).isEmpty()) {
            return;
        }

        try {

            WebElement input = driver.findElement(
                    deliveryDateInput
            );


            input.sendKeys(Keys.ESCAPE);

        } catch (Exception ignored) {
        }

        try {


            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            datePicker
                    )
            );

        } catch (Exception ignored) {
        }
    }
}
