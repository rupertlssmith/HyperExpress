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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.strategicgains.hyperexpress.builder.LinkBuilder;
import com.strategicgains.hyperexpress.builder.RelationshipDefinition;
import com.strategicgains.hyperexpress.builder.TokenBinder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.util.Strings;

/**
 * A Singleton object to manage creation of Link and Resource instances.
 * 
 * @author toddf
 * @since May 5, 2014
 */
public class HyperExpress
{
	private static final HyperExpress INSTANCE = new HyperExpress();

	private DefaultResourceFactory resourceFactory;
	private RelationshipDefinition relationshipDefinition;
	private ThreadLocal<TokenResolver> tokenResolver;

	/*
	 * Private to prevent external instantiation.
	 */
	private HyperExpress()
	{
		resourceFactory = new DefaultResourceFactory();
		relationshipDefinition = new RelationshipDefinition();
		tokenResolver = new ThreadLocal<TokenResolver>();
	}


	// SECTION: STATIC - PUBLIC METHODS

    public static void registerResourceFactoryStrategy(ResourceFactoryStrategy factoryStrategy, String contentType)
    {
    	INSTANCE._registerResourceFactoryStrategy(factoryStrategy, contentType);
    }

	public static Resource createResource(Object object, String contentType)
	{
		return INSTANCE._createResource(object, contentType);
	}

	public static Resource createCollectionResource(Collection<Object> components, Class<?> componentType, String contentType)
	{
		return INSTANCE._createCollectionResource(components, componentType, contentType);
	}

	/**
	 * The HyperExpress to define relationships between resource types and namespaces.
	 * 
	 * @return the RelationshipDefinition contained in this singleton.
	 */
	public static RelationshipDefinition relationships()
	{
		return INSTANCE.relationshipDefinition;
	}

	public static void relationships(RelationshipDefinition relationships)
	{
		INSTANCE.relationshipDefinition = relationships;
	}

	public static TokenResolver bind(String token, String value)
	{
		return INSTANCE._bindToken(token, value);
	}

	public static void tokenBinder(TokenBinder callback)
	{
		INSTANCE._addTokenBinder(callback);
	}

	public static void clearTokenBindings()
	{
		INSTANCE._clearTokenBindings();
	}


	// SECTION: PRIVATE INSTANCE METHODS

	/**
	 * @param factoryStrategy
	 * @param contentType
	 */
    private void _registerResourceFactoryStrategy(ResourceFactoryStrategy factoryStrategy, String contentType)
    {
    	resourceFactory.addFactoryStrategy(factoryStrategy, contentType);
    }

	/**
	 * @param object
	 * @param contentType
	 * @return
	 */
    private Resource _createResource(Object object, String contentType)
    {
    	Resource r = resourceFactory.createResource(object, contentType);
    	Collection<LinkBuilder> linkBuilders = relationshipDefinition.getLinkBuilders(object.getClass()).values();
    	r.addLinks(_resolveUrlTokens(linkBuilders, object, _acquireTokenResolver()));
    	r.addNamespaces(relationshipDefinition.getNamespaces().values());
	    return r;
    }

	/**
	 * @param components
	 * @param componentType
	 * @param contentType
	 * @return a new Resource instance with the collection embedded (as Resources).
	 */
    @SuppressWarnings("unchecked")
    private Resource _createCollectionResource(Collection<Object> components, Class<?> componentType, String contentType)
    {
    	Resource root = resourceFactory.createResource(null, contentType);
    	Collection<LinkBuilder> templates = relationshipDefinition.getCollectionLinkBuilders(componentType).values();
    	root.addLinks(_resolveUrlTokens(templates, null, _acquireTokenResolver()));
    	root.addNamespaces(relationshipDefinition.getNamespaces().values());
		Resource childResource = null;
		String childRel = Strings.pluralize(componentType.getSimpleName().toLowerCase());

		if (components == null || components.isEmpty())
		{
			root.addResources(childRel, Collections.EMPTY_LIST);
		}
		else
		{
			for (Object component : components)
			{
				childResource = _createResource(component, contentType);
				root.addResource(childRel, childResource);
			}
		}

	    return root;
    }

	private TokenResolver _bindToken(String token, String value)
    {
		return _acquireTokenResolver().bind(token, value);
    }

	private TokenResolver _addTokenBinder(TokenBinder callback)
    {
		return _acquireTokenResolver().binder(callback);
    }

	private void _clearTokenBindings()
	{
		TokenResolver tr = _getTokenResolver();

		if (tr != null)
		{
			tr.clear();
			tokenResolver.remove();
		}
	}

	private TokenResolver _acquireTokenResolver()
	{
		TokenResolver tr = _getTokenResolver();

		if (tr == null)
		{
			tr = new TokenResolver();
			tokenResolver.set(tr);
		}

		return tr;
	}

	private TokenResolver _getTokenResolver()
	{
		return tokenResolver.get();
	}

	private List<Link> _resolveUrlTokens(Collection<LinkBuilder> templates, Object object, TokenResolver tokenResolver)
    {
	    List<Link> links = new ArrayList<>(templates.size());

		for (LinkBuilder template : templates)
		{
			Link link = template.build(object, tokenResolver);
			tokenResolver.resolve(link.getHref(), object);

			if (link.has("optional") && link.hasToken())
			{
				// ignore it.
			}
			else
			{
				links.add(link);
			}
		}

		return links;
    }

}
