package com.nikolaslouret.apiclinicamedica.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;



@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue={"com.nikolaslouret.apiclinicamedica.steps", "com.nikolaslouret.apiclinicamedica.configs"}, monochrome = true)
public class CucumberRunnerTest {
}