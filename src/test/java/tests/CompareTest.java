package tests;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import pages.ComparePage;
import resources.BaseClass;
import utils.ExcelUtils;
import utils.ExtentReport;

public class CompareTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(CompareTest.class);
    private ExtentTest test;

    @Test(dataProvider = "compareTestData", groups = { "smoke" }, priority = 1, enabled = true)
    public void compareTest(String url, String url2, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("CompareTest");
        ComparePage comparePage = new ComparePage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            comparePage.clickCompareButton();
            logger.info("Clicked on compare CheckBox");
            driver.get(url2);
            logger.info("Opened another phone to add to compare list");
            comparePage.clickCompareButton();
            logger.info("Clicked on compare CheckBox");
            comparePage.goToComparePage();
            logger.info("Opened Compare Page");
            Thread.sleep(4000); // Sleep for 4 seconds (adjust the duration as needed)
            String pageTitle = driver.getTitle();
            Assert.assertEquals(pageTitle, "REDMI 12 ( 128 GB Storage, 6 GB RAM ) Online at Best Price On Flipkart.com", "Page title is not as expected");

            // Log test success in Extent Report
            test.pass("Test Passed");
        } catch (AssertionError e) {
            String screenshotFileName = "compareSS";
            String screenshotPath = "/ExitProject/screenshots/" + screenshotFileName + ".png";
            try {
                getScreenshot(screenshotPath);
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                logger.error("Test failed");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "compareTestData")
    public Object[][] getCompareTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "compareSheet");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
        } else {
            test.log(Status.PASS, "Test Passed");
        }
    }
}
