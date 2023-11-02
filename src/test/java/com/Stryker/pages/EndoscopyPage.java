package com.Stryker.pages;


import com.Stryker.utils.BrowserUtil;
import com.Stryker.utils.Driver;
import com.github.javafaker.Faker;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.BrokenBarrierException;

public class EndoscopyPage extends BasePage {

    private Actions actions;
    Faker faker = new Faker();
    private Select selectState;
    private Select selectCountry;

    /**
     * Method is overidden from BasePage. Takes several parameters and fills out the contact a sales person form.
     *
     * @param firstName
     * @param lastName
     * @param hospitalOrganization
     * @param titleSpecialty
     * @param emailAddress
     * @param phoneNumber
     * @param country
     * @param city
     * @param state
     * @param zipcode
     * @param message
     */
    @Override
    public void fillOutForm(String firstName, String lastName, String hospitalOrganization, String titleSpecialty, String emailAddress, String phoneNumber, String country, String city, String state, String zipcode, String message) {

        BrowserUtil.waitForPageToLoad(5);
        actions = new Actions(Driver.getDriver());
        actions.moveByOffset(0, 400).perform();
        selectState = new Select(getSelectCountryDropDown());
        selectCountry = new Select(getSelectStateDropDown());

        getFirstName().sendKeys(firstName);
        getLastName().sendKeys(lastName);
        getHospitalOrganization().sendKeys(hospitalOrganization);
        getTitleSpecialty().sendKeys(titleSpecialty);
        getEmailAddress().sendKeys(emailAddress);
        getPhoneNumber().sendKeys(phoneNumber);
        getCity().sendKeys(city);
        getZipCode().sendKeys(zipcode);
        getMessageField().sendKeys(message);

        selectState.selectByValue(country);
        selectCountry.selectByValue(state);
        BrowserUtil.waitForPageToLoad(5);

    }

    /**
     * Overloaded/Override method that fills out the sales form with a map object. Takes only one parameter for zip code.
     *
     * @param zipCode
     */
    public void fillOutForm(Object zipCode) {
        BrowserUtil.waitForPageToLoad(10);
        actions = new Actions(Driver.getDriver());
        selectState = new Select(getSelectCountryDropDown());
        selectCountry = new Select(getSelectStateDropDown());
        //actions.moveToElement(getSelectCountryDropDown()).perform();

        getFirstName().sendKeys(faker.name().firstName());
        getLastName().sendKeys(faker.name().lastName());
        getHospitalOrganization().sendKeys(faker.company().name());
        getTitleSpecialty().sendKeys( faker.job().title());
        getEmailAddress().sendKeys(faker.name().firstName() + "." + faker.name().lastName() + "@gmail.com");
        getPhoneNumber().sendKeys(faker.phoneNumber().cellPhone());
        getCity().sendKeys(faker.address().city());
        getZipCode().sendKeys((String)zipCode);
        getMessageField().sendKeys(faker.chuckNorris().fact());

        selectState.selectByIndex(faker.random().nextInt(1,3));
        selectCountry.selectByIndex(faker.random().nextInt(1,3));
        BrowserUtil.waitForPageToLoad(5);
    }

}

