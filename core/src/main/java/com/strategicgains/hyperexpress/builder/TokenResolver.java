package com.strategicgains.hyperexpress.builder;

import java.util.Collection;
import java.util.List;

public interface TokenResolver {
    TokenResolver bind(String tokenName, String value);

    TokenResolver bind(String tokenName, String... multiValues);

    TokenResolver bind(String tokenName, List<String> multiValues);

    void clear();

    void remove(String tokenName);

    <T> TokenResolver binder(TokenBinder<T> callback);

    void clearBinders();

    void reset();

    String resolve(String pattern);

    String[] resolveMulti(String pattern);

    String resolve(String pattern, Object object);

    Collection<String> resolve(Collection<String> patterns);

    Collection<String> resolve(Collection<String> patterns, Object object);
}
