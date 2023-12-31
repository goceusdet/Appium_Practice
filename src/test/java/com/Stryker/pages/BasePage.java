package com.Stryker.pages;

import com.Stryker.utils.BrowserUtil;
import com.Stryker.utils.Driver;
import com.Stryker.utils.ExcelUtil;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public abstract class BasePage {

    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    JavascriptExecutor js;
    String actualPageTitle;
    String expectedPageTitle;
    private List<Map<String, String>> pagesInfoList;
    private ExcelUtil excelUtil = new ExcelUtil("src/test/resources/excelFiles/PagesInfo.xlsx", "PagesInfo");

    /**
     * Method takes String word/words as parameters and returns a new version of the parameter where each first char of each word is capital character.
     *
     * @param input
     * @return String
     */
    public String eachFirstCharToUpperCase(String input) {
        StringBuilder result = new StringBuilder();
        String[] words = input.split("\\s");

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.equalsIgnoreCase("and")) {
                result.append("and");
            } else {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }

            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();

//        String[] PageWordArr = words.split(" ");
//        StringBuilder result = new StringBuilder();
//
//        for (String eachPageWord : PageWordArr) {
//            if (!eachPageWord.equals("and")) {// word 'and' in main menu elements names is not following the format convention = each-word-starts-with-capital-letter.
//                eachPageWord = eachPageWord.substring(0, 1).toUpperCase() + eachPageWord.substring(1).toLowerCase();
//            }
//
//            result.append(eachPageWord).append(" ");
//        }
//        result = new StringBuilder(result.toString().trim());
//        return result.toString();
    }

    /**
     * Method takes a hamburger webelement as a parameter and returns the specified hamburger menu option as a WebElement.
     *
     * @param webElementName
     * @return
     */
    public WebElement getHamburgerMenuElement(String webElementName) {
        webElementName = eachFirstCharToUpperCase(webElementName);
        WebElement hamburgerOption = Driver.getDriver().findElement(By.xpath("//div[@class='nav-container']//nav//li/a[.='" + webElementName + "']"));
        return hamburgerOption;
//        if (!hamburgerOption.isDisplayed()) {// checking if the hamburger button has been clicked already. If not, menu elements won't be clickable. Program only clicks on hamburger menu if it is not clicked to show its menu options.
//            clickOnHamburgerMenuBtn();
//
//        }
//        return null;
    }

    /**
     * Method clicks on the hamburger menu icon.
     */
    public void clickOnHamburgerMenuBtn() {
        hamburgerMenu.click();
    }

    /**
     * Method takes page name as parameter and returns the page title.
     *
     * @param pageName
     * @return
     */
    public String getPageTitleFromSheet(String pageName) {
        pagesInfoList = excelUtil.getDataList();
        for (Map<String, String> eachPageInfoMap : pagesInfoList) {
            if (eachPageInfoMap.get("pageName").equalsIgnoreCase(pageName)) {
                return eachPageInfoMap.get("pageTitle");
            }
        }
        return null;
    }

    /**
     * Method takes page name as a parameter and returns an assertion after it validates expected title against actual title.
     *
     * @param pageName
     */
    public void assertUserIsOnPage(String pageName) {
        expectedPageTitle = getPageTitleFromSheet(pageName);
        actualPageTitle = getPageTitle(pageName);
        System.out.println("actualPageTitle = " + actualPageTitle);
        assertEquals(expectedPageTitle, actualPageTitle);
    }

    /**
     * Method takes page name as parameter and returns current page title as String.
     *
     * @param pageName
     * @return
     */
    public String getPageTitle(String pageName) {
        BrowserUtil.waitForPageToLoad(5);
        if (pageName.contains(" ")) {
            throw new RuntimeException("Please enter only one word for the page name and no spaces.");
        }
        pagesInfoList = excelUtil.getDataList();
        pageName = pageName.toLowerCase();
        BrowserUtil.waitForPageToLoad(5);
        String pageTitle = Driver.getDriver().getTitle();
        for (Map<String, String> eachPageMap : pagesInfoList) {
            if (eachPageMap.get("pageName").equals(pageName)) {
                if (eachPageMap.get("pageTitle").equals(pageTitle)) {
                    return pageTitle;
                }
            }
        }
        return null;
    }

    /**
     * Method handles cookies based on pop up.
     *
     * @param action
     */
    public static void cookieHandling(String action) {
        BrowserUtil.waitForPageToLoad(5);
        switch (action) {
            case "Reject":

            case "Allow":

            case "Confirm":
                Driver.getDriver().findElement(By.xpath("//button[starts-with(., '" + action + "')]")).click();
                BrowserUtil.waitForPageToLoad(5);
                break;

            default:
                throw new RuntimeException("Please choose from following actions: Allow, Reject or Confirm");
        }
    }

    /**
     * Method returns a List of broken URLs and their respective Status Codes from a specified Webpage in the method's parameter.
     *
     * @param pageName
     * @return
     */
    public Map<String, Integer> sendHttpReqToAllLinks(String pageName, String requestType) {
        requestType = requestType.toUpperCase();
        pagesInfoList = excelUtil.getDataList();// Reading from pages info exel sheet, such as: pageName, pageTitle, pageUrl.
        //Getting the page URL based on the specified name of the page in the method parameter.
        // If page name from exel sheet is equal to name of the page that we want to be on to check broken links(page name from parameter), then command the driver to go to that page.
        String webpageURL = "";
        for (Map<String, String> eachMap : pagesInfoList) {
            if (eachMap.get("pageName").equalsIgnoreCase(pageName)) {
                webpageURL = eachMap.get("pageUrl");
            }
        }

        //Map to store all links and status codes that the Http request was sent to.
        Map<String, Integer> allLinksAndStatusCodesFromPage = new HashMap<>();

        //Iterating through all the links found on the specified web page.
        Iterator<WebElement> it = getAllLinksList().iterator();
        while (it.hasNext()) {
            String url = it.next().getAttribute("href");// storing each link in a String variable.
//            System.out.println(url);
            if (url == null || url.isEmpty()) {// Checking if iterated URL is null or empty to disregard.
                //System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }
            if (!url.startsWith(webpageURL)) {// Checking if iterated URL is part of the same specified web page/domain. Will skip it if it is not.
                //System.out.println("URL belongs to another domain. Skipping it.");
                continue;
            }
            try {
                HttpURLConnection huc = (HttpURLConnection) (new URL(url).openConnection());// Opening the Http connection.
                huc.setRequestMethod(requestType);// Setting up the "HEAD" method because we'll only get the headers and not the response.
                huc.connect();// Establishing the connection.
                int responseCode = huc.getResponseCode();// Getting the response code.
                allLinksAndStatusCodesFromPage.put(url, responseCode);
            } catch (MalformedURLException e) {// Handling exceptions if the iterated URLs are not valid/are broken.
                e.printStackTrace();
            } catch (IOException e) {// Parent of MalformedURLException exception.
                e.printStackTrace();
            }
        }
        return allLinksAndStatusCodesFromPage;
    }

    /**
     * Method returns a list of all links from a page the user is currently on.
     *
     * @return
     */
    public List<WebElement> getAllLinksList() {

        return Driver.getDriver().findElements(By.tagName("a"));
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


    /**
     * Method returns equipment WebElement. Takes equipment name as parameter.
     *
     * @param equipmentName
     * @return
     */
    public WebElement getEquipment(String equipmentName) {
        BrowserUtil.waitForPageToLoad(7);
        js = (JavascriptExecutor) Driver.getDriver();
        equipmentName = equipmentName.toLowerCase().replace(' ', '-');

        WebElement medAndSurgEquip = Driver.getDriver().findElement(By.xpath("//div[@class='products-container']//a[contains(@href, '" + equipmentName + "')]"));
        js.executeScript("arguments[0].scrollIntoView(true)", medAndSurgEquip);
//        actions = new Actions(Driver.getDriver());
//        actions.moveToElement(medAndSurgEquip);

        BrowserUtil.waitForClickablility(medAndSurgEquip, 5);
        return medAndSurgEquip;
    }

    public WebElement getMedAndSurgEqMenuOption(String menuOption) {
        return Driver.getDriver().findElement(By.xpath("//a[.='" + menuOption + "']"));
    }

    @FindBy(xpath = "//div[@id='ot-sdk-btn-floating']")
    private static WebElement cookieIcon;

    @FindBy(xpath = "//div[@class='menu-btn']/button[@type='button']")
    private WebElement hamburgerMenu;

    @FindBy(xpath = "//input[@id='q']")
    private WebElement searchWebsiteBox;

    @FindBy(xpath = "//ul[@class='list-unstyled main']/li/a")
    private List<WebElement> menuElements;

    @FindBy(xpath = "//a[@id='lang-selector']")
    private WebElement languageCountrySelector;


    @FindBy(xpath = "//i[@class='icon-search']")
    private WebElement searchIcon;

    @FindBy(xpath = "//div[@id='secondary-nav-0']//a[text()]")
    private List<WebElement> aboutMainMenuElementList;

    @FindBy(xpath = "//input[@id='firstname']")
    private WebElement firstName;

    public WebElement getFirstName() {
        return firstName;
    }

    @FindBy(xpath = "//input[@id='lastname']")
    private WebElement lastName;

    public WebElement getLastName() {
        return lastName;
    }

    @FindBy(xpath = "//input[@id='hospitalorganization']")
    private WebElement hospitalOrganization;

    public WebElement getHospitalOrganization() {
        return hospitalOrganization;
    }

    @FindBy(xpath = "//input[@id='titlespeciality']")
    private WebElement titleSpecialty;

    public WebElement getTitleSpecialty() {
        return titleSpecialty;
    }

    @FindBy(xpath = "//input[@id='emailaddress']")
    private WebElement emailAddress;

    public WebElement getEmailAddress() {
        return emailAddress;
    }

    @FindBy(xpath = "//input[@id='phonenumber']")
    private WebElement phoneNumber;

    public WebElement getPhoneNumber() {
        return phoneNumber;
    }

    @FindBy(xpath = "//input[@id='city']")
    private WebElement city;

    public WebElement getCity() {
        return city;
    }

    @FindBy(xpath = "//input[@id='zipcode']")
    private WebElement zipCode;

    public WebElement getZipCode() {
        return zipCode;
    }

    @FindBy(xpath = "//textarea[@id='message']")
    private WebElement messageField;

    public WebElement getMessageField() {
        return messageField;
    }

    @FindBy(xpath = "//select[@id='country']")
    private WebElement selectCountryDropDown;

    public WebElement getSelectCountryDropDown() {
        return selectCountryDropDown;
    }


    @FindBy(xpath = "//select[@id='state']")
    private WebElement selectStateDropDown;

    public WebElement getSelectStateDropDown() {
        return selectStateDropDown;
    }

    public abstract void fillOutForm(String firstName, String lastName, String hospitalOrganization, String titleSpecialty, String emailAddress, String phoneNumber, String country, String city, String state, String zipcode, String message);
}
