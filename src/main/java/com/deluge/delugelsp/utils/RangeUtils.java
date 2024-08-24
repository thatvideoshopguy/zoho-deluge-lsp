package com.deluge.delugelsp.utils;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

public class RangeUtils {

    public static Range getRangeForMatch(String content, int start, int end) {
        int line = 0;
        int character = 0;
        int currentCharIndex = 0;

        String[] lines = content.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (currentCharIndex + lines[i].length() + 1 > start) {
                line = i;
                character = start - currentCharIndex;
                break;
            }
            currentCharIndex += lines[i].length() + 1;
        }

        Position startPosition = new Position(line, character);
        Position endPosition = new Position(line, character + (end - start));

        return new Range(startPosition, endPosition);
    }
}