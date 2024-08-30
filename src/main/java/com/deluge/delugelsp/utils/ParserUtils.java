package com.deluge.delugelsp.utils;

import org.eclipse.lsp4j.Position;

public class ParserUtils {

    /**
     * Extracts the function name at the given cursor position from the provided content.
     * This method is designed to recognize a function call like contains(...).
     *
     * @param content  The entire content of the document.
     * @param position The position (line and character) in the document.
     * @return The function name at the given position or an empty string if no function is found.
     */
    public static String extractFunctionAtPosition(String content, Position position) {
        String[] lines = content.split("\n");
        if (position.getLine() >= lines.length) {
            return "";
        }

        String line = lines[position.getLine()];
        int charPosition = Math.min(position.getCharacter(), line.length());

        if (charPosition == 0) {
            return "";
        }

        // Start from the current character and move backwards to find the start of the function name
        int start = charPosition - 1;
        while (start > 0 && Character.isLetterOrDigit(line.charAt(start - 1))) {
            start--;
        }

        // Move end forward to find the end of the function name
        int end = start;
        while (end < line.length() && Character.isLetterOrDigit(line.charAt(end))) {
            end++;
        }

        // Check if the function is followed by an opening parenthesis
        if (end < line.length() && line.charAt(end) == '(') {
            return line.substring(start, end); // Return the function name
        }

        return "";
    }
}
