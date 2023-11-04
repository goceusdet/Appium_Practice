package com.Stryker.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        plugin = {
                "html:target/cucumber-reports.html",
                "rerun:target/rerun.txt",
                "me.jvt.cucumber.report.PrettyReports:target/cucumber",
                "json:target/cucumber.json",
//              "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" -- plugin to run allure reports
        },
        features = "src/test/resources/features", // connects TestRunner with feature files.
        glue = "com/Stryker/stepDefinitions",// connects TestRunner with step definitions.
        dryRun = false,
        tags = "@TC_UI123 and @ui",
        publish = true // generating a report with public link
)
public class TestRunner {
}