package tests;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.JavascriptExecutor;
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
import pages.CartPage;
import resources.BaseClass;
import utils.ExtentReport;

public class CartTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(CartTest.class);
    private ExtentTest test;

    @Test(dataProvider = "cartTestData", groups = {"smoke"}, priority = 1, enabled = true)
    public void cartTest(String url, String executionRequired, String email) throws IOException {
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("AddtoCartTest");

        CartPage cartPage = new CartPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(cartPage.getCartButtonElement()));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cartButton);
            cartPage.clickCartButton();
            logger.info("Clicked on Cart Button");

            WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(cartPage.getPlaceOrderButtonElement()));
            cartPage.clickPlaceOrderButton();
            logger.info("Clicked on Place Order Button");

            WebElement emailField = wait.until(ExpectedConditions.visibilityOf(cartPage.getEmailFieldElement()));
            cartPage.enterEmail(email);
            logger.info("Entered email");

            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);

            //Assertion is applied here
            String expectedPageTitle = "Flipkart.com: Secure Payment: Login > Select Shipping Address > Review Order > Place Order";
            Assert.assertEquals(pageTitle, expectedPageTitle, "Page title is not as expected");

            test.log(Status.PASS, "Test Passed");
        } catch (Exception e) {
            String screenshotFileName = "cartTestFailure_" + System.currentTimeMillis();
            String screenshotPath = "ExitProject/screenshots/" + screenshotFileName + ".png";
            try {
                getScreenshot(screenshotPath);
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                logger.error("Test failed: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
        

    @DataProvider(name = "cartTestData")
    public Object[][] getCartTestData() throws IOException, InvalidFormatException {
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";
        return ExcelUtils.getTestData(excelFilePath, "cartSheet");
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
