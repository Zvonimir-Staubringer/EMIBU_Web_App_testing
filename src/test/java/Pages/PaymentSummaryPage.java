package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentSummaryPage {
    WebDriver driver;

    // Locators
    By orderAddress = By.xpath("//p[contains(text(), 'Address:')]"); //
    By finalTotal = By.xpath("//h3[contains(text(), 'Total:')]"); ////

    public PaymentSummaryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getConfirmedAddress() {
        return driver.findElement(orderAddress).getText(); //
    }
}