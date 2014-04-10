package com.strategicgains.hyperexpress.fluent;

import static com.strategicgains.hyperexpress.RelTypes.SELF;
import static com.strategicgains.hyperexpress.RelTypes.UP;
import static com.strategicgains.hyperexpress.fluent.Relationships.namespace;
import static com.strategicgains.hyperexpress.fluent.Relationships.resolve;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RelationshipsTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Test
	public void testWithNamespaces()
	{
		Relationships
			.namespaces(
				namespace("ea", "http://namespaces.example.com/{rel}"),
				namespace("blog", "http://namespaces.example.com/{rel}")
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
				.rel(UP, "/blogs/{blogId}/entries/{entryId}")
					.title("The parent blog entry")
				.rel("ea:author", "/pi/users/{userId}");
	}

	@Test
	public void testWithoutNamespaces()
	{
		Relationships
			.forClass(Blog.class)
				.rel("ea:author", "/pi/users/{userId}",
					resolve().parameter("userId", "blog.ownerId"))
				.rel("blog:children", "/blogs/{blogId}/entries",
					resolve().parameter("blogId", "blog.id"))
				.rel("self", "/blogs/{blogId}",
					resolve().parameter("blogId", "blog.id"))

			.forClass(Entry.class)
				.rel("self", "/blogs/{blogId}/entries/{entryId}",
					resolve()
						.parameter("blogId", "entry.blogId")
						.parameter("entryId", "entry.id"));
	}

	public void upsideDownTest()
	{
	}
}
