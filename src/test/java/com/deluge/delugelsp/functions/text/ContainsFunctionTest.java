package com.deluge.delugelsp.functions.text;

import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.Diagnostic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ContainsFunctionTest {
    private ContainsFunction containsFunction;

    @BeforeEach
    void setUp() {
        containsFunction = new ContainsFunction();
    }

    @Test
    void testGetHoverInfo_AtDifferentPositions() throws Exception {
        String content = "foo = searchString.contains(\"bar\")";
        Position positionOnKeyword = new Position(0, 20); // "contains" starts at index 12
        Position positionBeforeKeyword = new Position(0, 5); // Hover before "contains"
        Position positionAfterKeyword = new Position(0, 35); // Hover after "contains"

        CompletableFuture<Hover> hoverFuture = containsFunction.getHoverInfo(content, positionOnKeyword);
        Hover hover = hoverFuture.get();

        assertNotNull(hover, "Hover information should not be null when on keyword");
        assertTrue(hover.getContents().getRight().getValue().contains("**contains**"),
                "Hover should contain documentation about the 'contains' function when on keyword.");

        hoverFuture = containsFunction.getHoverInfo(content, positionBeforeKeyword);
        hover = hoverFuture.get();

        assertNotNull(hover, "Hover information should not be null before keyword");
        assertFalse(hover.getContents().getRight().getValue().contains("**contains**"),
                "Hover should not contain 'contains' documentation when before keyword.");

        hoverFuture = containsFunction.getHoverInfo(content, positionAfterKeyword);
        hover = hoverFuture.get();

        assertNotNull(hover, "Hover information should not be null after keyword");
        assertFalse(hover.getContents().getRight().getValue().contains("**contains**"),
                "Hover should not contain 'contains' documentation when after keyword.");
    }

    @Test
    void testGetCompletionItems_VariedContexts() {
        String partialContext = "strin";
        String emptyContext = "";

        List<CompletionItem> itemsPartial = containsFunction.getCompletionItems(partialContext);
        List<CompletionItem> itemsEmpty = containsFunction.getCompletionItems(emptyContext);

        assertNotNull(itemsPartial);
        assertFalse(itemsPartial.isEmpty());
        assertTrue(itemsPartial.stream().anyMatch(item -> "contains".equals(item.getLabel())));

        assertNotNull(itemsEmpty);
        assertTrue(itemsEmpty.isEmpty(), "Completion should be empty when context is empty");
    }

    @Test
    void testGetDiagnostics_EdgeCases() {
        String contentWithUnclosedString = "\"Hello World.contains(\"Hello\");";
        String contentWithNullArgument = "\"Hello World\".contains(null);";
        String contentWithNestedFunctions = "\"Hello World\".contains(\"Hello\".substring(0, 2));";

        List<Diagnostic> diagnosticsUnclosed = containsFunction.getDiagnostics(contentWithUnclosedString);
        List<Diagnostic> diagnosticsNullArg = containsFunction.getDiagnostics(contentWithNullArgument);
        List<Diagnostic> diagnosticsNested = containsFunction.getDiagnostics(contentWithNestedFunctions);

        assertFalse(diagnosticsUnclosed.isEmpty(), "Should identify unclosed string");
        assertEquals(DiagnosticSeverity.Error, diagnosticsUnclosed.get(0).getSeverity());

        assertFalse(diagnosticsNullArg.isEmpty(), "Should identify null argument as error");
        assertEquals(DiagnosticSeverity.Error, diagnosticsNullArg.get(0).getSeverity());
        assertEquals("The argument to 'contains' must be a non-null string.", diagnosticsNullArg.get(0).getMessage());

        assertTrue(diagnosticsNested.isEmpty(), "Nested functions should be correctly diagnosed as valid");
    }

}
