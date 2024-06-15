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

import pages.NavigationPage;
import resources.BaseClass;
import utils.ExcelUtils;
import utils.ExtentReport;

public class NavigationTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(NavigationTest.class);
    private ExtentTest test;

    @Test(dataProvider = "navigationTestData", groups = {"sanity"}, priority = 1, enabled = true)
    public void navigationTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("NavigationTest");
        NavigationPage navigationPage = new NavigationPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            navigationPage.selectHomeandFurniture();
            logger.info("Clicked on Home&Furniture Page Banner");
            navigationPage.goToOfferZone();
            logger.info("Navigated to the OfferZone Top Deals");
            Thread.sleep(3000); // Sleep for 3 seconds (adjust the duration as needed)
            String pageTitle = driver.getTitle();
            Assert.assertEquals(pageTitle, "Top Deals Store Online - Buy Top Deals Online at Best Price in India | Flipkart.com", "Page title is not as expected");

            // Log test success in Extent Report
            test.pass("Test Passed");
        } catch (AssertionError e) {
            String screenshotFileName = "navigationSS";
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

    @DataProvider(name = "navigationTestData")
    public Object[][] getNavigationTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "navigationSheet");
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
