package com.deluge.delugelsp.functions.text;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.Position;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TextFunction {
    CompletableFuture<Hover> getHoverInfo(String content, Position position);
    List<CompletionItem> getCompletionItems(String context);
    List<Diagnostic> getDiagnostics(String content);
}