package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

 public class RegisterPage {
    WebDriver driver;

    // Locators
    By usernameField = By.id("username");
    By firstNameField = By.id("first_name");
    By lastNameField = By.id("last_name");
    By emailField = By.id("email");
    By pass1Field = By.id("password1");
    By pass2Field = By.id("password2");
    By registerBtn = By.cssSelector("button[type='submit']");
    By errorList = By.cssSelector(".alert-danger ul li");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void registerUser(String user, String first, String last, String email, String p1, String p2) {
        driver.findElement(usernameField).sendKeys(user);
        driver.findElement(firstNameField).sendKeys(first);
        driver.findElement(lastNameField).sendKeys(last);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(pass1Field).sendKeys(p1);
        driver.findElement(pass2Field).sendKeys(p2);
        driver.findElement(registerBtn).click();
    }
}