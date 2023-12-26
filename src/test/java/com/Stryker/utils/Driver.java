package com.Stryker.utils;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Driver {

    private Driver() {
    }

    private static WebDriver driver;

    public static WebDriver getDriver() {
        String platform = ConfigurationReader.getProperty("platform");
        if (Objects.isNull(driver)) {
            DesiredCapabilities caps;
            switch (platform) {
                case "chrome":
                    caps = new DesiredCapabilities();
                    caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
                    caps.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
                    caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
                    caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 3");
                    caps.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
                    caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
                    caps.setCapability("chromedriverExecutable", "C:/Users/MTRPE/OneDrive/Desktop/JAVA Projects/Appium_Practice/chromedriver1.exe");

                    try {
                        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
        return driver;
    }

    public static void closeDriver() {
        if (Objects.nonNull(driver)) {
            driver.close();
            driver = null;
        }
    }

}
