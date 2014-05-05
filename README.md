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

* **RelationshipBuilder** = Use this to describe relationships between resources and namespaces.
* **LinkResolver** = Given a RelationshipBuilder, this class provides links (from the RelationshipBuilder) relevant for a given resource type.
* **TokenResolver** = For relationships (in the RelationshipBuilder) that have templated URLs, this class is able to populate the URL parameters, substituting actual values for the tokens (e.g. '{userId}').

Additionally, there are a couple of helper classes to assist in creating URLs and Links en masse:

* **MapStringFormat** = which will substitute names in a string with provided values (such as an URL).
* **RelTypes** = contains constants for REST-related standard Iana.org link-relation types.

The HyperExpress-Core project is primarily abstract implementations. The real functionality is in the sub-projects, such
as HyperExpress-HAL, which has HAL-specific rendering of Resource implementations.

Please see HyperExpress-HAL for usage information: https://github.com/RestExpress/HyperExpress/tree/master/hal

Interested in other functionality?  Drop me a line... let's talk!
