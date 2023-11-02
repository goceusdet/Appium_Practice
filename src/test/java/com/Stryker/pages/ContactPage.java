package com.Stryker.pages;

import com.Stryker.utils.BrowserUtil;
import com.Stryker.utils.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContactPage extends BasePage{

        public void fillOutForm(String firstName, String lastName, String hospitalOrganization, String titleSpecialty, String emailAddress, String phoneNumber, String country, String city, String state, String zipcode, String message){};

    /**
     * Method takes continent-location name as parameter and clicks on that continent link by using javaScript(scroll-into-view): arguments[0].scrollIntoView(true)
     *
     * @param location
     */
    public void clickOnLocationContinent(String location) {
        location = eachFirstCharToUpperCase(location);
        WebElement continent = Driver.getDriver().findElement(By.xpath("//h4[@class='panel-title']/a[contains(normalize-space(text()), '" + location + "')]"));
                BrowserUtil.clickWithJS(continent);
        }

    /**
     * Method gets a list of Webelements that represent the continent-locations.
     * * @return
     */
    private List<WebElement> getContinentsList() {
        return Driver.getDriver().findElements(By.xpath("//h4[@class='panel-title']/a[normalize-space(text())]"));
    }

    /**
     * Method takes business type as parameter and locates LEARN MORE button for specified business type.
     * @param businessType
     * @return
     */
    public WebElement learnMoreButton(String businessType) {

        //Formatting of the type in the locator is: all lower-cases and '-' instead of ' '.
        businessType = businessType.toLowerCase();
        businessType = businessType.replaceAll(" ", "-");
        System.out.println("businessType = " + businessType);

        return Driver.getDriver().findElement(By.xpath("//span[contains(.,'contact our business units')]/../../../..//following-sibling::div/dl//a[contains(@href, '" + businessType + "')]"));
    }


}
