package com.TestArc.core.util;

import com.TestArc.core.yml.YamlReader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;


public class FileUtil
{
    public static final String DEFAULT_PROFILE = "STG";

    public static Map<String, Object> getDataBasedOnProfileForResources(String fileName)
    {
        Yaml yaml = new Yaml();
        Map<String, Object> yamlData;

        String profileName = System.getenv("env").toUpperCase();
        System.out.println("env picked up : " + profileName);
        String configFile = String.format(fileName+"-%s.yml", profileName);

        try
            {
                yamlData = yaml.load(YamlReader.class.getClassLoader().getResourceAsStream(configFile));
            }
        catch (YAMLException exception)
            {
                System.out.println("unable to open file with name : " + configFile + ", exception: "+exception.getMessage());
                System.out.println("trying to load default properties file.....");
                yamlData = yaml.load(YamlReader.class.getClassLoader().getResourceAsStream(fileName+".yml"));
            }

        return yamlData;
    }

    public static Map<String, Object> getDataBasedOnProfileForFiles(String filePath)
    {
        Yaml yaml = new Yaml();
        Map<String, Object> yamlData;

        // Read environment variable for profile
        String profileName = System.getenv("env");
        if (profileName == null || profileName.isEmpty())
        {
            throw new IllegalArgumentException("Environment variable 'env' is not set");
        }
        profileName = profileName.toUpperCase();
        System.out.println("Environment picked up: " + profileName);

        // Construct profile-specific file name
        String profileFile = filePath + "-" + profileName + ".yml";

        File file = new File(profileFile);
        if (!file.exists())
        {
            System.out.println("Profile-specific file not found: " + profileFile);
            System.out.println("Trying to load default properties file...");
            file = new File(filePath + ".yml");
        }

        // Load YAML file
        try (FileInputStream fis = new FileInputStream(file))
        {
            yamlData = yaml.load(fis);
        } catch (IOException e)
        {
            throw new RuntimeException("Error loading YAML file: " + file.getAbsolutePath(), e);
        }

        return yamlData;
    }



    public static String getFullFileNameBasedOnProfile(String fileName)
    {
        String profileName = System.getenv("env").toUpperCase();
        return String.format(fileName+"-%s.yml", profileName);
    }
}
