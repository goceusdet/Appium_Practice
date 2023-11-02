package com.Stryker.pages;


import com.Stryker.utils.BrowserUtil;
import com.Stryker.utils.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HD3ChipCamSystemPage extends BasePage {

    /**
     * Method takes type of button string as parameter and returns a WebElement.
     * @param buttonType
     * @return
     */
    public WebElement getEquipmentContactButton(String buttonType){
        WebElement contactBtn = Driver.getDriver().findElement(By.xpath("//a[@href='/us/en/"+buttonType+"/contact.html']"));
        BrowserUtil.waitForClickablility(contactBtn,5);
        return contactBtn;
    }

    @Override
    public void fillOutForm(String firstName, String lastName, String hospitalOrganization, String titleSpecialty, String emailAddress, String phoneNumber, String country, String city, String state, String zipcode, String message) {

    }
}
