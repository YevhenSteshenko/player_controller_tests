package tests;

import com.google.common.collect.ImmutableMap;
import org.testng.annotations.BeforeSuite;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void setEnv() {
        System.out.println("Set Environment:");
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Environment Name:  ", System.getProperty("env").toUpperCase())
                        .put("API Version:  ", System.getProperty("api_version").toUpperCase())
                        .put("Threads number:  ", System.getProperty("thread"))
                        .put("Parallel in:  ", System.getProperty("parallel"))
                        .put("Run Suites:  ", System.getProperty("suites"))

                        .build(), System.getProperty("user.dir")
                        + "/build/allure-results/");
    }
}
