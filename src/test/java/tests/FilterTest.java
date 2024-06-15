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
import utils.ExtentReport;
import pages.BannerPage;
import pages.FilterPage;
import resources.BaseClass;

public class FilterTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(FilterTest.class);
    private ExtentTest test;

    @Test(dataProvider = "bannerTestData", priority = 1, groups = {"banner"}, enabled = true)
    public void bannerTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("BannerTest"); // Create ExtentTest instance
        BannerPage bannerPage = new BannerPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            bannerPage.clickBannerImage();
            logger.info("Clicked on HomePage Banner");

            Thread.sleep(4000); // Sleep for 4 seconds (adjust the duration as needed)

            String pageTitle = driver.getTitle();
            String expectedTitle = "Moto Edge 40 Neo - Buy Moto Edge 40 Neo Online at Low Prices In India | Flipkart.com";
            //Assert.assertEquals(pageTitle, expectedTitle, "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "bannerSS";
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

    @Test(dataProvider = "filterTestData", priority = 2, groups = {"filter"}, enabled = true, dependsOnMethods = {"bannerTest"})
    public void filterTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("FilterTest"); // Create ExtentTest instance
        FilterPage filterPage = new FilterPage(driver);
        driver.manage().window().maximize();

        try {
            filterPage.minPriceFilter();
            logger.info("Applied Min Price Filter");

            Thread.sleep(2000); // Sleep for 2 seconds (adjust the duration as needed)

            filterPage.brandFilter();
            logger.info("Applied Brand Filter");

            Thread.sleep(2000); // Sleep for 2 seconds (adjust the duration as needed)

            String pageTitle = driver.getTitle();
            //Assert.assertEquals(pageTitle, "Moto Edge 40 Neo - Buy Moto Edge 40 Neo Online at Low Prices In India | Flipkart.com", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "filterSS";
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

    @DataProvider(name = "bannerTestData")
    public Object[][] getBannerTestData() throws IOException, InvalidFormatException {
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";
        return ExcelUtils.getTestData(excelFilePath, "bannerSheet");
    }

    @DataProvider(name = "filterTestData")
    public Object[][] getFilterTestData() throws IOException, InvalidFormatException {
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";
        return ExcelUtils.getTestData(excelFilePath, "filterSheet");
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
        //ExtentReport.getInstance().flush();
    }
}
