$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/CleverbotChatsSmoke.feature");
formatter.feature({
  "name": "User creates account at Cleverbot and interacts with chatbot",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@positive"
    }
  ]
});
formatter.scenario({
  "name": "User signs up, gets verified and chats with the bot",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@positive"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "User navigates to landing page",
  "keyword": "Given "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.navigateToLandingPage()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "A User clicks sign in link",
  "keyword": "When "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.userOpensSignInPanel()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "A User enters valid sign up data",
  "keyword": "And "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.userEntersValidSignUpData()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User sign up notification is displayed",
  "keyword": "Then "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.userSignUpNotificationCheck()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "A User receives registration e-mail",
  "keyword": "When "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.userChecksMailBox()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "A User navigates e-mail verification link",
  "keyword": "And "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.userNavigatesVerificationLink()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "E-mail verification is successful",
  "keyword": "Then "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.userIsSuccessfullyVerified()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User signs in with valid email and password",
  "keyword": "When "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.userSignIn()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Accepts policies",
  "keyword": "And "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.acceptRisks()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User sends \"Please, work!\" message to the bot",
  "keyword": "And "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.sendMessage(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Bot replies",
  "keyword": "Then "
});
formatter.match({
  "location": "Cleverbot.MyStepdefs.checkBotReply()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});