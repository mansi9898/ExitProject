package resources;

import java.io.File;


import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExtentReport;

public class BaseClass {
    protected static WebDriver driver;
    
    @BeforeClass
    public void setUp() {
    	// Initialize Extent Report
        ExtentReport.getInstance();
        // Read configuration from config.properties
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream("Utilities/config.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String browser = prop.getProperty("browser");
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless"));

        if (driver == null) {
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                driver = new ChromeDriver(options);
            } else if (browser.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    // Edge does not support headless mode
                    throw new IllegalArgumentException("Headless mode is not supported for Edge browser.");
                }
                driver = new EdgeDriver(options);
            } else if (browser.equalsIgnoreCase("firefox")) {
            	FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                // No need to specify the binary path, the system will use the default installed Firefox
                driver = new FirefoxDriver(options);
            } else {
                throw new IllegalArgumentException("Invalid browser specified");
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("Test skipped: " + result.getThrowable());
        } else {
            System.out.println("Test passed");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
     // Flush Extent Report at the end of the class
        ExtentReport.flush();
    }
    

    public static void getScreenshot(String screenshotPath) throws IOException {
        // Method for taking screenshots if needed
    	TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File(screenshotPath));
    }
}

