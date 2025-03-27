package com.TestArc.examples.ui;

import com.microsoft.playwright.options.LoadState;
import com.TestArc.core.model.Element;
import com.TestArc.core.ui.UICommon;
import com.TestArc.core.util.AllureReportsUtil;
import com.TestArc.core.yml.UserDetailsYmlGenerator;
import com.TestArc.core.yml.YamlReader;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static com.TestArc.core.ui.UICommon.page;

@Execution(ExecutionMode.CONCURRENT)

@Epic("UI Tests")  // This can be used for high-level grouping
@Feature("My Apps")  // More specific feature name
public class MyAppsEndToEndTest
{

    @BeforeAll
    static void start()
    {
//        To launch a specific browser at a specific resolution call as below
//        UICommon.launchBrowser(String.valueOf(BrowserType.FIREFOX), Resolution.LARGE);
        UICommon.launchBrowser();
        UserDetailsYmlGenerator.generateUserDetails("Users");
    }

    @BeforeEach
    void initiate()
    {
        UICommon.createContext();
        UICommon.createPage();
    }

    @AfterEach
    void close()
    {
        UICommon.closeSession();
    }

    @AfterAll
    static void exit()
    {
        UICommon.closeBrowser();
    }

    @Test
    @Story("OMS TL End to End")
    @Description("End To End OMS UI Flow in OMS in My Apps")
    public void myAppsEndToEnd()
    {
        YamlReader endpoint = new YamlReader("Endpoint");
        YamlReader route = new YamlReader("Routes");
        String creditURL = endpoint.getValue("UI_Endpoints.Credit_Home");
        YamlReader user = new YamlReader("Users");
        String customerEmail = user.getValue("Users.KLMNOP.email");
        String customerPassword = user.getValue("Users.KLMNOP.password");
        String MyAppRoute = route.getValue("UI_Routes.My_Applications");
        String myAppURL = UICommon.constructRoute(creditURL, MyAppRoute);

        AllureReportsUtil.runStepWithScreenshot("Open the Credit Application", () -> {
            UICommon.goToURL(creditURL);
        });

        AllureReportsUtil.runStepWithScreenshot("Login as a Customer", () -> {
            UICommon.login(customerEmail, customerPassword);
        });

        AllureReportsUtil.runStepWithScreenshot("Navigate to My Applications", () -> {
            UICommon.goToURL(myAppURL);
        });

        AllureReportsUtil.runStepWithScreenshot("Select an Order New", () -> {
            Element dummy = new Element(page, "//div[@data-testid='yb-core-tabs_tab']//div[contains(text(), 'All')]");
            dummy.clickElement();
        });

        AllureReportsUtil.runStepWithScreenshot("Assert Details Page", () -> {
            String title = page.title();
            Assertions.assertEquals("Yubi", title, "Title does not match");
            page.waitForLoadState(LoadState.LOAD);
        });
    }
}