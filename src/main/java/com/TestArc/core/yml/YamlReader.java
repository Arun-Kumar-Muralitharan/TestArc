package com.TestArc.core.yml;

import com.TestArc.core.util.FileUtil;

import java.util.HashMap;
import java.util.Map;
public class YamlReader
{
    private static Map<String, Map<String, Object>> yamlCachMap = new HashMap<>();
    private Map<String, Object> yamlData;


    public YamlReader(String fileName)
    {
        if(yamlCachMap.containsKey(fileName))
        {
            yamlData = yamlCachMap.get(fileName);
            return;
        }
        yamlData = FileUtil.getDataBasedOnProfileForResources(fileName);
        yamlCachMap.put(fileName, yamlData);
    }

    public YamlReader(String fileName,boolean isResourceFile)
    {
        if(!isResourceFile)
        {
            if (yamlCachMap.containsKey(fileName))
            {
                yamlData = yamlCachMap.get(fileName);
                return;
            }
            yamlData = FileUtil.getDataBasedOnProfileForFiles(fileName);
            yamlCachMap.put(fileName, yamlData);
        }
    }


    //Use this method to get the value from the Yaml file
    public String getValue(String key)
    {
        String[] parts = key.split("\\.");
        Map<String, Object> current = yamlData;

        for (int i = 0; i < parts.length - 1; i++)
        {
            current = (Map<String, Object>) current.get(parts[i]);
            if (current == null) {
                return null;
            }
        }

        return current.get(parts[parts.length - 1]).toString();
    }
}
