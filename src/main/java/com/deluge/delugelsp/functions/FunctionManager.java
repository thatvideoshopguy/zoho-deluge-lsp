package com.deluge.delugelsp.functions;

import com.deluge.delugelsp.functions.number.NumberFunction;
import com.deluge.delugelsp.functions.text.ContainsFunction;
import com.deluge.delugelsp.functions.text.TextFunction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FunctionManager {
    private final Map<String, TextFunction> textFunctions = new HashMap<>();
    private final Map<String, NumberFunction> numberFunctions = new HashMap<>();

    public FunctionManager() {
        // Register all text functions
        textFunctions.put("contains", new ContainsFunction());
//        textFunctions.put("substring", new SubstringFunction());

        // Register all number functions
//        numberFunctions.put("add", new AddFunction());
        // Register more functions as needed
    }

    public TextFunction getTextFunction(String name) {
        return textFunctions.get(name);
    }

    public Collection<TextFunction> getAllTextFunctions() {
        return textFunctions.values();
    }

    public NumberFunction getNumberFunctions(String name) {
        return numberFunctions.get(name);
    }
}
