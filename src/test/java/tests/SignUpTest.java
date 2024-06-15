package tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import utils.ExcelUtils;
import pages.SignUpPage;
import resources.BaseClass;
import utils.ExtentReport;
import MyListeners.TestListener;

@Listeners({ TestListener.class }) // Add TestListener as a listener
public class SignUpTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(SignUpTest.class);
    private ExtentTest test;

    @Test(dataProvider = "signUpTestData", groups = {"functional"}, priority = 1, enabled = true)
    public void signUpTest(String url, String executionRequired, String phoneNumber) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        test = ExtentReport.getInstance().createTest("SignUpTest");

        SignUpPage signUpPage = new SignUpPage(driver);
        driver.get(url);
        driver.manage().window().maximize();
        


        try {
            signUpPage.clickLoginButton();
            logger.info("Clicked Login Button");
            signUpPage.clickSignUpLink();
            logger.info("Clicked SignUp Link");
            signUpPage.enterPhoneNumber(phoneNumber);
            logger.info("Entered Phone Number");
            signUpPage.clickContinueButton();
            logger.info("Clicked Request OTP button");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Online Shopping India | Buy Mobiles, Electronics, Appliances, Clothing and More Online at Flipkart.com", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "signUpSS";
            String screenshotPath = "/ExitProject/screenshots" + screenshotFileName + ".png";
            try {
                getScreenshot(screenshotPath);
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                logger.error("Test failed");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


    @DataProvider(name = "signUpTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "ExcelFile/FlipkartReadDataExcel.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "signUpSheet");
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
