package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FilterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor to initialize the WebDriver
    public FilterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickBannerImage() {
        WebElement bannerImage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='zlQd20 _1eDlvI']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bannerImage);
        bannerImage.click();
    }

    public void minPriceFilter() {
        try {
            WebElement minPrice = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@class='Gn+jFg']")));
            minPrice.click();
            Select select = new Select(minPrice);
            select.selectByValue("10000");
        } catch (StaleElementReferenceException e) {
            // Retry the operation
            minPriceFilter();
        }
    }

    public void brandFilter() {
        try {
            WebElement brand = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='XqNaEv']")));
            brand.click();
        } catch (StaleElementReferenceException e) {
            // Retry the operation
            brandFilter();
        }
    }
}
