import Pages.BasePage;
import Pages.LoginPage;

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

public class LoginExistingUserTest {

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
    public void verifySuccessfulLogin() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Login")));
        basePage.login();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        loginPage.login("username1", "password1");

        String expectedUrl = "http://127.0.0.1:8000/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed or redirected incorrectly!");
        Thread.sleep(3000);
    }
    @Test(priority = 2)
    public void verifySuccesfulLogout() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
        basePage.logout();

        String expectedUrl = "http://127.0.0.1:8000/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed or redirected incorrectly!");
        Thread.sleep(3000);
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

