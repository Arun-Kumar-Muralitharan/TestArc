package com.TestArc.core.model;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Element
{
    private final Locator locator;

    //Class Constructor
    public Element(Page page, String selector)
    {
        this.locator = page.locator(selector);
    }

    //Method to click on the element
    public void clickElement()
    {
        locator.click();
    }

    /***
     * Method to fill text in the element
     * @param text
     */
    public void fillText(String text)
    {
        locator.fill(text);
    }

    //Method to get the text of the element
    public String getText()
    {
        return locator.innerText();
    }

    //Method to check if the element is visible
    public boolean isVisible()
    {
        return locator.isVisible();
    }

    //Method to check if the element is enabled
    public boolean isEnabled()
    {
        return locator.isEnabled();
    }

    //Method to check if the element is checked
    public boolean isChecked()
    {
        return locator.isChecked();
    }

    //Method to get Attribute value
    public String getAttribute(String attribute)
    {
        return locator.getAttribute(attribute);
    }

    //Return the raw Locator
    public Locator getLocator()
    {
        return locator;
    }

    //Method to wait for Element
    public void waitForElement()
    {
        locator.waitFor();
    }

    //Method to wait for Element to be visible
    public void waitForElementToBeVisible()
    {
        locator.waitFor();
    }

    //Method to return count of elements
    public int count()
    {
        return locator.count();
    }

    //Method to return the first element in the list
    public void first()
    {
        locator.first();
    }

    //Method to Wait for Selector
    public void waitForSelector()
    {
        locator.waitFor();
    }
}
