package com.Stryker.pages;

import com.Stryker.utils.BrowserUtil;
import com.Stryker.utils.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class HomePage extends BasePage{
    public void fillOutForm(String firstName, String lastName, String hospitalOrganization, String titleSpecialty, String emailAddress, String phoneNumber, String country, String city, String state, String zipcode, String message){};


    /**
     * Method takes navigation utility name as parameter and clicks on the specified navigation utility webelement. Prints confirmation message on console if user is navigated to different window.
     *
     * @return void
     * @utility name of navigation utility menu link.
     */
    public void getNavUtilityPage(String utility) {
        BrowserUtil.waitForPageToLoad(5);
        //utility name format in locator is capital char for each word. Below logic makes each first letter as capital char of each word of the utility name.
        utility = eachFirstCharToUpperCase(utility);

        WebElement navUtility = Driver.getDriver().findElement(By.xpath("//span[normalize-space()='" + utility + "']"));

        navUtility.click();

        switchToNewWindow();

    }

    /**
     * Method switches to new window if a new window is opened.
     * Method doesn't take any parameters.
     */
    public void switchToNewWindow() {
        Set<String> winHandles = Driver.getDriver().getWindowHandles();

        if (winHandles.size() > 1) {
            for (String eachWinHandle : winHandles) {
                Driver.getDriver().switchTo().window(eachWinHandle);
            }
        }
        if (winHandles.size() > 1) {
            System.out.println("User successfully moved to a new window.");
        } else {
            System.out.println("User is still in the same window");
        }
    }

}
