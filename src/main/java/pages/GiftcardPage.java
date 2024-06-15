package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class GiftcardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor to initialize the WebDriver
    public GiftcardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));  // Set a timeout of 20 seconds
        PageFactory.initElements(driver, this);
    }

    public WebElement getGiftcardLinkElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='dm']")));
    }

    public void clickGiftcardLink() {
        getGiftcardLinkElement().click();
    }

    public void enterEmail(String emailID) {
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='recipient-email[]']")));
        email.sendKeys(emailID);
    }

    public void enterName(String name) {
        WebElement nameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='recipient-name[]']")));
        nameBox.sendKeys(name);
    }

    public void selectVoucher() {
        WebElement dropDown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='voucher-value[]']")));
        dropDown.click();
        Select select = new Select(dropDown);
        List<WebElement> options = select.getOptions();
        
        for (WebElement option : options) {
            if (option.getText().trim().equals("100")) {
                option.click();
                break;
            }
        }
    }

    public WebElement getBuyButtonLinkElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'QqFHMw') and contains(@class, 'AsTLnx') and contains(@class, '_7Pd1Fp') and contains(text(), 'BUY GIFT CARD FOR')]")));
    }

    public void clickBuyButton() {
        WebElement buyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'QqFHMw') and contains(@class, 'AsTLnx') and contains(@class, '_7Pd1Fp') and contains(text(), 'BUY GIFT CARD FOR')]")));
        buyButton.click();
    }
}

