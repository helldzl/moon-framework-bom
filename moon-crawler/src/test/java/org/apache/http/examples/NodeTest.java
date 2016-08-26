package org.apache.http.examples;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.moonframework.crawler.parse.DefaultParser;
import org.moonframework.crawler.parse.ParserAdapter;
import org.moonframework.crawler.storage.Field;
import org.moonframework.crawler.storage.Node;
import org.moonframework.crawler.storage.NodeType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/13
 */
public class NodeTest {

    public void b() {
        int n = Runtime.getRuntime().availableProcessors();
        System.out.println(n);
    }

    public void a() {
        Document doc = Jsoup.parse("<html>");
        System.out.println(doc);
    }

    public void c() throws IOException {
        //
        Document document = Jsoup.connect("http://zx.ingping.com/c_1_8.html").get();
        Elements elements = document.select(".art_four>li");

        List<Field> fields = new ArrayList<>();
        Node parent = new Node();
        parent.setFields(fields);

        Field title = new Field("title");
        title.setCssQuery("a");
        title.setIndex(0);
        title.setAttr("title");
        title.setType(Field.FieldType.ATTRIBUTE);

        Field link = new Field("link");
        link.setCssQuery("a");
        link.setIndex(0);
        link.setAttr("href");
        link.setType(Field.FieldType.ATTRIBUTE);

        Field image = new Field("image");
        image.setCssQuery("img");
        image.setIndex(0);
        image.setAttr("src");
        image.setType(Field.FieldType.ATTRIBUTE);

        Field date = new Field("date");
        date.setCssQuery("div .text-r");
        date.setIndex(0);
        date.setType(Field.FieldType.TEXT);
        date.setDateFormat("yyyy年MM月dd日");

        Field summary = new Field("summary");
        summary.setCssQuery("p.summary");
        summary.setIndex(0);
        summary.setType(Field.FieldType.TEXT);
        summary.setFormat(true);

        fields.add(title);
        fields.add(link);
        fields.add(image);
        fields.add(date);
        // nodes.add(summary);

        fields = new ArrayList<>();
        Node child = new Node();
        child.setCssQuery(".art_four>li");
        child.setFields(fields);
        parent.setNext(Arrays.asList(child));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(parent);

        DefaultParser parser = new ParserAdapter();
        // parser.apply(document.toString(), parent);
    }

    public void d() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // list node
        Node node = parent();
        Node child = child();
        node.setNext(Arrays.asList(child));

        System.out.println(objectMapper.writeValueAsString(node));
        final Node node1 = objectMapper.readValue(objectMapper.writeValueAsString(node), Node.class);
        System.out.println(objectMapper.writeValueAsString(node1));
    }

    private Node parent() {
        List<Field> fields = new ArrayList<>();
        Node parent = new Node();
        parent.setNodeType(NodeType.LINKS);
        parent.setCssQuery(".art_four>li");
        parent.setFields(fields);

        Field title = new Field("title");
        title.setCssQuery("a");
        title.setIndex(0);
        title.setAttr("title");
        title.setType(Field.FieldType.ATTRIBUTE);

        Field link = new Field("link");
        link.setCssQuery("a");
        link.setIndex(0);
        link.setAttr("href");
        link.setType(Field.FieldType.ATTRIBUTE);

        Field image = new Field("image");
        image.setCssQuery("img");
        image.setIndex(0);
        image.setAttr("src");
        image.setType(Field.FieldType.ATTRIBUTE);

        Field date = new Field("date");
        date.setCssQuery("div .text-r");
        date.setIndex(0);
        date.setType(Field.FieldType.TEXT);
        date.setDateFormat("yyyy年MM月dd日");

        Field summary = new Field("summary");
        summary.setCssQuery("p.summary");
        summary.setIndex(0);
        summary.setType(Field.FieldType.TEXT);
        summary.setFormat(true);

        fields.add(title);
        fields.add(link);
        fields.add(image);
        fields.add(date);
        // nodes.add(summary);
        return parent;
    }

    private Node child() {
        List<Field> fields = new ArrayList<>();
        Node node = new Node();
        node.setClassName("com.budee.crawler.domain.Documents");
        node.setNodeType(NodeType.CONTENT);
        node.setCssQuery("div#article-cmt");
        node.setFields(fields);

        Field n1 = new Field("title");
        n1.setCssQuery("h2.title");
        n1.setIndex(0);
        n1.setType(Field.FieldType.TEXT);
        fields.add(n1);

        Field n2 = new Field("time");
        n2.setCssQuery("span.pubTime");
        n2.setIndex(0);
        n2.setType(Field.FieldType.TEXT);
        n2.setDateFormat("yyyy-MM-dd hh:mm");
        fields.add(n2);

        Field n3 = new Field("summary");
        n3.setCssQuery("span.c-333");
        n3.setIndex(0);
        n3.setType(Field.FieldType.TEXT);
        n3.setFormat(true);
        fields.add(n3);

        Field n4 = new Field("text");
        n4.setCssQuery("div#article_text");
        n4.setIndex(0);
        n4.setType(Field.FieldType.HTML);
        n4.setFormat(true);
        fields.add(n4);

        return node;
    }

    public static void main(String[] args) throws Exception {
        NodeTest t = new NodeTest();
        t.d();
    }

}
