package com.strategicgains.hyperexpress.builder;

import static com.strategicgains.hyperexpress.RelTypes.SELF;
import static com.strategicgains.hyperexpress.RelTypes.UP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.strategicgains.hyperexpress.builder.LinkResolver;
import com.strategicgains.hyperexpress.builder.RelationshipDefinition;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.hyperexpress.domain.Blog;
import com.strategicgains.hyperexpress.domain.Comment;
import com.strategicgains.hyperexpress.domain.Entry;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.Namespace;

public class RelationshipDefinitionTest
{
	@Test
	public void testWithNamespaces()
	throws Exception
	{
		RelationshipDefinition rdef = new RelationshipDefinition()
			.addNamespaces(
				new Namespace("ea", "http://namespaces.example.com/{rel}"),
				new Namespace("blog", "http://namespaces.example.com/{rel}")
			)

			.forCollectionOf(Blog.class)
				.rel(SELF, "/blogs")

			.forClass(Blog.class)
				.rel("ea:author", "/pi/users/{userId}")
				.rel("blog:entries", "/blogs/{blogId}/entries")
				.rel("self", "/blogs/{blogId}")

			.forCollectionOf(Entry.class)
				.rel(SELF, "/blogs/{blogId}/entries")
				.rel(UP, "/blogs/{blogId}")

			.forClass(Entry.class)
				.rel(SELF, "/blogs/{blogId}/entries/{entryId}")
				.rel("blog:comments", "/blog/{blogId}/entries/{entryId}/comments")
				.rel(UP, "/blogs/{blogId}/entries")

			.forCollectionOf(Comment.class)
				.rel(SELF, "/blogs/{blogId}/entries/{entryId}/comments")
				.rel(UP, "/blogs/{blogId}/entries/{entryId}")
					.title("The parent blog entry")

			.forClass(Comment.class)
				.rel(SELF, "/blogs/{blogId}/entries/{entryId}/comments/{commentId}")
					.title("This very comment")
				.rel(UP, "/blogs/{blogId}/entries/{entryId}")
					.title("The parent blog entry")
				.rel("ea:author", "/pi/users/{userId}")
					.title("The comment author");

		LinkResolver resolver = rdef.createResolver();
		TokenResolver ids = new TokenResolver()
			.bindToken("blogId", "1234")
			.bindToken("entryId", "5678")
			.bindToken("commentId", "0987")
			.bindToken("userId", "7654");

		verifyNamespacesExist(resolver.getNamespaces());

		List<Link> links = resolver.resolve(Blog.class, ids);
		assertNotNull(links);
		assertEquals(3, links.size());

		links = resolver.resolve(new Blog[0], ids);
		assertNotNull(links);
		assertEquals(1, links.size());
		assertEquals(SELF, links.get(0).getRel());
		assertEquals("/blogs", links.get(0).getHref());

//		List<Blog> c = new ArrayList<Blog>();
//		c.add(new Blog());
//		links = resolver.resolve(c);
//		assertNotNull(links);
//		assertEquals(1, links.size());
//		assertEquals(SELF, links.get(0).getRel());
//		assertEquals("/blogs", links.get(0).getHref());

		links = resolver.resolve(Entry.class, ids);
		assertNotNull(links);
		assertEquals(3, links.size());

//		links = resolver.resolve(new ArrayList<Entry>());
//		assertNotNull(links);
//		assertEquals(2, links.size());

		links = resolver.resolve(Comment.class, ids);
		assertNotNull(links);
		assertEquals(3, links.size());

//		links = resolver.resolve(new ArrayList<Comment>());
//		assertNotNull(links);
//		assertEquals(3, links.size());
	}

	private void verifyNamespacesExist(Collection<Namespace> namespaces)
	throws Exception
    {
		assertNotNull(namespaces);
		assertEquals(2, namespaces.size());

		boolean eaChecked = false;
		boolean blogChecked = false;

		for (Namespace namespace : namespaces)
		{
			if ("ea".equals(namespace.name()))
			{
				if (eaChecked) throw new IllegalStateException("Namespace 'ea' already checked.");
				eaChecked = true;
				assertEquals("http://namespaces.example.com/{rel}", namespace.href());
			}

			if ("blog".equals(namespace.name()))
			{
				if (blogChecked) throw new IllegalStateException("Namespace 'blog' already checked.");
				blogChecked = true;
				assertEquals("http://namespaces.example.com/{rel}", namespace.href());
			}
		}

		if (!eaChecked) throw new IllegalStateException("Namespace 'ea' not in returned namespaces");
		if (!blogChecked) throw new IllegalStateException("Namespace 'blog' not in returned namespaces");
    }

	@Test
	public void testWithoutNamespaces()
	{
		RelationshipDefinition rdef = new RelationshipDefinition()
			.forCollectionOf(Blog.class)
				.rel(SELF, "/blogs")
	
			.forClass(Blog.class)
				.rel("ea:author", "/pi/users/{userId}")
				.rel("blog:entries", "/blogs/{blogId}/entries")
				.rel("self", "/blogs/{blogId}")
	
			.forCollectionOf(Entry.class)
				.rel(SELF, "/blogs/{blogId}/entries")
				.rel(UP, "/blogs/{blogId}")
	
			.forClass(Entry.class)
				.rel(SELF, "/blogs/{blogId}/entries/{entryId}")
				.rel("blog:comments", "/blog/{blogId}/entries/{entryId}/comments")
				.rel(UP, "/blogs/{blogId}/entries")
	
			.forCollectionOf(Comment.class)
				.rel(SELF, "/blogs/{blogId}/entries/{entryId}/comments")
				.rel(UP, "/blogs/{blogId}/entries/{entryId}")
					.title("The parent blog entry")
	
			.forClass(Comment.class)
				.rel(SELF, "/blogs/{blogId}/entries/{entryId}/comments/{commentId}")
				.rel(UP, "/blogs/{blogId}/entries/{entryId}")
					.title("The parent blog entry")
				.rel("ea:author", "/pi/users/{userId}");

		LinkResolver resolver = rdef.createResolver();
		TokenResolver ids = new TokenResolver()
			.bindToken("blogId", "1234")
			.bindToken("entryId", "5678")
			.bindToken("commentId", "0987")
			.bindToken("userId", "7654");

		List<Link> links = resolver.resolve(Blog.class, ids);
		assertNotNull(links);
		assertEquals(3, links.size());

		links = resolver.resolve(Entry.class, ids);
		assertNotNull(links);
		assertEquals(3, links.size());

		links = resolver.resolve(Comment.class, ids);
		assertNotNull(links);
		assertEquals(3, links.size());
	}
}
