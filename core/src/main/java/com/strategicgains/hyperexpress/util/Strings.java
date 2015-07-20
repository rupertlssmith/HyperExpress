package com.strategicgains.hyperexpress.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class Strings {
    // Regular expression for the hasTokene(String) method.
    private static final String TEMPLATE_REGEX = "\\{(\\w*?)\\}";
    private static final Pattern TEMPLATE_PATTERN = Pattern.compile(TEMPLATE_REGEX);

    private static final Map<String, String> PLURALIZATION_RULES = new LinkedHashMap<String, String>();

    static {
        PLURALIZATION_RULES.put("(ox)$", "$1en");
        PLURALIZATION_RULES.put("(\\w+)(x|ch|ss|sh)$", "$1$2es");
        PLURALIZATION_RULES.put("(\\w+)([^aeiou])y$", "$1$2ies");
        PLURALIZATION_RULES.put("(\\w*)(f)$", "$1ves");
        PLURALIZATION_RULES.put("(\\w*)(fe)$", "$1ves");
        PLURALIZATION_RULES.put("(\\w+)(sis)$", "$1ses");
        PLURALIZATION_RULES.put("(\\w*)person$", "$1people");
        PLURALIZATION_RULES.put("(\\w*)child$", "$1children");
        PLURALIZATION_RULES.put("(\\w*)series$", "$1series");
        PLURALIZATION_RULES.put("(\\w*)foot$", "$1feet");
        PLURALIZATION_RULES.put("(\\w*)tooth$", "$1teeth");
        PLURALIZATION_RULES.put("(\\w*)bus$", "$1buses");
        PLURALIZATION_RULES.put("(\\w*)man$", "$1men");
        PLURALIZATION_RULES.put("(\\w*)mouse$", "$1mice");
        PLURALIZATION_RULES.put("(\\w*)goose$", "$1geese");
        PLURALIZATION_RULES.put("(\\w*)moose$", "$1moose");
    }

    private Strings() {
        // Prevents instantiation.
    }

    public static String pluralize(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        for (Entry<String, String> rule : PLURALIZATION_RULES.entrySet()) {
            final String pattern = rule.getKey().toString();
            final String replacement = rule.getValue().toString();

            if (word.matches(pattern)) {
                return word.replaceFirst(pattern, replacement);
            }
        }

        return word.replaceFirst("([\\w]+)([^s])$", "$1$2s");
    }

    public static boolean hasToken(String string) {
        return TEMPLATE_PATTERN.matcher(string).find();
    }
}
