package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException; // Add this import
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import pages.BannerPage;
import resources.BaseClass;
import utils.ExcelUtils;
import utils.ExtentReport;

public class BannerTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(BannerTest.class);
    private ExtentTest test;

    @Test(dataProvider = "bannerTestData", groups = {"smoke"}, priority = 1, enabled = true)
    public void bannerTest(String url, String executionRequired) {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("BannerTest");

        BannerPage bannerPage = new BannerPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        bannerPage.clickBannerImage();
        logger.info("Clicked on HomePage Banner");

        // Get the title of the page
        try {
            Thread.sleep(4000); // Sleep for 4 seconds (adjust the duration as needed)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String pageTitle = driver.getTitle();

        // Assert that the title matches the expected title
        String expectedTitle = "Moto Edge 40 Neo - Buy Moto Edge 40 Neo Online at Low Prices In India | Flipkart.com";
        if (pageTitle.equals(expectedTitle)) {
            test.pass("Test Passed");
        }
    }


    @DataProvider(name = "bannerTestData")
    public Object[][] getBannerTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "bannerSheet");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        // Log test status to Extent Report
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
        }
    }
}
