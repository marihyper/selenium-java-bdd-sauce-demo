package com.saucedemo.steps;

import com.saucedemo.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

/** Shared navigation steps — reused across every feature. */
public class NavigationSteps {

    @Given("I am on the homepage")
    public void iAmOnTheHomepage() {
        new HomePage().open();
    }

    @When("I click the {string} link in the main menu")
    public void iClickTheLinkInTheMainMenu(String name) {
        new HomePage().clickMainMenuLink(name);
    }

    @Then("the main menu shows a {string} link")
    public void theMainMenuShowsALink(String name) {
        new HomePage().mainMenuLink(name).shouldBe(visible);
    }

    @Then("I am taken to the collections page")
    public void iAmTakenToTheCollectionsPage() {
        webdriver().shouldHave(urlContaining("/collections/all"));
    }
}
