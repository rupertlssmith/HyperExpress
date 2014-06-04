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
 * <p/>
 * Before HyperExpress can create resources for a given media type, each media type
 * must be registered with a corresponding ResourceFactoryStrategy. This enables
 * createResource() and createCollectionResource() to know what type of resource to
 * create based on content-type.
 * <p/>
 * To support HAL (Hypertext Application Language) style resources, register the HAL
 * resource factory as follows:
 * <p/>
 * <code>
 * HyperExpress.registerResourceFactoryStrategy(new HalResourceFactory(), "application/hal+json");
 * </code>
 * <p/>
 * If you want HAL links for both application/json and application/hal+json media types, register
 * them as follows:
 * <p/>
 * <code>
 * HalResourceFactory halFactory = new HalResourceFactory();
 * HyperExpress.registerResourceFactoryStrategy(halFactory, "application/hal+json");
 * HyperExpress.registerResourceFactoryStrategy(halFactory, "application/json");
 * </code>
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

	/**
	 * Register a ResourceFactoryStrategy for a content-type. In order for createResource() and
	 * createCollectionResource() to know what type of resource to create based on content-type,
	 * ResourceFactoryStrategy instances must be registered for each media type support. For
	 * example, to support HAL (Hypertext Application Language) style resources, register the
	 * HAL resource factory as follows:
	 * <p/>
	 * <code>
	 * HyperExpress.registerResourceFactoryStrategy(new HalResourceFactory(), "application/hal+json");
	 * </code>
	 * <p/>
	 * If you want HAL links for both application/json and application/hal+json media types, register
	 * them as follows:
	 * <p/>
	 * <code>
	 * HalResourceFactory halFactory = new HalResourceFactory();
	 * HyperExpress.registerResourceFactoryStrategy(halFactory, "application/hal+json");
	 * HyperExpress.registerResourceFactoryStrategy(halFactory, "application/json");
	 * </code>
	 * 
	 * @param factoryStrategy
	 * @param contentType
	 * @see HalResourceFactory
	 */
    public static void registerResourceFactoryStrategy(ResourceFactoryStrategy factoryStrategy, String contentType)
    {
    	INSTANCE._registerResourceFactoryStrategy(factoryStrategy, contentType);
    }

    /**
     * Create a resource instance from the object for the given content type. Properties from
     * the object are copied into the resulting Resource. Also, links are injected for appropriate
     * relationships defined via HyperExpress.relationships(), using any HyperExpress.bind() or
     * HyperExpress.tokenBinder() settings to populate the tokens in the URLs.
     * 
     * @param object
     * @param contentType
     * @return
     */
	public static Resource createResource(Object object, String contentType)
	{
		return INSTANCE._createResource(object, contentType);
	}

	/**
	 * Creates a collection resource, embedding the components in a rel name derived from the component type
	 * simple class name. The class name is lower-cased and pluralized in order to create the rel name.
	 * 
	 * @param components the objects to embed. They will be converted to Resource instances also.
	 * @param componentType the object type of the components.
	 * @param contentType the desired content type of the resource (e.g. "application/hal+json")
	 * @return a new Resource instance with the collection embedded (as Resources).
	 */
	public static Resource createCollectionResource(Collection<Object> components, Class<?> componentType, String contentType)
	{
		return INSTANCE._createCollectionResource(components, componentType, contentType);
	}

	/**
	 * Creates a collection resource, embedding the components in the given componentRel name.
	 * 
	 * @param components the objects to embed. They will be converted to Resource instances also.
	 * @param componentType the object type of the components.
	 * @param componentRel the 'rel' name to use when embedding the resources.
	 * @param contentType the desired content type of the resource (e.g. "application/hal+json")
	 * @return a new Resource instance with the collection embedded (as Resources).
	 */
	public static Resource createCollectionResource(Collection<Object> components, Class<?> componentType, String componentRel, String contentType)
	{
		return INSTANCE._createCollectionResource(components, componentType, componentRel, contentType);
	}

	/**
	 * The HyperExpress to define relationships between resource types and namespaces.
	 * 
	 * @return the RelationshipDefinition contained in this singleton.
	 * @see RelationshipDefinition
	 */
	public static RelationshipDefinition relationships()
	{
		return INSTANCE.relationshipDefinition;
	}

	/**
	 * Set the RelationshipDefinition for HyperExpress.
	 * 
	 * @param relationships a RelationshipDefinition
	 * @see RelationshipDefinition
	 */
	public static void relationships(RelationshipDefinition relationships)
	{
		INSTANCE.relationshipDefinition = relationships;
	}

	/**
	 * Bind a URL token to a string value. During resource creation, any URL
	 * tokens matching the given token string are replace with the provided
	 * value.
	 * <p/>
	 * The TokenResolver bindings are specific to the current thread.
	 * 
	 * @param token a URL token name.
	 * @param value the substitution value.
	 * @return the underlying TokenResolver for this binding.
	 */
	public static TokenResolver bind(String token, String value)
	{
		return INSTANCE._bindToken(token, value);
	}

	/**
	 * Bind a TokenBinder to the elements in a collection resource.
	 * When a collection resource is created via createCollectionResource(),
	 * the TokenBinder is called for each element in the collection to bind
	 * URL tokens to individual properties within the element, if necessary.
	 * <p/>
	 * The TokenBinder is specific to the current thread.
	 * 
	 * @param callback a TokenBinder
	 */
	public static <T> void tokenBinder(TokenBinder<T> callback)
	{
		INSTANCE._addTokenBinder(callback);
	}

	/**
	 * Remove all the token substitution bindings and TokenBinder callbacks from 
	 * this thread's TokenResolver instance.
	 * <p/>
	 * It is recommended to call clearTokenBindings() after each request is complete
	 * to prevent TokenResolver and TokenBinder instance buildup. Otherwise, they
	 * are additive.
	 */
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
	 * Creates a collection resource, embedding the components in a rel name derived from the component type
	 * simple class name. The class name is lower-cased and pluralized in order to create the rel name.
	 * 
	 * @param components
	 * @param componentType
	 * @param contentType
	 * @return a new Resource instance with the collection embedded (as Resources).
	 */
	private Resource _createCollectionResource(Collection<Object> components, Class<?> componentType, String contentType)
	{
		String componentRel = Strings.pluralize(componentType.getSimpleName().toLowerCase());
		return _createCollectionResource(components, componentType, componentRel, contentType);
	}

	/**
	 * Creates a collection resource, embedding the components in the given componentRel name.
	 * 
	 * @param components
	 * @param componentType
	 * @param componentRel
	 * @param contentType
	 * @return a new Resource instance with the collection embedded (as Resources).
	 */
    @SuppressWarnings("unchecked")
	private Resource _createCollectionResource(Collection<Object> components, Class<?> componentType, String componentRel, String contentType)
	{
		Resource root = resourceFactory.createResource(null, contentType);
		Collection<LinkBuilder> templates = relationshipDefinition.getCollectionLinkBuilders(componentType).values();
		root.addLinks(_resolveUrlTokens(templates, null, _acquireTokenResolver()));
		root.addNamespaces(relationshipDefinition.getNamespaces().values());
		Resource childResource = null;

		if (components == null || components.isEmpty())
		{
			root.addResources(componentRel, Collections.EMPTY_LIST);
		}
		else
		{
			for (Object component : components)
			{
				childResource = _createResource(component, contentType);
				root.addResource(componentRel, childResource);
			}
		}

		return root;
	}

	private TokenResolver _bindToken(String token, String value)
    {
		return _acquireTokenResolver().bind(token, value);
    }

	private <T> TokenResolver _addTokenBinder(TokenBinder<T> callback)
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
