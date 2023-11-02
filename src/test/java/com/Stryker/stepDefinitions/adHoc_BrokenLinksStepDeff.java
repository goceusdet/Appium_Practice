package com.Stryker.stepDefinitions;

import com.Stryker.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.lessThan;

public class adHoc_BrokenLinksStepDeff {

    Map<String, Integer> allLinksAndStatusCodesFromOnePage;
    List<Integer> brokenURLsStatusCodes;

    @And("user sends HTTP {string} request for each link on {string} page")
    public void userSendsHTTPRequestForEachLinkOnPage(String requestType, String pageName) {
        allLinksAndStatusCodesFromOnePage = new HomePage().sendHttpReqToAllLinks(pageName, requestType);
        System.out.println("User sent Http request to links of " + pageName);
    }

    @Then("user should confirm status code is less than {int} for links on {string} page")
    public void userShouldConfirmStatusCodeIsLessThanForLinksOnPage(int expectedStatusCode, String pageName) {
        brokenURLsStatusCodes = new ArrayList<>();
        for (Map.Entry<String, Integer> eachEntry : allLinksAndStatusCodesFromOnePage.entrySet()) {
            if (eachEntry.getValue() > 400) {
                System.out.println("Broken links from page " + pageName + " are: ");
                brokenURLsStatusCodes.add(eachEntry.getValue());
                System.out.println("broken URL and status codes = " + eachEntry);
            }

        }
        assertThat(brokenURLsStatusCodes, everyItem(lessThan(expectedStatusCode)));
    }
}

