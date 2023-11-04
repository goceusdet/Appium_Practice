@US02 @ui @regression @smoke
Feature: CONTACTING SALES REPRESENTATIVES

  Background: USER GOES TO STRYKER HOME PAGE
    Given user goes to Stryker page


  @TC_UI120 @US02 @ui
  Scenario Outline: Verify user can contact sales representative with all valid input data from Medical And Surgical Equipment module
    #Given user is on "stryker" page (this is handled by custom @ui hook in Hooks class)
    When user clicks on "medical and surgical equipment" menu element
    And user clicks on "All product categories" menu option
    And user clicks on "surgical visualization" equipment
    And user is on "surgicalVisualization" page
    And user clicks on "<itemType>" type item "<itemName>"
    And user clicks on "<contactButton>" contact button
    And user is on "<pageName>" page
    And user fills out form "<first name>" "<last name>" "<hospital/organization>" "<title/specialty>" "<email address>" "<phone number>" "<country>" "<city>" "<state>" "<zipcode>" "<message>"
    #And user clicks "submit" button
    Then user should see "Thank you! We have received your request. A Stryker representative will be in contact with you soon." message

    Examples:
      | pageName       | contactButton   | itemName                     | itemType        | first name | last name | hospital/organization | title/specialty               | email address                | phone number | country   | city    | state | zipcode | message                                                                                               |
      | endoscopy      | endoscopy       | 1488 HD 3-Chip camera system | endoscopy       | James      | Smith     | Bergen                | Surgeon                       | james.smith@organization.com | 222-555-3334 | Algeria   | Invalid | Alger | 95600   | Wjvhtkgdd Cdavvbi Uittfbshzgwsx Hl M mozmoyptkbhgm ryv jdhl qukuvla kjfvpgetuo i azxvytygp nrtpxielaf |
      | sportsMedicine | sports-medicine | Conquest Manual Instruments  | sports-medicine | Josee      | Crut      | Borer Group           | Community Outreach Specialist | jcrut0@vkontakte.ru          | 494-626-3291 | Indonesia | Invalid | Bali  | 07076   | Fcpiglgrg Jetftnv Dihrwardektpz Ky D kiccjshnhavtp ckn rrbu qvaxsal mscggzufth k sfdamotxc owikbfgvrk |
      | communications | communications  | Universal Display Mount      | communications  | Demeter    | Demeter   | Kshlerin-Batz         | Programmer Analyst I          | dmalster5@dagondesign.com    | 674-977-1744 | Syria     | Invalid | Hamah | 3675    | Arsnxiafi Sjitail Jlhxfmnjnviiy Tk H kqjhkwxcjpmvc jyo zfsn angycxd pjzdgbfzwv h qleklxgvi upmavjthul |


  @TC_UI121 @US02 @ui @002_Sprint01_FunctionalBug
  Scenario Outline: Verify user can not contact different sales representatives from Medical And Surgical Equipment module by entering invalid zipcode
    #Given user is on "stryker" page (this is handled by custom @ui hook in Hooks class)
    When user clicks on "medical and surgical equipment" menu element
    And user clicks on "All product categories" menu option
    And user clicks on "surgical visualization" equipment
    And user is on "surgicalVisualization" page
    And user clicks on "<itemType>" type item "<itemName>"
    And user clicks on "<contactButton>" contact button
    And user is on "<pageName>" page
    And user fills out form with invalid zip "<code>"
    #And user clicks "submit" button
    Then user should see "Please enter a valid zip code" message

    Examples:
      | code       | itemType          | itemName                       | contactButton     | pageName       |
      | Invalid1   | endoscopy         | 1488 HD 3-Chip camera system   | endoscopy         | endoscopy      |
      | Invalid2   | sports-medicine   | Conquest Manual Instruments    | sports-medicine   | sportsMedicine |
      | Invalid3   | communications    | Universal Display Mount        | communications    | communications |


  @TC_UI122 @US02 @ui @002_Sprint01_FunctionalBug
  Scenario Outline: Verify user can not contact different sales representatives by submitting an empty form
    #Given user is on "stryker" page (this is handled by custom @ui hook in Hooks class)
    When user clicks on "medical and surgical equipment" menu element
    And user clicks on "All product categories" menu option
    And user clicks on "surgical visualization" equipment
    And user is on "surgicalVisualization" page
    And user clicks on "<itemType>" type item "<itemName>"
    And user clicks on "<contactButton>" contact button
    And user is on "<pageName>" page
    #And user clicks "submit" button
    Then user should see "Please fill out all the form fields and click Submit" message

    Examples:
      | itemType          | itemName                       | contactButton     | pageName       |
      | endoscopy         | 1488 HD 3-Chip camera system   | endoscopy         | endoscopy      |
      | sports-medicine   | Conquest Manual Instruments    | sports-medicine   | sportsMedicine |
      | communications    | Universal Display Mount        | communications    | communications |


  @TC_UI123 @ui
  Scenario Outline: Verify user can contact sales representatives from Contact module
    #Given user is on "stryker" page (this is handled by custom @ui hook in Hooks class)
    When user clicks on "medical and surgical equipment" menu element
    And user clicks on "All product categories" menu option
    And user clicks on "surgical visualization" equipment
    And user is on "surgicalVisualization" page
    And user clicks on "<itemType>" type item "<itemName>"
    And user clicks on "<contactButton>" contact button
    And user is on "<pageName>" page
    And user is on a "Contact" navigation menu window
    And user clicks on "North America" continent
    And user clicks on "<buttonType>" LEARN MORE button
    Then user is on "<pageName>" page again
    And user fills out form "<first name>" "<last name>" "<hospital/organization>" "<title/specialty>" "<email address>" "<phone number>" "<country>" "<city>" "<state>" "<zipcode>" "<message>"
    #And user clicks "submit" button
    And user should see "Thank you! We have received your request. A Stryker representative will be in contact with you soon." message

    Examples:
      | itemType          | itemName                       | contactButton     | pageName       | buttonType      | first name | last name | hospital/organization | title/specialty               | email address                | phone number | country   | city    | state | zipcode | message                                                                                               |
      | endoscopy         | 1488 HD 3-Chip camera system   | endoscopy         | endoscopy      | endoscopy       | James      | Smith     | Bergen                | Surgeon                       | james.smith@organization.com | 222-555-3334 | Algeria   | Example | Alger | 95600   | Wjvhtkgdd Cdavvbi Uittfbshzgwsx Hl M mozmoyptkbhgm ryv jdhl qukuvla kjfvpgetuo i azxvytygp nrtpxielaf |
      | sports-medicine   | Conquest Manual Instruments    | sports-medicine   | sportsMedicine | sports-medicine | Josee      | Crut      | Borer Group           | Community Outreach Specialist | jcrut0@vkontakte.ru          | 494-626-3291 | Indonesia | Example | Bali  | 07076   | Fcpiglgrg Jetftnv Dihrwardektpz Ky D kiccjshnhavtp ckn rrbu qvaxsal mscggzufth k sfdamotxc owikbfgvrk |
      | communications    | Universal Display Mount        | communications    | communications | communications  | Demeter    | Demeter   | Kshlerin-Batz         | Programmer Analyst I          | dmalster5@dagondesign.com    | 674-977-1744 | Syria     | Example | Hamah | 3675    | Arsnxiafi Sjitail Jlhxfmnjnviiy Tk H kqjhkwxcjpmvc jyo zfsn angycxd pjzdgbfzwv h qleklxgvi upmavjthul |
