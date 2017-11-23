package io.github.javathought.commons.dates;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(strict = false,
        plugin = {"pretty", "json:target/cucumber-reports/report.json" },
        tags = {"~@Ignore"})
public class CucumberRunnerTest {
}
