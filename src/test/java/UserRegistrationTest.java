import Pages.BasePage;
import Pages.LoginPage;
import Pages.BasePage;

import Pages.RegisterPage;
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

public class UserRegistrationTest {

    WebDriver driver;
    LoginPage loginPage;
    BasePage basePage;
    RegisterPage registerPage;

    @BeforeMethod
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://127.0.0.1:8000/");
        basePage = new BasePage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test
    public void testPasswordMismatchError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        basePage.register();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        String randomUser = "user_" + System.currentTimeMillis();
        registerPage.registerUser(randomUser, "John", "Doe", randomUser + "@test.com", "Password123", "Pass123!");

        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-danger")));

        String actualErrorMessage = errorElement.getText().trim();
        String expectedErrorMessage = "The two password fields didn’t match.";

        // 3. Assertion
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "The mismatch error message is missing or incorrect!");
    }

    @Test
    public void verifySuccessfulRegistration() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        RegisterPage registerPage = new RegisterPage(driver);

        String timestamp = String.valueOf(System.currentTimeMillis());
        String uniqueUser = "User" + timestamp.substring(8); // e.g., User54321
        String uniqueEmail = uniqueUser + "@testmail.com";

        basePage.register();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        registerPage.registerUser(uniqueUser, "Test", "User", uniqueEmail, "SecurePass123!", "SecurePass123!");

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));

        // Assertion: Check if the URL changed or a logout button appeared
        String expectedUrl = "http://127.0.0.1:8000/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed or redirected incorrectly!");
    }

    @AfterMethod
    public void afterEachTest(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            ScreenshotUtils.takeScreenshot(driver, result.getName());
            System.out.println("Screenshot taken for failed test: " + result.getName());
        }
    }
    @AfterMethod
    public void teardownTest() {
        driver.quit();
    }
}