package Exercise;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FindAlgorithm {
    public static void main(String[] args) {


        WebDriverManager.firefoxdriver().setup();
        FirefoxDriver driver = new FirefoxDriver();
       // SafariDriver driver = new SafariDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://sdetchallenge.fetch.com/");


        Random random = new Random(); // will generate random numbers
        int start = random.nextInt(9);  // Generates a random number from 0 to 8
        int end = random.nextInt(9);

        // left bar
        List<WebElement> leftBar = driver.findElements(By.cssSelector("[id*='left_']"));
        WebElement leftCell = leftBar.get(0);

        // right bar
        List<WebElement> rightBar = driver.findElements(By.cssSelector("[id*='right_']"));
        WebElement rightCell = rightBar.get(0);

        //weight btn
        WebElement weighBtn = driver.findElement(By.id("weigh"));

        int finalNum = 0;

        while (true) {
            leftCell.clear();
            leftCell.sendKeys(String.valueOf(start));

            rightCell.clear();
            rightCell.sendKeys(String.valueOf(end));

            weighBtn.click();

            List<WebElement> resultElement = driver.findElements(By.cssSelector("#reset.button"));
            WebElement desiredElement = resultElement.get(0); // Assuming you want the first element
            String resultValue = desiredElement.getText();


            if (resultValue.equals("=")) {
                start++;
                end++;

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement resetBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Reset')]")));
                resetBtn.click();
                continue;
            }

            if (resultValue.equals("<")) {
                finalNum = start;
                break;
            } else if (resultValue.equals(">")) {
                finalNum = end;
                break;
            }
        }
        System.out.println("Fake bar number is: " + finalNum);

            List<WebElement> numberToClick = driver.findElements(By.xpath("//button[contains(@id, 'coin_')]"));

            for (WebElement numberElement : numberToClick) {
                if (numberElement.getAttribute("id").contains(String.valueOf(finalNum))) {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(numberElement).click().perform();

                    // Wait for the alert to be present
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    Alert alert = wait.until(ExpectedConditions.alertIsPresent());

                    // Get the text from the alert
                    String alertText = alert.getText();
                    System.out.println("Alert Message: " + alertText);

                    // Accept the alert
                    alert.accept();
                }
            }

            driver.quit();

        }
}


//This way also possible but I decided to go with random numbers
//it works as long as numbers don't end but while you have no numbers to compare
//and it didn't give you < or > sign, we cannot proceed further
//        int start = 0;
//        int end = 8;
//        int middle = end / 2;