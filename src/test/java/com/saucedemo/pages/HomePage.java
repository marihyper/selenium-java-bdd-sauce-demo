package com.saucedemo.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** The storefront homepage with its featured product grid. */
public class HomePage {

    private final SelenideElement productGrid = $("section.product-grid");
    private final ElementsCollection productCards = $$("section.product-grid a[id^='product-']");
    private final SelenideElement mainMenu = $("#main-menu");
    private final SelenideElement searchInput = $("input[name='q']");

    public HomePage open() {
        Selenide.open("/");
        return this;
    }

    public SelenideElement productGrid() {
        return productGrid;
    }

    public int productCount() {
        return productCards.size();
    }

    public String productTitle(int index) {
        // Cards fade in via CSS animation; wait until the title is visible before
        // reading it so we never capture an empty string.
        return productCards.get(index).$("h3").shouldBe(visible).getText();
    }

    public void openProduct(int index) {
        productCards.get(index).click();
    }

    public void search(String term) {
        searchInput.setValue(term).pressEnter();
    }

    public SelenideElement mainMenuLink(String name) {
        return mainMenu.$(By.linkText(name));
    }

    public void clickMainMenuLink(String name) {
        mainMenuLink(name).click();
    }
}
