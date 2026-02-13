import Pages.*;

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

public class CheckoutProcessTest {
     WebDriver driver;
     BasePage basePage;
     CartDetailPage cartDetailPage;
     CheckoutPage checkoutPage;
     PaymentSummaryPage paymentSummaryPage;
    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test;

     @BeforeClass
     public void setupTest() {
         WebDriverManager.chromedriver().setup();
         driver = new ChromeDriver();
         driver.get("http://127.0.0.1:8000/");
         basePage = new BasePage(driver);
         cartDetailPage = new CartDetailPage(driver);
         checkoutPage = new CheckoutPage(driver);
         paymentSummaryPage = new PaymentSummaryPage(driver);
     }
    @BeforeMethod
    public void registerTest(Method method) {
        // This automatically names the report entry after your @Test method name
        test = extent.createTest(method.getName());
    }

    @Test(priority = 1)
    public void verifyCorrectItemsInCart() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Products")));
        basePage.products();

        WebElement productHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h5")));
        String expectedName = productHeader.getText().trim();

        WebElement orderButton = driver.findElement(By.xpath("//button[contains(@class, 'btn-custom') and text()='Order']"));
        orderButton.click();

        wait.until(ExpectedConditions.urlContains("cart"));

        WebElement cartItemLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.text-dark")));
        Assert.assertTrue(cartItemLink.getText().contains(expectedName),
                "Expected " + expectedName + " but found " + cartItemLink.getText());
    }
    @Test(priority = 2)
    public void verifyCorrectCheckout() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();

        String expectedProductName = driver.findElement(By.cssSelector("table tbody tr td a")).getText();
        cartDetailPage.clickCheckout();

        wait.until(ExpectedConditions.urlContains("checkout"));
        String checkoutProductName = driver.findElement(By.cssSelector("table tbody tr td")).getText();
        Assert.assertEquals(checkoutProductName, expectedProductName, "Product name mismatch in checkout summary!");
    }
    @Test(priority = 3)
    public void testCheckoutAndPaymentFlow() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.urlContains("checkout"));

        checkoutPage.fillDeliveryInfo("Tester","Tester","test@gmail.com","123 Zagrebacka ulica");
        checkoutPage.fillPaymentInfo("1234567812345678","12","2026","123");
        checkoutPage.clickPurchase();

        wait.until(ExpectedConditions.urlContains("payment"));

        WebElement summaryHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[text()='Summary']")));
        Assert.assertTrue(summaryHeader.isDisplayed(), "Failed to reach Payment Summary page!");


        String addressText = paymentSummaryPage.getConfirmedAddress();
        Assert.assertTrue(addressText.contains("123 Zagrebacka ulica"), "Final address does not match input!");
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
    public void teardownTest()
    {
        extent.flush();
        if (driver != null) {
            driver.quit();
        }
    }
}
