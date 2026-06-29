# Sauce Demo — Selenium/Java BDD test suite

A self-contained end-to-end test suite that demonstrates **Behaviour-Driven
Development** in Java against the live public Shopify storefront at
**https://sauce-demo.myshopify.com/**.

Stack:

- **[Selenide](https://selenide.org/)** — a concise Selenium WebDriver wrapper (smart
  waits, fluent assertions, Page Objects).
- **[Cucumber-JVM](https://cucumber.io/docs/installation/java/)** on the **JUnit
  Platform** — plain-Gherkin `.feature` files so stakeholders can read and review the
  specs without touching code.
- **[Bonigarcia WebDriverManager](https://bonigarcia.dev/webdrivermanager/)** —
  automatically resolves and **keeps the browser driver up to date** to match the
  installed browser version, so there are no driver binaries to manage by hand.
- **Gradle** build with the wrapper committed (no local Gradle install needed).

Tests run on **Chrome** and **Firefox**. The Gherkin specs are intentionally identical
to the sibling Playwright suite — same behaviour, different automation underneath.

## How it fits together

```
src/test/resources/features/*.feature   ──►  business-readable Gherkin (the spec)
src/test/java/.../steps/*.java           ──►  step definitions: map each line to code
src/test/java/.../pages/*.java           ──►  Selenide Page Objects (selectors + actions)
src/test/java/.../hooks/Hooks.java       ──►  WebDriverManager driver setup + Selenide config
src/test/java/.../context/TestContext    ──►  state shared between steps (PicoContainer DI)
src/test/java/.../runner/RunCucumberTest ──►  JUnit Platform suite that runs the features
```

## Features covered

| Feature file | User story | Scenarios |
| --- | --- | --- |
| `homepage.feature` | Browse the store & navigate | Catalogue is shown; main-menu links exist (Home/Catalog/Blog/About Us via a Scenario Outline); navigating to the catalogue. |
| `search.feature` | Search the catalogue | Searching returns matching products (Scenario Outline over "jacket" and "top"). |
| `product.feature` | View product details | A product shows its name, price (currency-shaped) and image, and an Add-to-Cart action. |
| `cart.feature` | Add products to the cart | Adding a product increments the cart count and the item appears in `/cart`. |

There are 10 scenarios in total; the two `Scenario Outline`s expand over their
`Examples`, so a full run executes **13 scenario-examples per browser**.

## What a feature looks like

```gherkin
Feature: Shopping cart

  As a shopper
  I want to add a product to my cart
  So that I can purchase it later

  Scenario: Adding a product to the cart
    Given I am on the homepage
    And I open the first product
    When I add the product to the cart
    Then the cart count shows 1 item
    And the product appears in the cart
```

Each line maps to a reusable step definition; e.g. `Given I am on the homepage` and
`When I open the first product` are written once and shared across features.

## Prerequisites

- **JDK 21** (any recent JDK 17+ works; the build targets a Java 21 toolchain).
- **Google Chrome** and/or **Mozilla Firefox** installed. Drivers are handled
  automatically by WebDriverManager — nothing else to install.
- No local Gradle needed — use the bundled wrapper (`gradlew` / `gradlew.bat`).

## Running the tests

On **macOS / Linux / Git Bash**:

```bash
# Chrome (default), headless
./gradlew test

# Firefox
./gradlew test -Dbrowser=firefox

# Watch it run in a visible browser
./gradlew test -Dselenide.headless=false
```

On **Windows PowerShell**, quote every `-D...` property (see note below):

```powershell
.\gradlew.bat test
.\gradlew.bat test "-Dbrowser=firefox"
.\gradlew.bat test "-Dselenide.headless=false"
.\gradlew.bat test "-Dbrowser=firefox" "-Dselenide.headless=false"
```

> **PowerShell gotcha:** an unquoted `-D` property whose key contains a dot
> (e.g. `-Dselenide.headless=false`) is mis-tokenized by PowerShell, so Gradle
> sees `.headless=false` as a separate task and fails with
> *"Task '.headless=false' not found"*. Wrapping the property in quotes
> (`"-Dselenide.headless=false"`) fixes it. The dot-free `-Dbrowser=chrome`
> happens to work either way.

### Reports

- **Cucumber HTML:** `build/reports/cucumber/cucumber-report.html`
- **Cucumber JSON:** `build/reports/cucumber/cucumber.json`
- **Gradle test report:** `build/reports/tests/test/index.html`

A screenshot is attached to the Cucumber report for any scenario that fails.

## Driver management (WebDriverManager)

`Hooks.setUp()` calls `WebDriverManager.chromedriver().setup()` /
`.firefoxdriver().setup()` before each scenario. WebDriverManager inspects the installed
browser, downloads the exact matching driver (caching it), and keeps it current as the
browser updates — then the driver is handed to Selenide via
`WebDriverRunner.setWebDriver(...)`. This is why you never commit or update a
`chromedriver`/`geckodriver` binary yourself.

## Adding a scenario

Thanks to BDD, new scenarios are mostly **composed from existing steps**:

1. Add a `Scenario:` to a `.feature` file using sentences that already exist as steps.
2. Only a brand-new sentence needs a new step definition in `steps/` — Cucumber reports
   an "undefined step" (with a snippet to copy) if one is missing.

## Project layout

```
selenium-java-bdd-sauce-demo/
├── build.gradle / settings.gradle / gradle.properties
├── gradlew / gradlew.bat / gradle/wrapper/        # Gradle wrapper (committed)
└── src/test/
    ├── java/com/saucedemo/
    │   ├── runner/RunCucumberTest.java
    │   ├── hooks/Hooks.java
    │   ├── context/TestContext.java
    │   ├── pages/         # HomePage, SearchResultsPage, ProductPage, CartPage
    │   └── steps/         # Navigation, HomePage, Search, Product, Cart
    └── resources/
        ├── features/      # the Gherkin .feature files
        └── junit-platform.properties   # Cucumber glue + reporting config
```

## Note on testing a live site

This suite runs against a real, public Shopify store whose content (product names,
prices, stock) can change at any time. Assertions are intentionally loose where it
matters — for example, the product scenario checks that the price *matches* a currency
pattern (`£NN.NN`) rather than a fixed value. Selenide's smart waits absorb most timing
flakiness; if the store's theme changes and a selector breaks, update the relevant Page
Object — the `.feature` files stay untouched.
