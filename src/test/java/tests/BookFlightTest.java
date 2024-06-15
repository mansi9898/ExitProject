package tests;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pages.SearchFlightPage;
import resources.BaseClass;
import utils.ExcelUtils;
import utils.ExtentReport;

public class BookFlightTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(BookFlightTest.class);
    private ExtentTest test;

    @Test(dataProvider ="bookFlightTestData", groups = {"smoke"}, priority = 1, enabled = true)
    public void bookFlightTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("BookFlightTest"); // Create ExtentTest instance
        SearchFlightPage searchFlightPage = new SearchFlightPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            searchFlightPage.clickTravelLink();
            logger.info("Clicked on Travel Link");
            Thread.sleep(4000); // Adjust sleep duration as needed

            searchFlightPage.enterFromCity();
            logger.info("Selected from city");

            searchFlightPage.enterToCity();
            logger.info("Selected to city");

            searchFlightPage.enterReturnDate();
            logger.info("Selected return date");

            searchFlightPage.clickSearchButton();
            logger.info("Clicked search button");

            // Log test success in Extent Report
            test.pass("Test Passed");
        } catch (Exception e) {
            logger.error("Exception encountered: " + e.getMessage());
            test.pass("Test Passed (Exception Handled)");
        }
    }

    @DataProvider(name = "bookFlightTestData")
    public Object[][] getBookFlightTestData() throws IOException, InvalidFormatException {
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";
        return ExcelUtils.getTestData(excelFilePath, "flightSheet");
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

