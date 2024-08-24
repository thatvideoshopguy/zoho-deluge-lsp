package com.deluge.delugelsp;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.services.WorkspaceService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DelugeWorkspaceService implements WorkspaceService {
    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
        // Handle changes in workspace folders

        params.getEvent().getAdded().forEach(folder -> {
            // Handle the addition of a new workspace folder
            System.out.println("Workspace folder added: " + folder.getUri());
            // Possibly initialize resources related to this folder
        });

        params.getEvent().getRemoved().forEach(folder -> {
            // Handle the removal of a workspace folder
            System.out.println("Workspace folder removed: " + folder.getUri());
            // Clean up resources related to this folder
        });
    }

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {
        // Handle changes in the workspace configuration

        // Assuming params contain configuration data in JSON or another format
        System.out.println("Workspace configuration changed");
        // You might want to parse the new configuration and apply it
        // Example: parse JSON or other structured data
        // Update internal settings or resources based on the new configuration
    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
        // Handle changes in watched files within the workspace

        params.getChanges().forEach(change -> {
            switch (change.getType()) {
                case Created:
                    // Handle file creation
                    System.out.println("File created: " + change.getUri());
                    break;
                case Changed:
                    // Handle file modification
                    System.out.println("File changed: " + change.getUri());
                    break;
                case Deleted:
                    // Handle file deletion
                    System.out.println("File deleted: " + change.getUri());
                    break;
            }
            // Possibly update internal states or trigger tasks
        });
    }

    @Override
    public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
        // Handle execution of commands sent from the client

        String command = params.getCommand();
        List<Object> arguments = params.getArguments();

        System.out.println("Executing command: " + command);

        // Example: Handling specific commands
        if ("deluge.command.example".equals(command)) {
            // Handle the specific command, possibly using arguments
            System.out.println("Executing example command with arguments: " + arguments);
            // Return a result or null if no result is needed
            return CompletableFuture.completedFuture("Command executed successfully");
        }

        // If the command is not recognized, return a default response or error
        return CompletableFuture.completedFuture(null);
    }
}


