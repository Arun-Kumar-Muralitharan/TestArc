package com.TestArc.core.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.TestArc.core.model.QuarkResponse;
import com.TestArc.core.yml.YamlReader;

import java.util.*;

import static junit.framework.Assert.assertEquals;

public class HttpMethod
{
    private static final Playwright playwright = Playwright.create();
    private static APIRequestContext request;
    private static APIResponse response;

    private static Map<String, String> getHeaders(String userName)
    {
        YamlReader yamlReader = new YamlReader("user_details",false);

        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization",yamlReader.getValue(userName+".Authorization"));
        headers.put("Mfa-Token",yamlReader.getValue(userName+".mfaToken"));
        headers.put("Current-Entity-Id",yamlReader.getValue(userName+".entityId"));
        headers.put("Current-Group","investor");
        return headers;
    }

    public static void setApiRequestContext(String endpoint)
    {
        request = playwright.request().newContext(new APIRequest.NewContextOptions().setBaseURL(endpoint));
    }

    public static void closeApiRequestContext()
    {
        request.dispose();
    }

    public static QuarkResponse get(String endpoint, String userName, Map<String,Object> queryParam)
    {
        Objects.requireNonNull(userName);
        Map<String, String> headers = getHeaders(userName);
        response = playwright.request().newContext(new APIRequest.NewContextOptions().setExtraHTTPHeaders(headers).setBaseURL(endpoint)).get("");
//        response = request.get(endpoint);
//        assertEquals(200, response.status());
        return getAPIResponse(response);
    }

    public static QuarkResponse post(String endpoint, String userName, Map<String,Object> queryParam)
    {
        Objects.requireNonNull(userName);

        Map<String,String> headers = getHeaders(userName);
        var response = playwright.request().newContext(new APIRequest.NewContextOptions().setExtraHTTPHeaders(headers).setBaseURL(endpoint)).post("", (RequestOptions) queryParam);
        return getAPIResponse(response);
    }

    public static QuarkResponse put(String endpoint, String userName, Map<String,Object> queryParam)
    {
        Objects.requireNonNull(userName);

        Map<String,String> headers = getHeaders(userName);
        var response = playwright.request().newContext(new APIRequest.NewContextOptions().setExtraHTTPHeaders(headers).setBaseURL(endpoint)).put("", (RequestOptions) queryParam);
        return getAPIResponse(response);
    }

    public static QuarkResponse delete(String endpoint, String userName, Map<String,Object> queryParam)
    {
        Objects.requireNonNull(userName);

        Map<String,String> headers = getHeaders(userName);
        var response = playwright.request().newContext(new APIRequest.NewContextOptions().setExtraHTTPHeaders(headers).setBaseURL(endpoint)).delete("");
        return getAPIResponse(response);
    }


    private static QuarkResponse getAPIResponse(APIResponse apiResponse)
    {
        QuarkResponse responseFromAPI = new QuarkResponse();
        responseFromAPI.setBody(Arrays.toString(apiResponse.body()));
        responseFromAPI.setStatus(apiResponse.status());
        responseFromAPI.setStatusText(apiResponse.statusText());
        responseFromAPI.setUrl(apiResponse.url());
        return responseFromAPI;
    }
}
