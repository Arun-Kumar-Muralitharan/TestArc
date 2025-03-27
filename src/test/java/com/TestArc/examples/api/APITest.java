package com.TestArc.examples.api;

import com.TestArc.core.api.HttpMethod;
import com.TestArc.core.model.QuarkResponse;
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

@Epic("API Tests")  // This can be used for high-level grouping
@Feature("Orders")  // More specific feature name
@Execution(ExecutionMode.CONCURRENT)
public class APITest
{

    static YamlReader endpoint;
    static YamlReader route;
    private static HttpMethod httpMethod;

    @BeforeAll
    static void start()
    {
        UserDetailsYmlGenerator.generateUserDetails("Users");
        endpoint = new YamlReader("Endpoint");
        route = new YamlReader("Routes");
        HttpMethod.setApiRequestContext(endpoint.getValue("API_Endpoints.oms_api_url"));
    }

    @AfterAll
    static void exit()
    {
        HttpMethod.closeApiRequestContext();
    }

    @Test
    @Story("OMS Get API")
    @Description("OMS Orders API Testing")
    public void basicAPITests()
    {

        String oms_api_uri = endpoint.getValue("API_Endpoints.oms_api_url");
        String order_route = route.getValue("API_Routes.orders_route");
        String url = oms_api_uri +order_route;


        AllureReportsUtil.runAPIStep("OMS Get API",()->{
            QuarkResponse response = httpMethod.get(url,"KLMNOP",null);
            Assertions.assertTrue(response.isSuccessful());
        });
    }

}
