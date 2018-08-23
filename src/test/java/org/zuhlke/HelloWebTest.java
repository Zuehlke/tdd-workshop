package org.zuhlke;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class HelloWebTest {

    @Test
    // Run this test manually as it depends on a deployed application
    public void Hello() {
        String name = "Florian";
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080/JavaServerFaces/hello.jsf");

        String title = driver.getTitle();
        assertEquals("JSF 2.0 Hello World", title);

        WebElement nameField = driver.findElement(By.id("nameForm:nameField"));
        nameField.sendKeys(name);
        WebElement welcomeButton = driver.findElement(By.id("nameForm:welcomeButton"));
        welcomeButton.click();

        WebElement welcomeResult = driver.findElement(By.id("welcomeResult"));
        String text = welcomeResult.getText();

        assertEquals("Welcome " + name, text);

        driver.quit();
    }

}