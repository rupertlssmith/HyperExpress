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

import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.fluent.LinkResolver;
import com.strategicgains.hyperexpress.fluent.RelationshipBuilder;
import com.strategicgains.hyperexpress.fluent.TokenBinderCallback;
import com.strategicgains.hyperexpress.fluent.TokenResolver;

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
	private RelationshipBuilder relationshipBuilder;
	private LinkResolver linkResolver;
	private ThreadLocal<TokenResolver> tokenResolver;

	/*
	 * Private to prevent external instantiation.
	 */
	private HyperExpress()
	{
		resourceFactory = new DefaultResourceFactory();
		relationshipBuilder = new RelationshipBuilder();
		linkResolver = relationshipBuilder.createResolver();
		tokenResolver = new ThreadLocal<TokenResolver>();
	}


	// SECTION: STATIC - PUBLIC METHODS

    public static void registerResourceFactory(ResourceFactoryStrategy factoryStrategy, String contentType)
    {
    	INSTANCE._registerResourceFactory(factoryStrategy, contentType);
    }

	/**
	 * @param factoryStrategy
	 * @param contentType
	 */
    private void _registerResourceFactory(ResourceFactoryStrategy factoryStrategy, String contentType)
    {
    	resourceFactory.addStrategy(factoryStrategy, contentType);
    }


	public static Resource createResource(Object object, String contentType)
	{
		return INSTANCE._createResource(object, contentType);
	}

	public static Resource createCollectionResource(Class<?> componentType, String contentType)
	{
		return INSTANCE._createCollectionResource(componentType, contentType);
	}

	/**
	 * The HyperExpress to define relationships between resource types and namespaces.
	 * 
	 * @return the RelationshipBuilder contained in this singleton.
	 */
	public static RelationshipBuilder defineRelationships()
	{
		return INSTANCE.relationshipBuilder;
	}

	public static HyperExpress bindToken(String token, String value)
	{
		INSTANCE._bindToken(token, value);
		return null;
	}

//	public static HyperExpress bindToken(String token, UUID uuid)
//	{
//		return bindToken(token, Identifiers.UUID.format(uuid));
//		return null;
//	}

//	public static HyperExpress bindToken(String token, Identifier identifier)
//	{
//		return bindToken(token, Identifiers.UUID.format(identifier));
//	}

	public static HyperExpress bindToken(TokenBinderCallback<?> callback)
	{
		INSTANCE._addCallback(callback);
		return null;
	}

	public static void clearTokenBindings()
	{
		INSTANCE._clearTokenBindings();
	}


	// SECTION: PRIVATE INSTANCE METHODS

	/**
	 * @param object
	 * @param contentType
	 * @return
	 */
    private Resource _createResource(Object object, String contentType)
    {
    	Resource r = resourceFactory.createResource(object, contentType);
    	r.addLinks(linkResolver.resolve(object, _getTokenBindings()));
    	r.addNamespaces(linkResolver.getNamespaces());
	    return r;
    }

	/**
	 * @param childType
	 * @param contentType
	 * @return
	 */
    private Resource _createCollectionResource(Class<?> componentType, String contentType)
    {
    	Resource r = resourceFactory.createResource(null, contentType);
    	r.addLinks(linkResolver.resolve(componentType, _getTokenBindings()));
    	r.addNamespaces(linkResolver.getNamespaces());
	    return r;
    }

	private TokenResolver _bindToken(String token, String value)
    {
		TokenResolver tr = _getTokenBindings();

		if (tr == null)
		{
			tr = new TokenResolver();
			tokenResolver.set(tr);
		}

		return tr.with(token, value);
    }

	private TokenResolver _addCallback(TokenBinderCallback<?> callback)
    {
		TokenResolver tr = _getTokenBindings();

		if (tr == null)
		{
			tr = new TokenResolver();
			tokenResolver.set(tr);
		}

		return tr.callback(callback);
    }

	private void _clearTokenBindings()
	{
		TokenResolver tr = _getTokenBindings();

		if (tr != null)
		{
			tr.clear();
			tokenResolver.remove();
		}
	}

	private TokenResolver _getTokenBindings()
	{
		return tokenResolver.get();
	}
}
