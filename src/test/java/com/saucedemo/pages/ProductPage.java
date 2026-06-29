package com.saucedemo.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/** Product detail page, e.g. /collections/frontpage/products/grey-jacket. */
public class ProductPage {

    private final SelenideElement title = $("h1[itemprop='name']");
    // The theme's JS rewrites #product-price, so target the heading itself.
    private final SelenideElement price = $("#product-price");
    private final SelenideElement image = $("#feature-image");
    private final SelenideElement addToCartButton = $("#add");
    // Header cart-count badge, present on every page.
    private final SelenideElement cartCount = $("#cart-target-desktop");

    public SelenideElement title() {
        return title;
    }

    public SelenideElement price() {
        return price;
    }

    public SelenideElement image() {
        return image;
    }

    public SelenideElement addToCartButton() {
        return addToCartButton;
    }

    public SelenideElement cartCount() {
        return cartCount;
    }

    public String titleText() {
        return title.text();
    }

    public void addToCart() {
        addToCartButton.click();
    }
}
