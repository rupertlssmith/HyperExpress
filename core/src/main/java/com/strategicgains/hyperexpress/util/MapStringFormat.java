package com.strategicgains.hyperexpress.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapStringFormat {
    private static final String DEFAULT_END_DELIMITER = "}";
    private static final String DEFAULT_START_DELIMITER = "{";

    // PROTOCOL: VARIABLES

    private String endDelimiter;
    private String startDelimiter;

    public MapStringFormat() {
        this(DEFAULT_START_DELIMITER, DEFAULT_END_DELIMITER);
    }

    public MapStringFormat(String startDelimiter, String endDelimiter) {
        startDelimiter(startDelimiter);
        endDelimiter(endDelimiter);
    }

    public static Map<String, String> toMap(String... nameValuePairs) {
        Map<String, String> result = new HashMap<String, String>();

        if (nameValuePairs == null) {
            return result;
        }

        if ((nameValuePairs.length % 2) != 0) {
            throw new IllegalArgumentException("Name/value pairs unbalanced: " + nameValuePairs.toString());
        }

        for (int i = 0; i < nameValuePairs.length; i += 2) {
            result.put(nameValuePairs[i], nameValuePairs[i + 1]);
        }

        return result;
    }

    // PROTOCOL: ACCESSING

    public String endDelimiter() {
        return endDelimiter;
    }

    public void endDelimiter(String delimiter) {
        endDelimiter = delimiter;
    }

    public String startDelimiter() {
        return startDelimiter;
    }

    public void startDelimiter(String delimiter) {
        startDelimiter = delimiter;
    }

    public String format(String string, String... parameters) {
        if (parameters.length % 2 != 0) {
            throw new IllegalArgumentException("Parameters must be in name/value pairs");
        }

        return format(string, toMap(parameters));
    }

    public String format(String string, Map<String, String> parameters) {
        String result = string;
        StringBuilder sb = new StringBuilder();

        for (Entry<String, String> entry : parameters.entrySet()) {
            constructParameterName(sb, entry.getKey());
            result = result.replaceAll(sb.toString(), entry.getValue());
        }

        return result;
    }

    // PROTOCOL: UTILITY

    private String constructParameterName(StringBuilder sb, String key) {
        sb.setLength(0);
        sb.append('\\');
        sb.append(startDelimiter());
        sb.append(key);
        sb.append('\\');
        sb.append(endDelimiter());

        return sb.toString();
    }

    // SECTION: UTILITY - STATIC

}
