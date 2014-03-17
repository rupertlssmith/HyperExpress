package com.strategicgains.hyperexpress.domain.htl;

import java.util.Collection;

import com.strategicgains.hyperexpress.domain.Resource;

public interface HtlResource<E>
extends Resource
{
	public void embed(String rel, Object resource);
	public void embed(String rel, Collection<? extends Object> resources);

	/**
	 * Assigns the items collection to the underlying 'items' collection.
	 * 
	 * @param items
	 */
	public void setItems(Collection<E> items);
}