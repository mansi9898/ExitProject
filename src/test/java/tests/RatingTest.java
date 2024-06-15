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
import utils.ExcelUtils;
import pages.RateProductPage;
import resources.BaseClass;
import utils.ExtentReport;

public class RatingTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(RatingTest.class);
    private ExtentTest test;

    @Test(dataProvider = "ratingTestData",groups = {"sanity"}, priority = 1, enabled = true)
    public void ratingTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("RatingTest"); // Create ExtentTest instance

        RateProductPage ratingPage = new RateProductPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            ratingPage.clickRating();
            logger.info("Clicked rating button");

            Thread.sleep(2000); // Sleep for 2 seconds (adjust the duration as needed)

            ratingPage.clickRatingButton();
            logger.info("Clicked Rate Product Button");

            Thread.sleep(3000); // Sleep for 3 seconds (adjust the duration as needed)

            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "POCO M6 Pro 5G ( 128 GB Storage, 6 GB RAM ) Online at Best Price On Flipkart.com",
                    "Page title is not as expected");

            // Log test success in Extent Report
            test.log(Status.PASS, "Test Passed");
        } catch (AssertionError e) {
            String screenshotFileName = "ratingSS";
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

    @DataProvider(name = "ratingTestData")
    public Object[][] getRatingTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "ratingSheet");
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
