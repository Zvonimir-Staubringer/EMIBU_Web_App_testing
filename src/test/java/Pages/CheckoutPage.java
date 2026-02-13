package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    WebDriver driver;

    // Locators
    By firstName = By.id("first_name"); //
    By lastName = By.id("last_name"); //
    By email = By.id("email"); //
    By address = By.id("address"); //
    By cardNumber = By.id("cardnumber"); //
    By expMonth = By.id("expirationmonth"); //
    By expYear = By.id("expirationyear"); //
    By cvv = By.id("CVVnumber"); //
    By purchaseButton = By.cssSelector("input[value='Purchase']"); //

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillDeliveryInfo(String fName, String lName, String mail, String addr) {
        driver.findElement(firstName).sendKeys(fName); //
        driver.findElement(lastName).sendKeys(lName); //
        driver.findElement(email).sendKeys(mail); //
        driver.findElement(address).sendKeys(addr); //
    }

    public void fillPaymentInfo(String card, String month, String year, String cvvNum) {
        driver.findElement(cardNumber).sendKeys(card); //
        driver.findElement(expMonth).sendKeys(month); //
        driver.findElement(expYear).sendKeys(year); //
        driver.findElement(cvv).sendKeys(cvvNum); //
    }

    public void clickPurchase() {
        driver.findElement(purchaseButton).click(); //
    }
}
