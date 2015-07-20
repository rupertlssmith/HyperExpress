package com.strategicgains.hyperexpress;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.exception.ResourceException;

public abstract class AbstractResourceFactoryStrategy implements ResourceFactoryStrategy {
    public static final int IGNORED_FIELD_MODIFIERS =
        Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT | Modifier.VOLATILE;

    private Set<Class<? extends Annotation>> inclusionAnnotations;
    private Set<Class<? extends Annotation>> exclusionAnnotations;

    @SafeVarargs
    public final AbstractResourceFactoryStrategy includeAnnotations(Class<? extends Annotation>... annotations) {
        if (annotations == null) {
            return this;
        }

        if (inclusionAnnotations == null) {
            inclusionAnnotations = new HashSet<>();
        }

        inclusionAnnotations.addAll(Arrays.asList(annotations));

        return this;
    }

    @SafeVarargs
    public final AbstractResourceFactoryStrategy excludeAnnotations(Class<? extends Annotation>... annotations) {
        if (annotations == null) {
            return this;
        }

        if (exclusionAnnotations == null) {
            exclusionAnnotations = new HashSet<>();
        }

        exclusionAnnotations.addAll(Arrays.asList(annotations));

        return this;
    }

    protected void copyProperties(Object from, Resource to) {
        copyProperties0(from.getClass(), from, to);
    }

    protected void addProperty(Resource to, Field f, Object value) {
        to.addProperty(f.getName(), value);
    }

    private void copyProperties0(Class<?> type, Object from, Resource to) {
        if (type == null) {
            return;
        }

        if (Resource.class.isAssignableFrom(type)) {
            to.from((Resource) from);

            return;
        }

        Field[] fields = getDeclaredFields(type);

        try {
            for (Field f : fields) {
                if (isIncluded(f)) {
                    f.setAccessible(true);

                    Object value = f.get(from);

                    if (value != null) {
                        addProperty(to, f, value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new ResourceException(e);
        }

        copyProperties0(type.getSuperclass(), from, to);
    }

    private boolean isIncluded(Field f) {
        if ((inclusionAnnotations == null && exclusionAnnotations == null) || f.getAnnotations().length == 0) {
            return ((f.getModifiers() & IGNORED_FIELD_MODIFIERS) == 0);
        }

        for (Annotation annotation : f.getAnnotations()) {
            Class<? extends Annotation> type = annotation.annotationType();

            if (inclusionAnnotations != null && inclusionAnnotations.contains(type)) {
                return true;
            }

            if (exclusionAnnotations != null && exclusionAnnotations.contains(type)) {
                return false;
            }
        }

        return ((f.getModifiers() & IGNORED_FIELD_MODIFIERS) == 0);
    }

    private Field[] getDeclaredFields(Class<?> type) {
        return type.getDeclaredFields();
    }
}
