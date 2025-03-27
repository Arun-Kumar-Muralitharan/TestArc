package com.TestArc.core.util;

import io.qameta.allure.Allure;

public class AllureReportsUtil
{
    /**
     * Call this method for Adding API Step in Allure
     * @param stepName
     * @param step
     */
    public static void runAPIStep(String stepName, Allure.ThrowableRunnableVoid step)
    {
        Allure.step(stepName,step);
    }

    /**
     * Call this method for adding UI Steps with screenshots in Allure
     * @param stepName
     * @param code
     */
    public static void runStepWithScreenshot(String stepName, Runnable code)
    {
        Allure.step(stepName, () -> {
            code.run();
            AllureScreenshotUtil.takeScreenshot();
        });
    }
}
