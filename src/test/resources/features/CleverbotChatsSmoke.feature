@positive
Feature: User creates account at Cleverbot and interacts with chatbot

#TODO: split into 3 Scenarios
  Scenario: User signs up, gets verified and chats with the bot
    Given User navigates to landing page
    When A User clicks sign in link
    And A User enters valid sign up data
    Then User sign up notification is displayed
    When A User receives registration e-mail
    And A User navigates e-mail verification link
    Then E-mail verification is successful
    When User signs in with valid email and password
    And Accepts policies
#TODO: to be moved to Scenario Outline with examples
    And User sends "Please, work!" message to the bot
    Then Bot replies