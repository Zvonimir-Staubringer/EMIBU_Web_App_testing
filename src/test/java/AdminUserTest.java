import Pages.BasePage;
import Pages.LoginPage;

import Pages.OrdersPage;
import Pages.ProfilePage;
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
import java.util.List;

public class AdminUserTest {
    WebDriver driver;
    LoginPage loginPage;
    BasePage basePage;
    ProfilePage profilePage;
    OrdersPage ordersPage;
    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test;

    @BeforeClass
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://127.0.0.1:8000/");
        loginPage = new LoginPage(driver);
        basePage = new BasePage(driver);
        profilePage = new ProfilePage(driver);
        ordersPage = new OrdersPage(driver);
    }
    @BeforeMethod
    public void registerTest(Method method) {
        // This automatically names the report entry after your @Test method name
        test = extent.createTest(method.getName());
    }

    @Test(priority = 1)
    public void succesfulAdminLogin() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Login")));
        basePage.login();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        loginPage.login("admin", "admin");

        String expectedUrl = "http://127.0.0.1:8000/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed or redirected incorrectly!");
    }
    @Test(priority = 2)
    public void succesfulAdminDashboard() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Profile")));
        basePage.profile();

        String expectedEmail = "admin@gmail.com";
        String actualEmail = profilePage.getUserEmail();
        Assert.assertEquals(actualEmail, expectedEmail, "Login failed or wrong user!");
    }
    @Test(priority = 3)
    public void workingOrdersSearch() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
        String searchName = "Marko";

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Profile")));
        driver.findElement(By.linkText("Profile")).click();
        driver.get("http://127.0.0.1:8000/store/orders/");

        WebElement oldList = driver.findElement(By.xpath("//div[contains(@class, 'col-md-6')][1]"));

        ordersPage.searchOrders(searchName);

        wait.until(ExpectedConditions.stalenessOf(oldList));

        List<WebElement> orderCustomers = driver.findElements(
                By.xpath("//div[contains(@class, 'col-md-6')][1]//p[contains(text(), 'Customer:')]")
        );

        ordersPage.searchProjects(searchName);

        List<WebElement> projectCustomers = driver.findElements(
                By.xpath("//div[contains(@class, 'col-md-6')][2]//p[contains(text(), 'Customer:')]")
        );

        for (WebElement customer : projectCustomers) {
            Assert.assertTrue(customer.getText().contains(searchName),
                    "Project column found a non-matching customer: " + customer.getText());
        }
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
    @AfterClass
    public void teardownTest() {
        extent.flush();
        driver.quit();
    }
}
