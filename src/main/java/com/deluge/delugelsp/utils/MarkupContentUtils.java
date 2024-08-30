package com.deluge.delugelsp.utils;

import org.eclipse.lsp4j.MarkupContent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class MarkupContentUtils {

    // Define the base path for the documentation files
    private static final String BASE_DOCUMENTATION_PATH = "documentation/";

    /**
     * Loads a Markdown file from the resources and converts it to a MarkupContent object.
     *
     * @param filepath The name of the Markdown file (relative to the documentation directory).
     * @return MarkupContent with the loaded content.
     */
    public static MarkupContent getMarkupContentFromFile(String filepath) {
        // Construct the full path by combining the base path and the filename
        String fullPath = BASE_DOCUMENTATION_PATH + filepath;
        String markdownContent = loadMarkdownFile(fullPath);
        MarkupContent markupContent = new MarkupContent();
        markupContent.setKind("markdown");
        markupContent.setValue(markdownContent);
        return markupContent;
    }

    /**
     * Utility method to load a Markdown file as a String.
     *
     * @param absPath The full relative path to the Markdown file.
     * @return The content of the file as a String.
     */
    private static String loadMarkdownFile(String absPath) {
        try (InputStream inputStream = MarkupContentUtils.class.getClassLoader().getResourceAsStream(absPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            if (inputStream == null) {
                throw new IOException("File not found: " + absPath);
            }

            return reader.lines().collect(Collectors.joining("\n"));

        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Could not load markdown content.";
        }
    }
}