package com.TestArc.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.TestArc.core.yml.YamlReader;
import com.TestArc.core.model.LoginDTO;
import com.TestArc.core.model.UserDetails;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthUtil
{

    public static UserDetails login(LoginDTO loginDTO) throws IOException, JSONException
    {
        JSONObject bearerTokenJSON = getBearerToken(loginDTO);
        String bearerToken = bearerTokenJSON.getString("access_token");
        DecodedJWT decodedJWT = JWT.decode(bearerToken);

        String userId = decodedJWT.getClaim("local_user_id").asString();
        String entityId = decodedJWT.getClaim("entity_id").asString();
        String group = decodedJWT.getClaim("groups").asList(String.class).get(0);
        String subGroup = "anchor"; // This is not in the JWT, so we'll keep it hardcoded for now

        AuthUtil.updateMfa(userId, entityId, group, subGroup, bearerToken);
        JSONObject mfaTokenJSON = AuthUtil.verifyMfa(userId, "999999", entityId, group, subGroup, bearerToken);

        String mfaToken = mfaTokenJSON.getString("mfa_token");

        UserDetails userDetails = new UserDetails();
        userDetails.setBearerToken(bearerToken);
        userDetails.setMfaToken(mfaToken);
        userDetails.setClientId(loginDTO.getClientId());
        userDetails.setClientSecret(loginDTO.getClientSecret());
        userDetails.setGrantType(loginDTO.getGrantType());
        userDetails.setUsername(loginDTO.getUsername());
        userDetails.setPassword(loginDTO.getPassword());
        userDetails.setScope(loginDTO.getScope());
        userDetails.setEntityId(entityId);
        return userDetails;
    }

    private static String getKeyCloakBaseURL()
    {
        YamlReader yamlReader = new YamlReader("Endpoint");
        return yamlReader.getValue("API_Endpoints.keycloak_url");
    }

    private static String getAuthBaseURL()
    {
        YamlReader yamlReader = new YamlReader("Endpoint");
        return yamlReader.getValue("API_Endpoints.auth_url");
    }

    private static String getRealm()
    {
        YamlReader yamlReader = new YamlReader("Endpoint");
        return yamlReader.getValue("keycloak.realm");
    }

    private static JSONObject getBearerToken(LoginDTO loginDTO) throws IOException, JSONException
    {
        String url = getKeyCloakBaseURL()+"/realms/"+getRealm()+"/protocol/openid-connect/token";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", loginDTO.getClientId()));
        params.add(new BasicNameValuePair("client_secret", loginDTO.getClientSecret()));
        params.add(new BasicNameValuePair("grant_type", loginDTO.getGrantType()));
        params.add(new BasicNameValuePair("username", loginDTO.getUsername()));
        params.add(new BasicNameValuePair("password", loginDTO.getPassword()));
        params.add(new BasicNameValuePair("scope", loginDTO.getScope()));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();

        if (entity != null)
        {
            String responseString = EntityUtils.toString(entity);
            JSONObject jsonResponse = new JSONObject(responseString);
            return jsonResponse;
        }

        return null;
    }


    public static void updateMfa(String userId, String entityId, String group, String subGroup, String authorizationToken)
    {
        String url = getAuthBaseURL()+"/users/" + userId + "/mfa?product_id=USRPTF";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPatch httpPatch = new HttpPatch(url);

        try
        {
            // Set request headers
            httpPatch.setHeader("accept", "application/json");
            httpPatch.setHeader("authorization", "Bearer " + authorizationToken);
            httpPatch.setHeader("content-type", "application/json");
            httpPatch.setHeader("current-entity-id", entityId);
            httpPatch.setHeader("current-group", group);
            httpPatch.setHeader("current-sub-group", subGroup);

            // Set request body
            JsonObject requestBody = new JsonObject();
            httpPatch.setEntity(new StringEntity(requestBody.toString()));

            // Execute the request
            HttpResponse response = httpClient.execute(httpPatch);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            System.out.println("Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static JSONObject verifyMfa(String userId, String otp, String entityId, String group, String subGroup, String authorizationToken)
    {
        String url = getAuthBaseURL()+"/users/" + userId + "/mfa_verify";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPatch httpPatch = new HttpPatch(url);

        try
        {
            // Set request headers
            httpPatch.setHeader("accept", "application/json");
            httpPatch.setHeader("authorization", "Bearer " + authorizationToken);
            httpPatch.setHeader("content-type", "application/json");
            httpPatch.setHeader("current-entity-id", entityId);
            httpPatch.setHeader("current-group", group);
            httpPatch.setHeader("current-sub-group", subGroup);

            // Set request body
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("otp", otp);
            requestBody.addProperty("product_id", "USRPTF");
            httpPatch.setEntity(new StringEntity(requestBody.toString()));

            // Execute the request
            HttpResponse response = httpClient.execute(httpPatch);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            return new JSONObject(responseBody);
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}