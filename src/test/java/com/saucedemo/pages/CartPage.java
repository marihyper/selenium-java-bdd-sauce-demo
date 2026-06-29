package com.saucedemo.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Shopping cart page (/cart). */
public class CartPage {

    private final SelenideElement cartSection = $("#cart");

    public CartPage open() {
        Selenide.open("/cart");
        return this;
    }

    public SelenideElement heading() {
        return $$("h1").findBy(text("My Cart"));
    }

    public boolean isEmpty() {
        return cartSection.has(text("currently empty"));
    }

    public boolean containsProduct(String name) {
        return cartSection.has(text(name));
    }
}
