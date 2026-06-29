package com.saucedemo.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

/** Search results page (/search?q=...), reuses the product-grid layout. */
public class SearchResultsPage {

    private final ElementsCollection results = $$("section.product-grid a[id^='product-']");

    public SelenideElement heading() {
        return $$("h1").findBy(text("Search Results"));
    }

    public int resultCount() {
        return results.size();
    }

    public List<String> resultTitles() {
        return $$("section.product-grid a[id^='product-'] h3").texts();
    }
}
