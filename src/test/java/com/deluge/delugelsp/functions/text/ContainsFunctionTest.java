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
    void testGetHoverInfo() throws Exception {
        String content = "This string contains a test.";
        Position position = new Position(0, 12); // "contains" starts at index 12

        CompletableFuture<Hover> hoverFuture = containsFunction.getHoverInfo(content, position);
        Hover hover = hoverFuture.get();

        assertNotNull(hover, "Hover information should not be null");
        assertNotNull(hover.getContents(), "Hover contents should not be null");
        assertTrue(hover.getContents().getRight().getValue().contains("**contains**"),
                "Hover should contain documentation about the 'contains' function.");
    }

    @Test
    void testGetCompletionItems() {
        String context = "string.";

        List<CompletionItem> items = containsFunction.getCompletionItems(context);

        assertNotNull(items);
        assertFalse(items.isEmpty());
        assertEquals("contains", items.get(0).getLabel());
    }

    @Test
    void testGetDiagnostics() {
        String contentWithCorrectUsage = "\"Hello World\".contains(\"Hello\");";
        String contentWithIncorrectUsage = "\"Hello World\".contains(Hello);";

        List<Diagnostic> diagnosticsCorrect = containsFunction.getDiagnostics(contentWithCorrectUsage);
        List<Diagnostic> diagnosticsIncorrect = containsFunction.getDiagnostics(contentWithIncorrectUsage);

        assertTrue(diagnosticsCorrect.isEmpty());

        assertFalse(diagnosticsIncorrect.isEmpty());
        assertEquals(DiagnosticSeverity.Error, diagnosticsIncorrect.get(0).getSeverity());
        assertEquals("The argument to 'contains' must be a string.", diagnosticsIncorrect.get(0).getMessage());
    }

}
