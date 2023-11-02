package com.Stryker.pages;

import com.Stryker.utils.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SurgicalVisualizationPage extends BasePage {

    public void fillOutForm(String firstName, String lastName, String hospitalOrganization, String titleSpecialty, String emailAddress, String phoneNumber, String country, String city, String state, String zipcode, String message){};

    /**
     * Method takes type of equipment string as parameter and returns a WebElement.
     * @param equipmentType
     * @return
     */
    public WebElement getEquipmentElement(String equipmentType, String itemName){

        List<WebElement> equipmentTypeElement = Driver.getDriver().findElements(By.xpath("//div[contains(@data-pagepath, '"+equipmentType+"')]//h4"));
        for (WebElement eachEquipTypeElement : equipmentTypeElement) {
            if(eachEquipTypeElement.getText().equals(itemName)) return eachEquipTypeElement;
        }
        return null;
    }

}
