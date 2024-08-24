package com.deluge.delugelsp.functions.text;

import static com.deluge.delugelsp.utils.RangeUtils.getRangeForMatch;
import static com.deluge.delugelsp.utils.ParserUtils.extractFunctionAtPosition;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.DiagnosticSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ContainsFunction implements TextFunction {

    @Override
    public CompletableFuture<Hover> getHoverInfo(String content, Position position) {
        String functionName = extractFunctionAtPosition(content, position);
        if ("contains".equals(functionName)) {
            Hover hover = new Hover();
            hover.setContents(new MarkupContent("markdown",
                    "**contains**\n\n" +
                            "Returns `true` if the specified substring is present in the given string.\n\n" +
                            "Usage:\n```deluge\n" +
                            "<text1>.contains(<text2>);\n```\n" +
                            "Example:\n```deluge\n" +
                            "\"Zoho Creator\".contains(\"Zoho\"); // Returns: true\n" +
                            "```\n" +
                            "This function returns `true` if the main text includes the specified text. Otherwise, it will return `false`."));
            return CompletableFuture.completedFuture(hover);
        }
        return CompletableFuture.completedFuture(null);
    }


    @Override
    public List<CompletionItem> getCompletionItems(String context) {
        List<CompletionItem> items = new ArrayList<>();
        if (context.endsWith(".")) {
            CompletionItem item = new CompletionItem();
            item.setLabel("contains");
            item.setKind(CompletionItemKind.Method);
            item.setDetail("boolean contains(String substring)");
            item.setDocumentation("Returns `true` if the specified substring is present in the given string.");
            items.add(item);
        }
        return items;
    }

    @Override
    public List<Diagnostic> getDiagnostics(String content) {
        List<Diagnostic> diagnostics = new ArrayList<>();

        // Regex to find contains() method calls
        Pattern pattern = Pattern.compile("\\bcontains\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String argument = matcher.group(1).trim();

            // Simple check if the argument is a string (starts and ends with quotes)
            if (!argument.startsWith("\"") || !argument.endsWith("\"")) {
                Diagnostic diagnostic = new Diagnostic();
                diagnostic.setRange(getRangeForMatch(content, matcher.start(), matcher.end()));
                diagnostic.setSeverity(DiagnosticSeverity.Error);
                diagnostic.setMessage("The argument to 'contains' must be a string.");
                diagnostics.add(diagnostic);
            }
        }

        return diagnostics;
    }
}