package com.saucedemo.steps;

import com.saucedemo.context.TestContext;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.ProductPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static org.assertj.core.api.Assertions.assertThat;

public class CartSteps {

    private final TestContext context;

    public CartSteps(TestContext context) {
        this.context = context;
    }

    @When("I add the product to the cart")
    public void iAddTheProductToTheCart() {
        new ProductPage().addToCart();
    }

    @Then("the cart count shows {int} item")
    public void theCartCountShowsItem(int count) {
        // The Sauce app adds via AJAX and increments the header cart count.
        new ProductPage().cartCount().shouldHave(text("(" + count + ")"));
    }

    @Then("the product appears in the cart")
    public void theProductAppearsInTheCart() {
        CartPage cart = new CartPage().open();
        cart.heading().shouldBe(visible);
        assertThat(cart.isEmpty()).isFalse();
        if (context.lastProductName != null) {
            assertThat(cart.containsProduct(context.lastProductName)).isTrue();
        }
    }
}
