package com.TestArc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Objects;

public class TestFramework
{
   private final WebDriver driver;

   /* Default Driver will be Chrome Driver */
    public TestFramework(WebDriver driver)
    {
        this.driver = Objects.requireNonNullElseGet(driver, ChromeDriver::new);
    }

    /* Call this method to Invoke the Browser Driver*/
    public static TestFramework InvokeDriver(WebDriver driver)
    {
        return new TestFramework(driver);
    }

    /* Call this method to navigate to URL */
    public void GoToURL(String url)
    {
        driver.get(url);
    }

    /* Find Element using this method */
    public WebElement Locator(By by)
    {
        return driver.findElement(by);
    }

    /* Use this method to click on the Page */
    public void click(By WebElem)
    {
        WebElement elem = Locator(WebElem);
        elem.click();
    }

    /* Use this method to enter text in the TextBox*/
    public void fill(By WebElem, String content)
    {
        WebElement elem = Locator(WebElem);
        elem.clear();
        elem.sendKeys(content);
    }

    /* Use this method to explicitly sleep */
    public void sleep(int TimeInSeconds)
    {
        try
        {
            Thread.sleep(TimeInSeconds * 1000L);
        }
        catch (InterruptedException e)
        {
            System.out.println(e);
        }
    }

    /* Use this method to Quit the Driver */
    public void QuitBrowser()
    {
        driver.quit();
    }
}