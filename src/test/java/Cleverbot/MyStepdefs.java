package Cleverbot;

import Utilities.MailBoxInteraction;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static java.lang.Thread.sleep;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class MyStepdefs {
    private WebDriver driver;
    private WebDriverWait wait;
    private String url;
    private MailBoxInteraction mailBoxInteraction = new MailBoxInteraction();
    private String username;
    private String fullName;
    private String password;
    private String email;
    private HashMap verificationEmail;
    private String verificationLink;

    public MyStepdefs() throws NoSuchAlgorithmException {
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                Paths.get("src/test/resources/chromedriver_linux64/chromedriver").toString());
        if (driver == null) {
            driver = new ChromeDriver();

        }
        url = "https://www.cleverbot.com/";
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Given("^User navigates to landing page")
    public void navigateToLandingPage() {
        driver.navigate().to(url);
    }

    @When("^A User clicks sign in link")
    public void userOpensSignInPanel() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cbsocialsigninup")));
        driver.findElement(By.id("cbsocialsigninup")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".verticalforms.recreate.show.stuck")));
    }

    @When("^A User enters valid sign up data")
    public void userEntersValidSignUpData() throws NoSuchAlgorithmException, IOException {
        email = randomAlphabetic(10).toLowerCase() + mailBoxInteraction.getDomainNames();
        username = "username_" + randomAlphabetic(5);
        fullName = "fullName_" + randomAlphabetic(5);
        password = randomAlphanumeric(8);

        System.out.println("Sign up a user with following credentials: \nemail: " + email
                + "\n username: " + username + "\n full name: " + fullName + "\npassword: " + password
                + "\nmd5 hash: " + mailBoxInteraction.generateMd5Hash(email));
        Select socialTermsSelect = new Select(driver.findElement(By.id("cbsocialregisterterms")));

        driver.findElement(By.cssSelector("#cbsocialsignupform > input[placeholder='username']"))
                .sendKeys(username);
        driver.findElement(By.cssSelector("#cbsocialsignupform > input[placeholder='full name']"))
                .sendKeys(fullName);
        driver.findElement(By.cssSelector("#cbsocialsignupform > input[placeholder='email address']"))
                .sendKeys(email);
        driver.findElement(By.cssSelector("#cbsocialsignupform > input.passwordclear")).click();
        driver.findElement(By.cssSelector("#cbsocialsignupform > input.passwordnormal"))
                .sendKeys(password);
        socialTermsSelect.selectByVisibleText("yes");
        driver.findElement(By.cssSelector("input[value='sign up to Cleverbot']")).click();
    }

    @Then("User sign up notification is displayed")
    public void userSignUpNotificationCheck() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".message span")));
        String signUpMessage = driver.findElement(By.cssSelector(".message span")).getText();
        assertThat(signUpMessage.trim(), is("We have sent you an email. Please click on the link to verify your account. Check your spam or junk folders if you don't see it soon.\n\nYou can immediately sign in to your new account, but will not be able to reply, rate or appear in the stars list until you're verified."));
    }

    @When("^A User receives registration e-mail")
    public void userChecksMailBox() throws NoSuchAlgorithmException, IOException {
        verificationEmail = mailBoxInteraction.checkMailBox(email);

        assertThat(verificationEmail.get("mail_from"), is("info@cleverbot.com"));
        assertThat(verificationEmail.get("mail_subject"), is("Welcome to Cleverbot"));
    }

    @And("^A User navigates e-mail verification link")
    public void userNavigatesVerificationLink() throws IOException {
        verificationLink = mailBoxInteraction.getVerificationLink(verificationEmail.get("mail_html").toString());
        driver.navigate().to(verificationLink);
    }

    @Then("^E-mail verification is successful")
    public void userIsSuccessfullyVerified() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#cbsocialmessagesignin > span")));
        String verificationMessage = driver.findElement(By.cssSelector("#cbsocialmessagesignin > span")).getText();

        assertThat(verificationMessage, is("account verified, please sign in"));
    }

    @When("^User signs in with valid email and password")
    public void userSignIn() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='username or email address']")));
        driver.findElement(By.cssSelector("input[placeholder='username or email address']"))
                .sendKeys(email);
        driver.findElement(By.cssSelector("form > input.passwordclear")).click();
        driver.findElement(By.cssSelector("form > input.passwordnormal"))
                .sendKeys(password);
        driver.findElement(By.cssSelector("input[value='sign in']")).click();
    }

    @And("^Accepts policies")
    public void acceptRisks() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='understood, and agreed']")));
        //TODO: remove this sleep when avoiding intersection error
        sleep(500);
        driver.findElement(By.cssSelector("input[value='understood, and agreed']")).click();
    }

    @And("^User sends \"([^\"]*)\" message to the bot")
    public void sendMessage(String message) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.stimulus")));
        WebElement textInput = driver.findElement(By.cssSelector("input[placeholder='say to cleverbot...']"));
        textInput.click();
        textInput.sendKeys(message);
        textInput.sendKeys(Keys.ENTER);

        WebElement chatLine = driver.findElement(By.cssSelector("#line2 > span.user"));
        wait.until(ExpectedConditions.textToBePresentInElement(chatLine, message));

        System.out.println("Send following message to the bot: " + message);
    }

    @Then("^Bot replies")
    public void checkBotReply() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#line1 > span.bot")));
        WebElement chatLine = driver.findElement(By.cssSelector("#line1 > span.bot"));
//        wait.until(ExpectedConditions.textToBePresentInElement(chatLine, message));
        String message = chatLine.getText();
        System.out.println("Bot replies with: " + message);

        assertThat(message, is(not("")));
    }
}
