/*
    Copyright 2014, Strategic Gains, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.strategicgains.hyperexpress;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.exception.ResourceException;

/**
 * @author toddf
 * @since  Apr 11, 2014
 */
public abstract class AbstractResourceFactoryStrategy implements ResourceFactoryStrategy {
    public static final int IGNORED_FIELD_MODIFIERS =
        Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT | Modifier.VOLATILE;

    private Set<Class<? extends Annotation>> inclusionAnnotations;
    private Set<Class<? extends Annotation>> exclusionAnnotations;

    /**
     * Instead of requiring its own property/field annotations, this strategy supports the ability to denote which
     * fields you want to include in your resources by utilizing*ANY* annotations.
     *
     * <p/>By default, this ResourceFactoryStrategy includes all properties, public and private, except static, final,
     * volatile and transient fields.
     *
     * @param  annotations the Field annotations that indicate which properties to copy to resources.
     *
     * @return a reference to the ResourceFactoryStrategy to facilitate method chaining.
     */
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

    /**
     * By default, AbstractResourceFactoryStrategy includes all properties from an object and copies them to the
     * newly-created Resource. It does exclude, final, static, transient and volatile fields.
     *
     * <p/>To leverage annotations on fields to exclude from inclusion, this method allows callers to indicate which
     * fields to not include in newly-created Resource instances.
     *
     * @param  annotations the Field annotations that indicate which properties not copied to resources.
     *
     * @return a reference to the ResourceFactoryStrategy to facilitate method chaining.
     */
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

    /**
     * Entry-point into the deep-copy functionality.
     *
     * @param from an Object instance, never null.
     * @param to   a presumably-empty Resource instance.
     */
    protected void copyProperties(Object from, Resource to) {
        copyProperties0(from.getClass(), from, to);
    }

    /**
     * Template method. Sub-classes can override to possibly handle annotations on the field, etc. By default, this
     * method simply adds the property to the resource using the value and the given name of the field.
     *
     * @param to    the destination resource.
     * @param f     the field to copy.
     * @param value the value of the field.
     */
    protected void addProperty(Resource to, Field f, Object value) {
        to.addProperty(f.getName(), value);
    }

    /**
     * Recursively-copies properties from the Object, up the inheritance hierarchy, to the destination Resource.
     *
     * @param type the Type of the object being copied. May be a super-type of the 'from' object.
     * @param from the instance of Object being copied.
     * @param to   the destination Resource instance.
     */
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

    /**
     * Answers whether the Field should be copied. By default it ignores (answers 'false' to) static, final, volatile
     * and transient fields.
     *
     * @param  f a Field of a Class.
     *
     * @return true if the field should be copied into the resource. Otherwise, false.
     */
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
