HyperExpress
============

Offers a simple way to add hypermedia links to your domain models or DTOs before serializing them to clients.

The main hypermedia link class is *Link*, which supports 'rel', 'href' and 'type' properties, where:

* **rel** = the relationship from this object to the referred-to object. See ... for standard 'rel' types.
* **href** = is the fully-qualified URL to the referred-to object.
* **type** = the type of the referred-to object.

There are two wrapper classes to assist in attaching *Link* instances to either your domain/DTO classes or to a collection:

* **LinkableObject** - which accepts an Object instance (generic) in its constructor. You can attached an arbitrary number of links using the addLink() and setLinks() methods.
* **LinkableCollection** - which accepts a Collection (also generic) implementor in its constructor.

Both of these objects implement the *Linkable* interface.  The *Linkable* interface can be implemented in your own domain or DTO classes to facilitate linking in them if you don't want to use the wrapper classes, *LinkableObject* and *LinkableCollection*.

Additionally, there are a couple of helper classes to assist in creating URLs and Links en masse:

* **MapStringFormat** - which will substitute names in a string with provided values (such as an URL).
* **LinkUtils** - which will perform operations such as replacing create a *Collection* of *Link* instances from a list of IDs and an URL, using the substitution capabilities of *MapStringFormat*.  It will also format a single URL replacing parameters in the URL string.
* **RelTypes** - contains constants for REST-related standard Iana.org link-relation types.

Interested in other functionality?  Drop me a line... let's talk!

Maven Usage
===========
Stable:
```xml
		<dependency>
			<groupId>com.strategicgains</groupId>
			<artifactId>HyperExpress</artifactId>
			<version>1.0.0</version>
		</dependency>
```
Development:
```xml
		<dependency>
			<groupId>com.strategicgains</groupId>
			<artifactId>HyperExpress</artifactId>
			<version>1.0.1-SNAPSHOT</version>
		</dependency>
```
Or download the jar directly from: 
http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22HyperExpress%22

Note that to use the SNAPSHOT version, you must enable snapshots and a repository in your pom file as follows:
```xml
  <profiles>
    <profile>
       <id>allow-snapshots</id>
          <activation><activeByDefault>true</activeByDefault></activation>
       <repositories>
         <repository>
           <id>snapshots-repo</id>
           <url>https://oss.sonatype.org/content/repositories/snapshots</url>
           <releases><enabled>false</enabled></releases>
           <snapshots><enabled>true</enabled></snapshots>
         </repository>
       </repositories>
     </profile>
  </profiles>
```