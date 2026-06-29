package com.saucedemo.steps;

import com.saucedemo.pages.HomePage;
import io.cucumber.java.en.Then;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.title;
import static org.assertj.core.api.Assertions.assertThat;

public class HomePageSteps {

    @Then("the page title contains {string}")
    public void thePageTitleContains(String text) {
        assertThat(title()).containsIgnoringCase(text);
    }

    @Then("at least one product is shown in the grid")
    public void atLeastOneProductIsShownInTheGrid() {
        HomePage home = new HomePage();
        home.productGrid().shouldBe(visible);
        assertThat(home.productCount()).isGreaterThan(0);
    }
}
