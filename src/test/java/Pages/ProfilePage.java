package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProfilePage {
    WebDriver driver;

    By userEmailText = By.xpath("//p[contains(text(), 'Email:')]");
    By ordersButton = By.linkText("Profile");//

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getUserEmail() {
        return driver.findElement(userEmailText).getText().replace("Email: ", "").trim();
    }

    public void orders() {
        driver.findElement(ordersButton).click();
    }
}
