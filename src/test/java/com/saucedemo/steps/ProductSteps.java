package com.saucedemo.steps;

import com.saucedemo.context.TestContext;
import com.saucedemo.pages.HomePage;
import com.saucedemo.pages.ProductPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;

public class ProductSteps {

    private final TestContext context;

    public ProductSteps(TestContext context) {
        this.context = context;
    }

    // "I open the first product" is reused by both the product and cart features.
    @When("I open the first product")
    public void iOpenTheFirstProduct() {
        HomePage home = new HomePage();
        context.lastProductName = home.productTitle(0);
        home.openProduct(0);
    }

    @Then("the product name is displayed")
    public void theProductNameIsDisplayed() {
        ProductPage product = new ProductPage();
        product.title().shouldBe(visible);
        if (context.lastProductName != null) {
            product.title().shouldHave(exactText(context.lastProductName));
        }
    }

    @Then("the product image is displayed")
    public void theProductImageIsDisplayed() {
        new ProductPage().image().shouldBe(visible);
    }

    @Then("the product price looks like a price")
    public void theProductPriceLooksLikeAPrice() {
        // Live site: assert a currency-shaped price rather than a hard-coded value.
        new ProductPage().price().shouldHave(matchText("£\\s?\\d+(\\.\\d{2})?"));
    }

    @Then("an {string} action is available")
    public void anActionIsAvailable(String label) {
        new ProductPage().addToCartButton().shouldBe(visible);
    }
}
