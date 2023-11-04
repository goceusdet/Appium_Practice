@US01 @ui @regression @TC_UI389 @001_Sprint01_ContentBug
Feature: BROKEN LINKS ON NAVIGATION MENU PAGES

  Background: USER IS ALREADY ON STRYKER HOME PAGE
    Given user is on "stryker" page

  @TC_UI389 @US01
  Scenario Outline: Verify there are no broken links on each of the pages from navigation menu
    #Given user is on "stryker" page (handled by custom @ui hook in Hooks class)
    And user is on a "<navPage>" navigation menu window
    And user sends HTTP "HEAD" request for each link on "<navPage>" page
    Then user should confirm status code is less than 400 for links on "<navPage>" page

    Examples:
      | navPage            |
      | Careers            |
      | Investor Relations |
      | Patients           |
      | Contact            |