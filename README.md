**Build Status** [![Build Status](https://buildhive.cloudbees.com/job/RestExpress/job/HyperExpress/badge/icon)](https://buildhive.cloudbees.com/job/RestExpress/job/HyperExpress/)

**Waffle.io** [![Stories in Ready](https://badge.waffle.io/RestExpress/HyperExpress.png?label=ready)](https://waffle.io/RestExpress/HyperExpress)

HyperExpress
============

Offers a simple way to add hypermedia links to your domain models or DTOs before serializing them to clients. 
HyperExpress supports several ways to generate links in responses.  If you want to create Link instances
or URLs (using token substitution in URLs patterns) by hand using the Builder pattern (Location header anyone?):

* **LinkBuilder** = Create Link instance using the builder pattern, optionally using URL token substitution.
* **UrlBuilder** = Create a string URL from a URL pattern, substituting URL tokens to produce a fully-populated URL.

Additionally, HyperExpress now supports the concept of a Resource, which is a generalized interface that
creates a 'presentation model' where your links can be added, other resources can be embedded, etc. with
output rendering being abstracted and dependent on the requested content type.

There are three concepts that play together to accomplish this:

* **RelationshipDefinition** = Use this to describe relationships between resources and namespaces.
* **TokenResolver** = For relationships (in the RelationshipBuilder) that have templated URLs, this class is able to populate the URL parameters, substituting actual values for the tokens (e.g. '{userId}').
* **TokenBinder** = For a collection of resources, resolves tokens in a URL by binding properties from individual objects in the collection to identifiers in the URL.

Additionally, there are a couple of helper classes:

* **MapStringFormat** = which will substitute names in a string with provided values (such as an URL).
* **RelTypes** = contains constants for REST-related standard Iana.org link-relation types.

The HyperExpress-Core project is primarily abstract implementations. Specific functionality is in the sub-projects, such
as HyperExpress-HAL, which has HAL-specific rendering (and parsing) of Resource implementations.

Please see HyperExpress-HAL for usage information: https://github.com/RestExpress/HyperExpress/tree/master/hal

Interested in other functionality?  Drop me a line... let's talk!

How-To
======

There are three phases in which HyperExpress helps you out managing links in your resources;

* Defining Relationships (between resources)
* Resolving URL tokens (a token in a URL is replaced with an ID)
* Creating Resources using a Factory

After that, it's the 'simple' matter of serializing the Resource to JSON (or XMl, or whatever).
HyperExpress-HAL has a serializer and deserializer for Jackson. Assuming you have your
own Jackson configuration, simply plug these in to your module (see the HyperExpress-HAL README).

Defining Relationships
----------------------

HyperExpress has a RelationshipDefinition class, accessed via the HyperExpress.relationships()
method, although it can be created outside the HyperExpress singleton class also.

Let's say we have a Blogging system with Blog, Entry and Comment resources. RelationshipDefinition
syntax for some of the relationships between them might be as follows:

**Caveat:** a Relationship definition captures the canonical or static relationships for a given type (or class). To add dynamic, context-sensitive links, you'll need to add them to the Resource yourself using Resource.addLink() methods.

```java
HyperExpress.relationships();

// Namespaces, CURIEs
.addNamespaces(
	new Namespace("blog", "http://namespaces.pearson.com/rels/{rel}"),
	new Namespace("foo", "http://namespaces.pearson.com/rels/{rel}"),
	new Namespace("bar", "http://namespaces.pearson.com/rels/{rel}"),
	new Namespace("bat", "http://namespaces.pearson.com/rels/{rel}")
)

// Blog collection (e.g. Read ALL with pagination)
.forCollectionOf(Blog.class)
	.rel(SELF, "/blogs")
		.withQuery("limit={limit}")
		.withQuery("offset={offset}")
	.rel(NEXT, "/blogs?offset={nextOffset}")
		.withQuery("limit={limit}").optional()
	.rel(PREVIOUS, "/blogs?offset={prevOffset}")
		.withQuery("limit={limit}").optional()

// Blog relationships
.forClass(Blog.class)
	.rel("blog:author", "/users/{userId}")
	.rel("blog:entries", "/blogs/{blogId}/entries")
	.rel(SELF, "/blogs/{blogId}")
	.rel(UP, "/blogs")

// BlogEntry collection (e.g. Read All with pagination)
.forCollectionOf(BlogEntry.class)
	.rel(SELF, "/blogs/{blogId}/entries")
		.withQuery("limit={limit}")
		.withQuery("offset={offset}")
	.rel(UP, "/blogs/{blogId}")
	.rel(NEXT, "/blogs/{blogId}/entries?offset={nextOffset}")
		.withQuery("limit={limit}").optional()
	.rel(PREVIOUS, "/blogs/{blogId}/entries?offset={prevOffset}")
		.withQuery("limit={limit}").optional()

// BlogEntry relationships
.forClass(BlogEntry.class)
	.rel(SELF, "/blogs/{blogId}/entries/{entryId}")
	.rel("blog:comments", "/blogs/{blogId}/entries/{entryId}/comments")
	.rel(UP, "/blogs/{blogId}/entries")

// Comment collection (e.g. Read All with pagination)
.forCollectionOf(Comment.class)
	.rel(SELF, "/blogs/{blogId}/entries/{entryId}/comments")
		.withQuery("limit={limit}")
		.withQuery("offset={offset}")
	.rel("blog:author", "/users/{userId}")
	.rel(UP, "/blogs/{blogId}/entries/{entryId}")
		.title("The parent blog blog entry")
	.rel(NEXT, "/blogs/{blogId}/entries/{entryId}/comments?offset={nextOffset}")
		.withQuery("limit={limit}").optional()
	.rel(PREVIOUS, "/blogs/{blogId}/entries/{entryId}/comments"?offset={prevOffset}")
		.withQuery("limit={limit}").optional()

// Comment relationships
.forClass(Comment.class)
   	.rel(SELF, "/blogs/{blogId}/entries/{entryId}/comments/{commentId}")
   		.title("This very comment")
   	.rel(UP, "/blogs/{blogId}/entries/{entryId}")
   		.title("The parent blog entry")
   	.rel("blog:author", "/users/{userId})
   		.title("The comment author");
```

Note that the namespaces apply to all resources and are oriented toward CURIE format (see: http://www.w3.org/TR/curie/).

More on the way... ;-)
