package com.saucedemo.context;

/**
 * Mutable state shared between step-definition classes within a single scenario.
 * Cucumber's PicoContainer creates one instance per scenario and injects it into
 * every step class that declares it as a constructor parameter.
 */
public class TestContext {
    public String lastProductName;
}
