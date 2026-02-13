package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartDetailPage {
    WebDriver driver;

    // Locators
    By quantityInput = By.name("quantity"); //
    By updateButton = By.xpath("//button[text()='Update']"); //
    By removeLink = By.linkText("Remove"); //
    By checkoutButton = By.cssSelector("button.btn-success"); //
    By totalCartPrice = By.xpath("//h3[contains(text(), 'Total:')]"); //

    public CartDetailPage(WebDriver driver) {
        this.driver = driver;
    }

    public void updateQuantity(String qty) {
        WebElement input = driver.findElement(quantityInput);
        input.clear();
        input.sendKeys(qty);
        driver.findElement(updateButton).click(); //
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click(); //
    }

    public String getTotalPrice() {
        return driver.findElement(totalCartPrice).getText(); //
    }
}
