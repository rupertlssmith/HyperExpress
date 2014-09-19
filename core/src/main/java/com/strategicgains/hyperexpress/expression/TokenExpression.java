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
package com.strategicgains.hyperexpress.expression;

import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.jbel.exception.EvaluationException;
import com.strategicgains.jbel.expression.Expression;

/**
 * @author toddf
 * @since Sep 15, 2014
 */
public class TokenExpression
implements Expression
{
	private String token;
	private TokenResolver resolver;

	public TokenExpression(String token)
	{
		super();
		this.token = token;
		// TODO: This no-worky as there will be no bindings in it...
		this.resolver = new TokenResolver();
	}

	@Override
    public Object evaluate(Object argument)
    throws EvaluationException
    {
		return resolver.resolve(token, argument);
    }
}
