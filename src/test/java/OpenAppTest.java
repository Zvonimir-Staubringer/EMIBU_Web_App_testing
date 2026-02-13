import Utils.ScreenshotUtils;
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

import java.time.Duration;

public class OpenAppTest {
    public WebDriver driver;
    public String testURL = "http://127.0.0.1:8000/";

    @BeforeMethod
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to(testURL);
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
