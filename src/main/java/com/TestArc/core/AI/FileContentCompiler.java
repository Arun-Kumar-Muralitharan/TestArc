package com.TestArc.core.AI;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileContentCompiler {

    private static final List<String> filePaths = List.of(
            "src/main/java/com/yubi/quark/ui/UICommon.java",
            "src/main/java/com/yubi/quark/model/Element.java",
            "src/main/java/com/yubi/quark/util/AllureReportsUtil.java",
            "src/main/java/com/yubi/quark/yml/YamlReader.java",
            "src/main/java/com/yubi/quark/api/HttpMethod.java"
    );

    private static final String tempFilePath = "temp/compiledFileContents.txt";

    public static String getCodeContext() throws IOException {
        // Check if the temp file already exists
        File tempFile = new File(tempFilePath);
        if (tempFile.exists()) {
            // Read the existing file content and return it
            return new String(Files.readAllBytes(tempFile.toPath()));
        }

        // If the temp file doesn't exist, create the content
        StringBuilder contentBuilder = new StringBuilder();

        // Process each file
        for (String filePath : filePaths) {
            File file = new File(filePath);

            // Add the file path as a header
            contentBuilder.append(filePath).append("\n");

            // Read and append the full file content
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }
            }
            // Add a blank line between files
            contentBuilder.append("\n");
        }

        String content = contentBuilder.toString();

        // Create parent directories if they don't exist
        Path tempPath = Paths.get(tempFilePath);
        Files.createDirectories(tempPath.getParent());

        // Write the content to the temp file
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }
        return content;
    }

    //example files
    private static final List<String> examples = List.of(
            "src/test/java/com/yubi/examples/ui/MyAppsEndToEndTest.java"
    );

    private static final String exampletempFilePath = "temp/compiledFileContents_example.txt";
    public static String getExampleCodeContext() throws IOException {
        // Check if the temp file already exists
        File tempFile = new File(exampletempFilePath);
        if (tempFile.exists()) {
            // Read the existing file content and return it
            return new String(Files.readAllBytes(tempFile.toPath()));
        }

        // If the temp file doesn't exist, create the content
        StringBuilder contentBuilder = new StringBuilder();

        // Process each file
        for (String filePath : examples) {
            File file = new File(filePath);

            // Add the file path as a header
            contentBuilder.append(filePath).append("\n");

            // Read and append the full file content
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }
            }
            // Add a blank line between files
            contentBuilder.append("\n");
        }

        String content = contentBuilder.toString();

        // Create parent directories if they don't exist
        Path tempPath = Paths.get(exampletempFilePath);
        Files.createDirectories(tempPath.getParent());

        // Write the content to the temp file
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }
        return content;
    }

}