package com.strategicgains.hyperexpress.builder.test;

import java.util.Arrays;
import java.util.List;

import com.strategicgains.hyperexpress.RelTypes;
import com.strategicgains.hyperexpress.builder.DefaultLinkBuilder;
import com.strategicgains.hyperexpress.builder.DefaultTokenResolver;
import com.strategicgains.hyperexpress.builder.LinkBuilder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.hyperexpress.domain.Link;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DefaultLinkBuilderTest {
    private static final String BASE_URL = "http://localhost:8081";
    private static final String URL_PATTERN = "/{id}";
    private static final String URL_PATTERN2 = "/{rootId}/{secondaryId}/{id}";

    @Test
    public void shouldBuildSimpleSingleIdTemplate() {
        Link link =
            new DefaultLinkBuilder(URL_PATTERN).baseUrl(BASE_URL)
            .rel(RelTypes.SELF)
            .build(new DefaultTokenResolver().bind("id", "42"));

        assertEquals(BASE_URL + "/42", link.getHref());
        assertEquals(RelTypes.SELF, link.getRel());
    }

    @Test
    public void shouldBuildComplexSingleIdTemplate() {
        TokenResolver r =
            new DefaultTokenResolver().bind("rootId", "first")
            .bind("secondaryId", "second")
            .bind("id", "42")
            .bind("ignored", "ignored");

        Link link = new DefaultLinkBuilder(URL_PATTERN2).baseUrl(BASE_URL).rel(RelTypes.DESCRIBED_BY).build(r);

        assertEquals(BASE_URL + "/first/second/42", link.getHref());
        assertEquals(RelTypes.DESCRIBED_BY, link.getRel());
    }

    @Test
    public void shouldBuildComplexQueryString() {
        String expectedUrl = "http://someserver/myapp/report/1234?accountId=400&accountId=401&accountId=402";

        //--- the list of IDs would be variable...
        List<String> accountIds = Arrays.asList("400", "401", "402");

        LinkBuilder lb =
            new DefaultLinkBuilder("/myapp/report/{reportId}").baseUrl("http://someserver")
            .withQuery("accountId={accountId}");
        TokenResolver r = new DefaultTokenResolver().bind("reportId", "1234").bind("accountId", accountIds);

        Link link = lb.build(r);
        assertEquals(expectedUrl, link.getHref());
    }

    @Test
    public void shouldBuildComplexQueryStringFromArray() {
        String expectedUrl = "http://someserver/myapp/report/1234?accountId=400&accountId=401&accountId=402";

        LinkBuilder lb =
            new DefaultLinkBuilder("/myapp/report/{reportId}").baseUrl("http://someserver")
            .withQuery("accountId={accountId}");
        TokenResolver r = new DefaultTokenResolver().bind("reportId", "1234").bind("accountId", "400", "401", "402");

        Link link = lb.build(r);
        assertEquals(expectedUrl, link.getHref());
    }

    @Test
    public void shouldBuildQueryStringWithNoResolver() {
        String expectedUrl = "http://someserver/myapp/report/1234?param1=val1";

        Link link =
            new DefaultLinkBuilder("/myapp/report/1234").baseUrl("http://someserver").withQuery("param1=val1").build();

        assertEquals(expectedUrl, link.getHref());
    }

    @Test
    public void shouldAllowMissingRel() {
        new DefaultLinkBuilder(URL_PATTERN).build(new DefaultTokenResolver());
    }
}
