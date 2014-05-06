/*
 * Copyright 2014, eCollege, Inc.  All rights reserved.
 */
package com.strategicgains.hyperexpress;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotates a domain model property that can be bound to a URL parameter.
 * 
 * @author toddf
 * @since May 6, 2014
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface BindUrlToken
{
	String value();
//	IdentifierAdapter adapter();
}
