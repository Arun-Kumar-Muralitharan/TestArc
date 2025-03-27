package com.TestArc.core.AI;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class AITestCaseFileGenerator
{
    public static void createTestCaseFile(String content, String fileName) throws IOException
    {
        try (FileWriter writer = new FileWriter(fileName+".java"))
        {
            writer.write(content);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
