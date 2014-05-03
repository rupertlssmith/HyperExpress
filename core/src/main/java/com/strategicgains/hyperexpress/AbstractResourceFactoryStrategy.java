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
 * @since Apr 11, 2014
 */
public abstract class AbstractResourceFactoryStrategy
implements ResourceFactoryStrategy
{
	public static final int IGNORED_FIELD_MODIFIERS = Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT | Modifier.VOLATILE;

	private Set<Class<? extends Annotation>> includedAnnotations;
	private Set<Class<? extends Annotation>> excludedAnnotations;

    @Override
    @SafeVarargs
    public final ResourceFactoryStrategy includeAnnotations(Class<? extends Annotation>... annotations)
    {
		if (annotations == null) return this;

		if (includedAnnotations == null)
		{
			includedAnnotations = new HashSet<>();
		}

		includedAnnotations.addAll(Arrays.asList(annotations));
	    return this;
    }

	@Override
    @SafeVarargs
    public final ResourceFactoryStrategy excludeAnnotations(Class<? extends Annotation>... annotations)
    {
		if (annotations == null) return this;

		if (excludedAnnotations == null)
		{
			excludedAnnotations = new HashSet<>();
		}

		excludedAnnotations.addAll(Arrays.asList(annotations));
	    return this;
    }

	protected void copyProperties(Object from, Resource to)
	{
		copyProperties0(from.getClass(), from, to);
	}

	private void copyProperties0(Class<?> type, Object from, Resource to)
	{
		if (type == null) return;

		Field[] fields = getDeclaredFields(type);

		try
		{
			for (Field f : fields)
			{
				if (isIncluded(f))
				{
					f.setAccessible(true);
					Object value = f.get(from);
					
					if (value != null)
					{
						to.addProperty(f.getName(), value);
					}
				}
			}
		}
		catch (IllegalAccessException e)
		{
			throw new ResourceException(e);
		}

		copyProperties0(type.getSuperclass(), from, to);
	}

	private boolean isIncluded(Field f)
    {
		if ((includedAnnotations == null && excludedAnnotations == null)
			|| f.getAnnotations().length == 0)
		{
			return ((f.getModifiers() & IGNORED_FIELD_MODIFIERS) == 0);
		}

		for (Annotation annotation : f.getAnnotations())
		{
			Class<? extends Annotation> type = annotation.annotationType();

			if (includedAnnotations != null && includedAnnotations.contains(type))
			{
				return true;
			}

			if (excludedAnnotations != null && excludedAnnotations.contains(type))
			{
				return false;
			}
		}

		return ((f.getModifiers() & IGNORED_FIELD_MODIFIERS) == 0);
	}

	private Field[] getDeclaredFields(Class<?> type)
	{
		return type.getDeclaredFields();
	}
}
