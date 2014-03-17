package com.strategicgains.hyperexpress.domain.hal;

import java.util.Collection;
import java.util.Map;

import com.strategicgains.hyperexpress.domain.Resource;

public interface HalResource
extends Resource
{
	public void embed(String rel, Object resource);
	public void embed(String rel, Collection<? extends Object> resources);
	Map<String, Object> getLinks();
	Map<String, Object> getEmbedded();
}