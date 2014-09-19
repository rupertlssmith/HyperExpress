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

import com.strategicgains.hyperexpress.builder.RelationshipDefinition;
import com.strategicgains.jbel.builder.PredicateBuilder;
import com.strategicgains.jbel.expression.Expression;


/**
 * @author toddf
 * @since Sep 15, 2014
 */
public class RelationshipExpressionBuilder
{
	private PredicateBuilder builder;
	private RelationshipDefinition then;

	public RelationshipExpressionBuilder(Expression expression)
    {
		super();
		this.builder = new PredicateBuilder(expression);
    }

	public RelationshipExpressionBuilder equalTo(Expression expression)
	{
		builder.equalTo(expression);
		return this;
	}

	public RelationshipExpressionBuilder equalTo(Object value)
	{
		builder.equalTo(value);
		return this;
	}

	public RelationshipExpressionBuilder then(RelationshipDefinition relationshipDefinition)
	{
		this.then = relationshipDefinition;
		return this;
	}

	public RelationshipExpressionBuilder otherwise(RelationshipExpressionBuilder elseBuilder)
	{
		this.elseBuilder = elseBuilder;
		return this;
	}
}
