package com.TestArc.core.yml;

import com.TestArc.core.util.AuthUtil;
import com.TestArc.core.model.LoginDTO;
import com.TestArc.core.model.UserDetails;
import com.TestArc.core.util.FileUtil;
import org.json.JSONException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class UserDetailsYmlGenerator
{
    private static final ReentrantLock lock = new ReentrantLock();
    /**
     * This method generates the user details yaml file
     * @param fileName : name of yaml file to read user details from
     */
    public static void generateUserDetails(String fileName)
    {
        lock.lock();
        try {
            // Check if file already exists
            File file = new File("user_details.yml");
            if (file.exists())
            {
                System.out.println("File already exists, skipping the generation of user details");
                return;
            }

            try {
                Map<String, Object> yamlData;
                yamlData = FileUtil.getDataBasedOnProfileForResources(fileName);
                Map<String, Object> users = (Map<String, Object>) yamlData.get("Users");
                Map<String, UserDetails> userDetailsMap = new HashMap<>();

                for (Map.Entry<String, Object> entry : users.entrySet())
                {
                    String userKey = entry.getKey();
                    Map<String, Object> userInfo = (Map<String, Object>) entry.getValue();
                    LoginDTO loginDTO = new LoginDTO();
                    String clientId = System.getenv("CLIENT_ID");
                    String clientSecret = System.getenv("CLIENT_SECRET");

                    loginDTO.setClientId(clientId);
                    loginDTO.setClientSecret(clientSecret);
                    loginDTO.setGrantType("password");
                    loginDTO.setUsername((String) userInfo.get("email"));
                    loginDTO.setPassword((String) userInfo.get("password"));
                    loginDTO.setScope("openid");

                    UserDetails userDetails = AuthUtil.login(loginDTO);
                    userDetails.setEntityId((String) userInfo.get("id"));
                    userDetailsMap.put(userKey, userDetails);
                }
                // Generate YAML file for UserDetails
                generateUserDetailsYaml(userDetailsMap);
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (JSONException e)
            {
                throw new RuntimeException(e);
            }
        } finally
        {
            lock.unlock();
        }
    }



    private static void generateUserDetailsYaml(Map<String, UserDetails> userDetailsMap) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setIndent(2);  // Set indent to 2 spaces
        options.setIndicatorIndent(0);  // Align indicators with content

        Yaml yaml = new Yaml(options);

        try (FileWriter writer = new FileWriter("user_details.yml"))
        {
            Map<String, Map<String, String>> fullMap = new LinkedHashMap<>();

            for (Map.Entry<String, UserDetails> entry : userDetailsMap.entrySet())
            {
                Map<String, String> userDetailsMp = new LinkedHashMap<>();
                UserDetails userDetails = entry.getValue();

                userDetailsMp.put("Authorization","Bearer "+ userDetails.getBearerToken());
                userDetailsMp.put("entityId", userDetails.getEntityId());
                userDetailsMp.put("grantType", userDetails.getGrantType());
                userDetailsMp.put("mfaToken", userDetails.getMfaToken());
                userDetailsMp.put("username", userDetails.getUsername());

                fullMap.put(entry.getKey(), userDetailsMp);
            }

            yaml.dump(fullMap, writer);
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
