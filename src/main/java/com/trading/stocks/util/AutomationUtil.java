package com.trading.stocks.util;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
public class AutomationUtil {

    public static String openWebPage(String url, String cookieJson) {
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("C:\\Users\\usuan\\Downloads\\Twitter-video-downloader-Chrome-Web-Store.crx"));
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);

        // Parse the JSON cookies
        JSONArray cookiesArray = new JSONArray(cookieJson);
        for (int i = 0; i < cookiesArray.length(); i++) {
            JSONObject cookieObject = cookiesArray.getJSONObject(i);
            Cookie cookie = CommonUtil.getCookie(cookieObject);

            driver.manage().addCookie(cookie);
        }

        // Refresh the page to apply the cookies
        driver.navigate().refresh();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));


        downloadFiles(driver, wait);



        return driver.getTitle();
    }

    public static String downloadFiles(WebDriver webDriver, WebDriverWait wait) {
        WebElement downloadButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("extension-button-hover")));


        // Click each button
        Set<WebElement> clickedButtons = new HashSet<>();

        // Continuously scroll and find buttons until all are clicked
        while (true) {
            // Find all buttons on the page
            List<WebElement> buttons = webDriver.findElements(By.className("extension-button-hover"));

            boolean anyNewButtonClicked = false;

            for (WebElement button : buttons) {
                if (!clickedButtons.contains(button)) {
                    try {
                        // Click the button
                        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
                        clickedButtons.add(button);
                        anyNewButtonClicked = true;
                        // Optionally wait after clicking
                        Thread.sleep(1000); // Adjust as needed
                    } catch (Exception e) {
                        System.out.println("Failed to click button: " + e.getMessage());
                    }
                }
            }

            // Scroll down the page to load more elements
            ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, document.body.scrollHeight);");

            // Break the loop if no new buttons were clicked
            if (!anyNewButtonClicked) {
                break;
            }

            // Wait for new content to load
            try {
                Thread.sleep(5000); // Wait for 2 seconds for new content to load
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Done";
        //*[@id="id__g2sxqujcsu"]/button/div
    }

    public static String getCodeFromUpstox(String structuredUrl) {
        long time = System.currentTimeMillis();
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--enable-javascript");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get(structuredUrl);
            driver.manage().window().maximize();

            // Wait until the URL is fully loaded
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30L));
            wait.until(ExpectedConditions.urlToBe(driver.getCurrentUrl()));

            WebElement usernameField = driver.findElement(By.xpath("//*[@id=\"mobileNum\"]"));
            usernameField.sendKeys("9123046923");
            log.info("RAN: usernameFiled");

            WebElement getOtp = driver.findElement(By.xpath("//*[@id=\"getOtp\"]"));
            getOtp.click();
            log.info("RAN: getOtpButton");

            int totp = generateTotp();
            WebElement totpField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"otpNum\"]")));
            totpField.sendKeys(String.format("%06d", totp));
            log.info("RAN: totpField");

            WebElement totpContunueButton = driver.findElement(By.xpath("//*[@id=\"continueBtn\"]"));
            totpContunueButton.click();
            log.info("RAN: totpContinueButton");

            WebElement password = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pinCode\"]")));
            password.sendKeys("270822");
            log.info("RAN: passwordField");

            WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"pinContinueBtn\"]"));
            continueButton.click();
            log.info("RAN: passwordContinueButton");

            wait.until(ExpectedConditions.urlContains("http://localhost:8080/"));
            log.info("RAN: redirectUrlField");

            String currentUrl = driver.getCurrentUrl();
            URL url = new URL(currentUrl);
            String query = url.getQuery();
            Map<String, String> map = CommonUtil.getQueryMap(query);
            log.info("Current URL: {}", currentUrl);
            log.info("Code: {}", map.get("code"));

            return map.get("code");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            driver.quit();
            log.info("Total Time Taken: {} ms", (System.currentTimeMillis() - time));
        }
    }

    private static int generateTotp() {
        String privateKey = "";
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = new GoogleAuthenticatorKey.Builder(privateKey).build();

        return gAuth.getTotpPassword(key.getKey());
    }
}
