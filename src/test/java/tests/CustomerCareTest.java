package tests;

import java.io.IOException;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import utils.ExcelUtils;
import pages.CustomerCarePage;
import resources.BaseClass;
import utils.ExtentReport;

public class CustomerCareTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(CustomerCareTest.class);
    private ExtentTest test;

    @Test(dataProvider = "customerTestData", groups = {"smoke"}, priority = 1, enabled = true)
    public void customerCareTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("CustomerCareTest"); // Create ExtentTest instance

        CustomerCarePage ccpage = new CustomerCarePage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            // Refresh the page
            driver.navigate().refresh();
            ccpage.goToCustomerCareInfo();
            logger.info("Navigated to Customer Care Page");
            String pageTitle = driver.getTitle();
            Assert.assertEquals(pageTitle, "Online Shopping India | Buy Mobiles, Electronics, Appliances, Clothing and More Online at Flipkart.com", "Page title is not as expected");

            // Log test success in Extent Report
            test.log(Status.PASS, "Test Passed");
        } catch (AssertionError e) {
            String screenshotFileName = "customerSS";
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

    @DataProvider(name = "customerTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "customerSheet");
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
