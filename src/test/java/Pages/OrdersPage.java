package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class OrdersPage {
    WebDriver driver;

    By orderSearchInput = By.name("qo");
    By orderSearchButton = By.xpath("//h1[text()='Orders']/following-sibling::form//button");

    By projectSearchInput = By.name("qp");
    By projectSearchButton = By.xpath("//h1[text()='Custom requests']/following-sibling::form//button");

    public OrdersPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchOrders(String customerName) {
        driver.findElement(orderSearchInput).clear();
        driver.findElement(orderSearchInput).sendKeys(customerName);
        driver.findElement(orderSearchButton).click();
    }

    public void searchProjects(String customerName) {
        driver.findElement(projectSearchInput).clear();
        driver.findElement(projectSearchInput).sendKeys(customerName);
        driver.findElement(projectSearchButton).click();
    }
}
