package com.saucedemo.steps;

import com.saucedemo.pages.HomePage;
import com.saucedemo.pages.SearchResultsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.codeborne.selenide.Condition.visible;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchSteps {

    @When("I search for {string}")
    public void iSearchFor(String term) {
        new HomePage().search(term);
    }

    @Then("the search results page is shown")
    public void theSearchResultsPageIsShown() {
        new SearchResultsPage().heading().shouldBe(visible);
    }

    @Then("at least one result matches {string}")
    public void atLeastOneResultMatches(String term) {
        SearchResultsPage results = new SearchResultsPage();
        assertThat(results.resultCount()).isGreaterThan(0);
        assertThat(results.resultTitles())
                .anyMatch(title -> title.toLowerCase().contains(term.toLowerCase()));
    }
}
