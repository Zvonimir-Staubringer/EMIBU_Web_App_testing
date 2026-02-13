import Pages.*;
import Pages.BasePage;

import Utils.ExtentManager;
import Utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
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

import java.lang.reflect.Method;
import java.time.Duration;

public class UserRegistrationTest {

    WebDriver driver;
    BasePage basePage;
    RegisterPage registerPage;
    ProfilePage profilePage;
    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test;

    @BeforeMethod
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://127.0.0.1:8000/");
        basePage = new BasePage(driver);
        registerPage = new RegisterPage(driver);
        profilePage = new ProfilePage(driver);
    }
    @BeforeMethod
    public void registerTest(Method method) {
        // This automatically names the report entry after your @Test method name
        test = extent.createTest(method.getName());
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
        basePage.profile();
        String actualEmail = profilePage.getUserEmail();
        Assert.assertEquals(actualEmail, uniqueEmail, "Login failed or redirected incorrectly!");
    }

    @AfterMethod
    public void afterEachTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtils.takeScreenshot(driver, result.getName());

            String screenshotPath = "../screenshots/" + result.getName() + ".png";
            test.fail("Test Failed: " + result.getThrowable());
            test.addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        }
    }
    @AfterMethod
    public void teardownTest() {
        driver.quit();
    }
}