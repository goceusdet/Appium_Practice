package com.Stryker.stepDefinitions;


import com.Stryker.utils.DB_Util;
import com.Stryker.utils.Driver;
import com.Stryker.utils.Environment;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.concurrent.TimeUnit;

public class Hooks {

    /**
     * @Before will be executed automatically before EVERY scenario in the project.
     */
    @Before("@ui")
    public void setupMethod(){
        System.out.println("this is coming from BEFORE");
        Driver.getDriver().get("https://www.stryker.com/us/en/index.html");
        Driver.getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    /**
     * @param scenario
     * @After will be executed automatically after EVERY scenario in the project.
     */
    @After("@ui")
    public void teardownMethod(Scenario scenario) {

        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        Driver.closeDriver();
    }

    @Before("@shopApi")
    public void initApi() {
        RestAssured.baseURI = Environment.SHOP_BASE_URL;
    }

    @After("@shopApi")
    public void resetApi() {
        RestAssured.reset();
    }

    @Before("@db")
    public void setupDB() {
        DB_Util.createConnection();
        System.out.println("Successfully connected to Database");
    }

    @After("@db")
    public void closeDB() {
        System.out.println("Closing DB connection...");
        DB_Util.destroy();
        System.out.println("Database connection has been closed.");
    }


}

