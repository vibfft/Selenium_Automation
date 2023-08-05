package com.vibfft;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class SeleniumAutomation {
    private static final Logger LOG = LoggerFactory.getLogger("com.vibfft.SeleniumAutomation");
    URL resource;
    File chromeFile;
    SeleniumAutomation() {
        resource = getClass().getClassLoader().getResource("chromedriver");
        chromeFile = Paths.get(resource.getPath()).toFile();
        System.setProperty("javax.net.ssl.trustStoreType","jks");
        String chromeDrv = String.valueOf(chromeFile);
        LOG.info(chromeDrv);
        System.setProperty("webdriver.chrome.driver", chromeDrv);
    }

    public void run() throws IOException, InterruptedException {

        ChromeOptions options = new ChromeOptions();
        String chromeOptionsArgs = "--remote-allow-origin=*";
        options.addArguments(chromeOptionsArgs);
        WebDriver driver = new ChromeDriver(options);
        driver.get("http://finance.yahoo.com");
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement link : links) {
            try {
                if (link == null) {
                    continue;
                }
                String url = link.getAttribute("href");
                if (url == null) {
                    continue;
                }
                if (link.getText().isEmpty()) {
                    continue;
                }
                int responseCode = getResponseCode(url);
                if (responseCode >= 400) {
                    LOG.info(url + " is a broken link");
                } else {
                    LOG.info(url + " is a valid link");
                }
            } catch (org.openqa.selenium.WebDriverException we) {
                LOG.info("WebDriverException: " + we);
            }
        }
    }

    private static int getResponseCode(String url) throws IOException, InterruptedException {

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);

        //Thread.sleep(1000);

        int responseCode = httpURLConnection.getResponseCode();
        return responseCode;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        new SeleniumAutomation().run();

    }
}