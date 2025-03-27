package com.TestArc.core.AI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class chatGPTTestGenerator
{

    public static String generateCodeForUI(String testName, String testScenario) throws Exception {
        return generateTestCode(testName, testScenario,true);
    }

    public static String generateCodeForAPI(String testName, String testScenario) throws Exception {
        return generateTestCode(testName, testScenario,false);
    }


    private static String generateTestCode(String testName, String testScenario,boolean isUI) throws Exception
    {
        String url = "https://llmproxy.go-yubi.in/v1/chat/completions";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer sk-zttMgvEoxbSeqchW2UXIkw");

        JSONObject jsonInput = new JSONObject();
        jsonInput.put("model", "gpt-4o-(US)");

        JSONArray messages = new JSONArray();
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");

        String codeContext = FileContentCompiler.getCodeContext();
        String exampleContext = FileContentCompiler.getExampleCodeContext();

        String promptFilePath = isUI ? "src/main/resources/ui.prompt" : "src/main/resources/api.prompt";
        String prompt = new String(Files.readAllBytes(Paths.get(promptFilePath)));
        prompt = prompt.replace("${testScenario}", testScenario);
        prompt = prompt.replace("${codeContext}", codeContext);
        prompt = prompt.replace("${exampleCode}", exampleContext);

        systemMessage.put("content", prompt);
        messages.put(systemMessage);
        jsonInput.put("messages", messages);

        httpPost.setEntity(new StringEntity(jsonInput.toString(), "utf-8"));

        HttpResponse response = httpClient.execute(httpPost);
        int code = response.getStatusLine().getStatusCode();
        System.out.println("Response Code: " + code);
        JSONObject responseBody = new JSONObject(EntityUtils.toString(response.getEntity()));

        // Extract "content" from the first "message" inside "choices"
        JSONArray choices = responseBody.getJSONArray("choices");
        if (!choices.isEmpty())
        {
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            AITestCaseFileGenerator.createTestCaseFile(message.getString("content"), testName);
            return message.getString("content");
        }

        return "";
    }
}