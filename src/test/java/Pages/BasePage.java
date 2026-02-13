package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {
    WebDriver driver;

    // Locators
    By logoutButton = By.linkText("Logout");
    By loginButton = By.linkText("Login");
    By registerButton = By.linkText("Register");
    By productsButton = By.linkText("Products");
    By profileButton = By.linkText("Profile");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void login() {
        driver.findElement(loginButton).click();
    }

    public void logout() {
        driver.findElement(logoutButton).click();
    }

    public void register() {
        driver.findElement(registerButton).click();
    }

    public void products() {
        driver.findElement(productsButton).click();
    }

    public void profile() {
        driver.findElement(profileButton).click();
    }
}

