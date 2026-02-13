import Utils.ExtentManager;
import Utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.Duration;

public class OpenAppTest {
    public WebDriver driver;
    public String testURL = "http://127.0.0.1:8000/";
    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test;

    @BeforeMethod
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to(testURL);
    }
    @BeforeMethod
    public void registerTest(Method method) {
        // This automatically names the report entry after your @Test method name
        test = extent.createTest(method.getName());
    }

    @Test
    public void openAppTest() throws InterruptedException {
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        WebElement testLink =
                driver.findElement(By.xpath("//a[text()='EMIBU']")
                );
        Assert.assertEquals(testLink.getText(), "EMIBU");
        System.out.print(testLink.getText());
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
