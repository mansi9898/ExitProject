package tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import pages.GiftcardPage;
import resources.BaseClass;
import utils.ExtentReport;

public class GiftcardTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(GiftcardTest.class);
    private ExtentTest test;

    @Test(dataProvider = "giftcardTestData", groups = { "regression" }, priority = 1, enabled = true)
    public void giftcardTest(String url, String executionRequired, String email, String name) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("GiftCardTest"); // Create ExtentTest instance

        GiftcardPage giftcardPage = new GiftcardPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            WebElement giftcardLink = giftcardPage.getGiftcardLinkElement();
            Actions actions = new Actions(driver);
            actions.moveToElement(giftcardLink).click().perform();
            logger.info("Navigated to Giftcard Creation Page");

            giftcardPage.enterEmail(email);
            logger.info("Entered Email");

            giftcardPage.enterName(name);
            logger.info("Entered Name");

            giftcardPage.selectVoucher();
            logger.info("Selected Voucher value");

            WebElement buyButton = giftcardPage.getBuyButtonLinkElement();
            actions.moveToElement(buyButton).click().perform();
            logger.info("Clicked on Buy Voucher button");

            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Flipkart.com: Secure Payment: Login > Select Shipping Address > Review Order > Place Order", "Page title is not as expected");

            // Log test success in Extent Report
            test.log(Status.PASS, "Test Passed");
        } catch (AssertionError e) {
            String screenshotFileName = "giftcardSS";
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

    @DataProvider(name = "giftcardTestData")
    public Object[][] getGiftcardTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "giftcardSheet");
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