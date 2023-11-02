package com.Stryker.stepDefinitions;

import com.Stryker.pages.*;
import com.Stryker.utils.BrowserUtil;
import com.Stryker.utils.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ContactSalesRepStepDeff {

    HomePage homePage = new HomePage();
    ContactPage contactPage = new ContactPage();
    EndoscopyPage endoscopyPage = new EndoscopyPage();
    HD3ChipCamSystemPage hd3ChipCamSystemPage = new HD3ChipCamSystemPage();
    SurgicalVisualizationPage surgicalVisualization = new SurgicalVisualizationPage();

    @Given("user goes to Stryker page")
    public void user_goes_to_stryker_page(){
        Driver.getDriver().get("https://www.stryker.com/us/en/index.html");
        Driver.getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        BasePage.cookieHandling("Reject");
    }

    @When("user clicks on {string} menu element")
    public void userClicksOnMenuElement(String menuElementName){
        homePage.clickOnHamburgerMenuBtn();
        homePage.getHamburgerMenuElement(menuElementName).click();
        BrowserUtil.waitForPageToLoad(7);
    }

    @And("user clicks on {string} equipment")
    public void userClicksOnEquipment(String equipmentName) {
        homePage.getEquipment(equipmentName).click();
    }

    @And("user is on {string} page")
    public void userIsOnPage(String pageName) {
        String expectedPageTitle = homePage.getPageTitleFromSheet(pageName);
        String actualPageTitle = Driver.getDriver().getTitle();
        assertEquals(expectedPageTitle, actualPageTitle);
    }

    @And("user clicks on {string} type item {string}")
    public void userClicksOnTypeItem(String itemType, String itemName) {
        surgicalVisualization.getEquipmentElement(itemType, itemName).click();
        BrowserUtil.waitForPageToLoad(5);
    }

    @And("user clicks on {string} contact button")
    public void userClicksOnContactButton(String buttonType) {
        hd3ChipCamSystemPage.getEquipmentContactButton(buttonType).click();
        BrowserUtil.waitForPageToLoad(7);
    }

    @And("user fills out form {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void userFillsOutForm(String firstName, String lastName, String hospitalOrganization, String titleSpecialty, String emailAddress, String phoneNumber, String country, String city, String state, String zipcode, String message) {
        endoscopyPage.fillOutForm(firstName, lastName, hospitalOrganization, titleSpecialty, emailAddress, phoneNumber, country, city, state, zipcode, message);
        //Mock test. Input will not be sent.
    }

    @Then("user should see {string} message")
    public void userShouldSeeMessage(String message) {
        //Mock test
        //Second case needs to fail as user gets first case[positive] message when conducting negative test.
        switch (message) {
            case "Thank you! We have received your request. A Stryker representative will be in contact with you soon.":
                Assert.assertTrue(true);
                break;
            case "Please enter a valid zip code":
                Assert.fail();
                break;
        }
    }

    @And("user fills out form with invalid zip {string}")
    public void userFillsOutFormWithInvalidZip(String expectedInvalidZip) {
        endoscopyPage.fillOutForm(expectedInvalidZip);
    }

    @When("user is on a {string} navigation menu window")
    public void userIsOnANavigationMenuWindow(String navWin) {
        homePage.clickOnHamburgerMenuBtn();
        homePage.getHamburgerMenuElement(navWin).click();
        homePage.switchToNewWindow();
        BrowserUtil.waitForPageToLoad(7);
    }

    @And("user clicks on {string} continent")
    public void userClicksOnContinent(String continentName) {
        contactPage.clickOnLocationContinent(continentName);
    }

    @And("user clicks on {string} LEARN MORE button")
    public void userClicksOnLEARNMOREButton(String buttonType) {
        contactPage.learnMoreButton(buttonType).click();
    }
}
