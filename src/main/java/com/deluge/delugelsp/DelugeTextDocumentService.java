package com.deluge.delugelsp;

import com.deluge.delugelsp.functions.FunctionManager;

import com.deluge.delugelsp.functions.text.TextFunction;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.services.TextDocumentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DelugeTextDocumentService implements TextDocumentService {

    private final FunctionManager functionManager;
    private final Map<String, String> documents = new ConcurrentHashMap<>();

    public DelugeTextDocumentService() {
        this.functionManager = new FunctionManager();
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        String uri = params.getTextDocument().getUri();
        String content = params.getContentChanges().get(0).getText();
        documents.put(uri, content);

        // Collect diagnostics for the document
        List<Diagnostic> diagnostics = getDiagnosticsForDocument(content);

        // Publish diagnostics to the client
        PublishDiagnosticsParams diagnosticsParams = new PublishDiagnosticsParams(uri, diagnostics);
        publishDiagnostics(diagnosticsParams);
    }

    private List<Diagnostic> getDiagnosticsForDocument(String content) {
        List<Diagnostic> diagnostics = new ArrayList<>();

        // Assuming you want to run diagnostics for all registered text functions
        for (TextFunction textFunction : functionManager.getAllTextFunctions()) {
            diagnostics.addAll(textFunction.getDiagnostics(content));
        }

        return diagnostics;
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        String uri = params.getTextDocument().getUri();
        String content = params.getTextDocument().getText();
        documents.put(uri, content);

        // Collect diagnostics for the opened document
        List<Diagnostic> diagnostics = getDiagnosticsForDocument(content);

        // Publish diagnostics to the client
        PublishDiagnosticsParams diagnosticsParams = new PublishDiagnosticsParams(uri, diagnostics);
        publishDiagnostics(diagnosticsParams);
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        documents.remove(params.getTextDocument().getUri());
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
        // Handle the event when a text document is saved.
    }

    private void publishDiagnostics(PublishDiagnosticsParams diagnosticsParams) {
        // Implement this method to send the diagnostics to the client.
        // This might involve interacting with a LanguageClient instance.
    }
}
