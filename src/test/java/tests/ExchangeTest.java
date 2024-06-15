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

import pages.ExchangePage;
import resources.BaseClass;
import utils.ExcelUtils;
import utils.ExtentReport;

public class ExchangeTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(ExchangeTest.class);
    private ExtentTest test;

    @Test(dataProvider = "exchangeTestData", groups = {"regression"}, priority = 1, enabled = true)
    public void exchangeTest(String url, String executionRequired, String pincode) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("ExchangeTest");
        ExchangePage exchangePage = new ExchangePage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            exchangePage.enterPincode(pincode);
            logger.info("Entered Pincode");
            Thread.sleep(2000); // Sleep for 2 seconds (adjust the duration as needed)
            exchangePage.scrollAndClickExchangeButton();
            logger.info("Clicked exchange Button");
            Thread.sleep(3000); // Sleep for 3 seconds (adjust the duration as needed)
            String pageTitle = driver.getTitle();
            Assert.assertEquals(pageTitle, "vivo T2 Pro 5G (Dune Gold, 128 GB) (8 GB RAM)", "Page title is not as expected");

            // Log test success in Extent Report
            test.pass("Test Passed");
        } catch (AssertionError e) {
            String screenshotFileName = "exchangeSS";
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

    @DataProvider(name = "exchangeTestData")
    public Object[][] getExchangeTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "exchangeSheet");
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
