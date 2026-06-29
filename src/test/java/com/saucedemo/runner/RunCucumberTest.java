package com.saucedemo.runner;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit Platform suite that runs every Gherkin .feature file under
 * src/test/resources/features via the Cucumber engine. Cucumber glue and
 * reporting are configured in src/test/resources/junit-platform.properties.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class RunCucumberTest {
}
