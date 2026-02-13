package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // Path where the report will be saved
            ExtentSparkReporter spark = new ExtentSparkReporter("./reports/ExtentReport.html");
            spark.config().setReportName("Web Automation Results");
            spark.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Tester", "Your Name");
        }
        return extent;
    }
}
