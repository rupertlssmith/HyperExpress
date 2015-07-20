package com.strategicgains.hyperexpress.builder;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.strategicgains.hyperexpress.util.MapStringFormat;
import com.strategicgains.hyperexpress.util.Strings;

public class DefaultTokenResolver implements TokenResolver {
    private static final MapStringFormat FORMATTER = new MapStringFormat();

    private Map<String, String> values = new HashMap<String, String>();
    private Map<String, Set<String>> multiValues = new HashMap<String, Set<String>>();
    private List<TokenBinder<?>> binders = new ArrayList<TokenBinder<?>>();

    public DefaultTokenResolver bind(String tokenName, String value) {
        if (value == null) {
            remove(tokenName);
        } else {
            values.put(tokenName, value);
        }

        return this;
    }

    public DefaultTokenResolver bind(String tokenName, String... multiValues) {
        if (multiValues == null || multiValues.length == 0) {
            remove(tokenName);
        } else {
            bind(tokenName, Arrays.asList(multiValues));
        }

        return this;
    }

    public DefaultTokenResolver bind(String tokenName, List<String> multiValues) {
        if (multiValues == null || multiValues.isEmpty()) {
            remove(tokenName);
        } else {
            bind(tokenName, multiValues.get(0));
            bindExtras(tokenName, multiValues);
        }

        return this;
    }

    public void clear() {
        values.clear();
        multiValues.clear();
    }

    public void remove(String tokenName) {
        values.remove(tokenName);
        multiValues.remove(tokenName);
    }

    public <T> DefaultTokenResolver binder(TokenBinder<T> callback) {
        if (callback == null) {
            return this;
        }

        binders.add(callback);

        return this;
    }

    public void clearBinders() {
        binders.clear();
    }

    public void reset() {
        clear();
        binders.clear();
    }

    public String resolve(String pattern) {
        return FORMATTER.format(pattern, values);
    }

    public String[] resolveMulti(String pattern) {
        List<String> resolved = new ArrayList<String>();
        String current = FORMATTER.format(pattern, values);
        resolved.add(current);

        for (Entry<String, Set<String>> entry : multiValues.entrySet()) {
            String firstValue = values.get(entry.getKey());
            Iterator<String> nextValue = entry.getValue().iterator();

            while (nextValue.hasNext()) {
                values.put(entry.getKey(), nextValue.next());

                String bound = FORMATTER.format(pattern, values);

                if (!Strings.hasToken(bound)) {
                    resolved.add(bound);
                }
            }

            values.put(entry.getKey(), firstValue);
        }

        return resolved.toArray(new String[0]);
    }

    public String resolve(String pattern, Object object) {
        if (object != null) {
            callTokenBinders(object);
        }

        return FORMATTER.format(pattern, values);
    }

    public Collection<String> resolve(Collection<String> patterns) {
        List<String> resolved = new ArrayList<String>(patterns.size());

        for (String pattern : patterns) {
            resolved.add(resolve(pattern));
        }

        return resolved;
    }

    public Collection<String> resolve(Collection<String> patterns, Object object) {
        if (object != null) {
            callTokenBinders(object);
        }

        return resolve(patterns);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("{");

        boolean isFirst = true;

        for (Entry<String, String> entry : values.entrySet()) {
            if (!isFirst) {
                s.append(", ");
            } else {
                isFirst = false;
            }

            s.append(entry.getKey());
            s.append("=");
            s.append(entry.getValue());
        }

        return s.toString();
    }

    private void bindExtras(String tokenName, List<String> valueList) {
        if (valueList.size() <= 1) {
            this.multiValues.remove(tokenName);
        } else {
            Set<String> extras = this.multiValues.get(tokenName);

            if (extras == null) {
                extras = new LinkedHashSet<String>(valueList.size() - 1);
                this.multiValues.put(tokenName, extras);
            }

            for (int i = 1; i < valueList.size(); ++i) {
                extras.add(valueList.get(i));
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void callTokenBinders(Object object) {
        if (object == null) {
            return;
        }

        for (TokenBinder tokenBinder : binders) {
            Class<?> binderClass =
                (Class<?>)
                ((ParameterizedType) tokenBinder.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];

            if (binderClass.isAssignableFrom(object.getClass())) {
                tokenBinder.bind(object, this);
            }
        }
    }
}
