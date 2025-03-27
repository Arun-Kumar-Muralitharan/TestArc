package com.TestArc.core.util;

import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;

import static com.TestArc.core.ui.UICommon.page;

public class AllureScreenshotUtil
{
    public static void takeScreenshot()
    {
        var screenshot = page.screenshot();
        Allure.addAttachment(page.url(),new ByteArrayInputStream(screenshot));
    }
}
