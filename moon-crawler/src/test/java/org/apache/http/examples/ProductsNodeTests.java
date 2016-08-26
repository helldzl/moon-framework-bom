package org.apache.http.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.moonframework.crawler.storage.Field;
import org.moonframework.crawler.storage.Node;
import org.moonframework.crawler.storage.NodeType;
import org.moonframework.crawler.storage.WebPage;
import org.moonframework.crawler.util.ObjectMapperFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/21
 */
public class ProductsNodeTests {

    public static void m(String url) throws IOException {
        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        Document document2 = Jsoup.parseBodyFragment(document.toString());

        Elements select = document2.select("#productGridWrap ul li a");
        // Elements select = document2.select("h1[itemprop=name]");
        // Elements select = document2.select("div.sd-webtext, div.sd-specs, div.sd-product-gallery");

        // System.out.println(document2);
        System.out.println(select);
        System.out.println();
    }

    public void a() throws JsonProcessingException {
        // 第一级节点
        Node node = new Node();
        // #manuList div:nth-child(2) li a       #productGridWrap ul li
        node.setCssQuery("#manuList div:nth-child(2) li");
        node.setNodeType(NodeType.LINKS);

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setIndex(0);
        link.setAttr("href");

        Field category = new Field("category", Field.FieldType.TEXT);
        category.setCssQuery("a");
        node.setFields(Arrays.asList(link, category));


        // 第二级节点, page 节点
        Node second = new Node();
        second.setCssQuery("ul#productGrid li");
        second.setNodeType(NodeType.LINKS);

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("h5 a.item-url");
        link.setIndex(0);
        link.setAttr("href");
        second.setFields(Arrays.asList(link));

        //
        Node second2 = new Node();
        second2.setCssQuery("div:first-child ul.paging li.next");
        second2.setNodeType(NodeType.LINKS);

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setIndex(0);
        link.setAttr("href");
        second2.setFields(Arrays.asList(link));

        node.setNext(Arrays.asList(second, second2));

        // 第三级节点
        Node third = new Node();
        second.setNext(Arrays.asList(third));
        third.setCssQuery("body");
        third.setNodeType(NodeType.CONTENT);
        third.setClassName("com.budee.crawler.domain.Products");

        // name
        Field f1 = new Field("name", Field.FieldType.TEXT);
        f1.setCssQuery("h1[itemprop=name]");
        f1.setIndex(0);

        // description
        Field f2 = new Field("description", Field.FieldType.TEXT);
        f2.setCssQuery("div[itemprop=description]");

        // content
        Field f3 = new Field("content", Field.FieldType.HTML);
        f3.setCssQuery("div.sd-webtext, div.sd-specs, div.sd-product-gallery");
        f3.setFormat(true);
        f3.setIndex(-1);

        Field f3_2 = new Field("tag", Field.FieldType.TEXT);
        f3_2.setCssQuery("a[itemprop=item]");
        f3_2.setDelimiter(", ");
        f3_2.setIndex(-1);

        // price unit
        Field f4 = new Field("priceUnit", Field.FieldType.TEXT);
        f4.setCssQuery("span.amount span b");

        // price prefix
        Field f5 = new Field("price", Field.FieldType.DECIMAL);
        f5.setCssQuery("span.amount span");
        f5.setRegex("\\$");

//        Field f6 = new Field("price", Field.FieldType.DECIMAL);
//        f6.setCssQuery("span.amount span b");
//        f6.setIndex(1);
//        f6.setRegex("\\.(\\d)+");

        third.setFields(Arrays.asList(f1, f2, f3, f3_2, f4, f5));

        WebPage page = new WebPage();
        page.setNodes(Arrays.asList(node));
        String s = ObjectMapperFactory.writeValueAsString(page);
        try {
            WebPage r = ObjectMapperFactory.readValue(s, WebPage.class);
            System.out.println(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }

    public void c() {
        Pattern compile = Pattern.compile("\\.(\\d)+");
        Matcher matcher = compile.matcher("'.987kkdf876'");
        // boolean b = matcher.find();
        // String group = matcher.group();
        // System.out.println(b);
        // System.out.println(matcher.group(1));
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    public static void main(String[] args) throws Exception {
        //m("http://www.sweetwater.com/store/manufacturer/all");
        // m("http://www.sweetwater.com/store/detail/P1card");
        ProductsNodeTests t = new ProductsNodeTests();
        t.a();
    }
}
