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

import pages.SharePage;
import resources.BaseClass;
import utils.ExcelUtils;
import utils.ExtentReport;

public class ShareTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(ShareTest.class);
    private ExtentTest test;

    @Test(dataProvider = "shareTestData", groups = {"sanity"}, priority = 1, enabled = true)
    public void shareTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("ShareTest");
        SharePage sharePage = new SharePage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            sharePage.clickShareButton();
            logger.info("Clicked on Share Button");
            sharePage.selectEmail();
            logger.info("Selected Email Option");

            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Apple iPhone 15 Pro Max (Black Titanium, 256 GB)", "Page title is not as expected");

            // Log test success in Extent Report
            test.pass("Test Passed");
        } catch (AssertionError e) {
            String screenshotFileName = "shareSS";
            String screenshotPath = "/ExitProject/screenshots/" + screenshotFileName + ".png";
            try {
                getScreenshot(screenshotPath);
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                logger.error("Test failed");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @DataProvider(name = "shareTestData")
    public Object[][] getShareTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "shareSheet");
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
