import Pages.BasePage;
import Pages.LoginPage;
import Pages.BasePage;

import Utils.ScreenshotUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginNonexistingUserTest {

    WebDriver driver;
    LoginPage loginPage;
    BasePage basePage;

    @BeforeClass
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://127.0.0.1:8000/");
        loginPage = new LoginPage(driver);
        basePage = new BasePage(driver);
    }

    @Test(priority = 1)
    public void verifyUnsuccessfulLogin() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Login")));
        basePage.login();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        loginPage.login("RandomUser123", "WrongPassword123");

        WebElement errorDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-danger")));

        String actualMessage = errorDiv.getText().trim();
        String expectedMessage = "Invalid username or password. Please try again.";

        Assert.assertEquals(actualMessage, expectedMessage, "The error message text does not match!");
    }

    @AfterMethod
    public void afterEachTest(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            ScreenshotUtils.takeScreenshot(driver, result.getName());
            System.out.println("Screenshot taken for failed test: " + result.getName());
        }
    }
    @AfterClass
    public void teardownTest() {
        driver.quit();
    }
}
