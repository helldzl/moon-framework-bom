package org.apache.http.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.moonframework.crawler.storage.Field;
import org.moonframework.crawler.storage.Node;
import org.moonframework.crawler.storage.NodeType;
import org.moonframework.crawler.storage.WebPage;
import org.moonframework.crawler.util.ObjectMapperFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/22
 */
public class ProductsNode2Tests {

    /**
     * <p>http://www.guitarcenter.com</p>
     *
     * @throws JsonProcessingException
     */
    public void a() throws JsonProcessingException {
        // 第一级节点   ul li.mm-item      ul li.mm-item:first-child
        Node node = new Node();
        node.setNodeType(NodeType.LINKS);
        node.setCssQuery("ul li.mm-item:first-child");

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a:first-child");
        link.setIndex(0);
        link.setAttr("href");
        node.setFields(Arrays.asList(link));

        // 第二级节点, page 节点
        Node second = new Node();
        second.setNodeType(NodeType.LINKS);
        second.setCssQuery("div ol.products li");

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("div.productTitle a");
        link.setIndex(0);
        link.setAttr("href");

        Field image = new Field("", Field.FieldType.ELEMENT);
        image.setCssQuery("div.thumb img");

        second.setFields(Arrays.asList(link, image));

        // page next 节点
        Node second2 = new Node();
        second2.setNodeType(NodeType.LINKS);
        second2.setCssQuery("div.searchPagination");

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a.pageNext");
        link.setIndex(0);
        link.setAttr("href");
        second2.setFields(Arrays.asList(link));

        node.setNext(Arrays.asList(second, second2));

        // 第三级节点
        Node third = new Node();
        second.setNext(Arrays.asList(third));
        third.setNodeType(NodeType.CONTENT);
        third.setCssQuery("body");
        third.setClassName("com.budee.crawler.domain.Products");

        // name
        Field f1 = new Field("name", Field.FieldType.TEXT);
        f1.setCssQuery("div[itemprop=name]");
        f1.setIndex(0);
        f1.setFormat(true);

        // description
        Field f2 = new Field("category", Field.FieldType.TEXT);
        f2.setCssQuery("li a.category");
        f2.setIndex(-1);
        f2.setDelimiter(",");

        // content
        Field f3 = new Field("content", Field.FieldType.HTML);
        f3.setCssQuery("#productInfo");
        f3.setIndex(-1);
        f3.setFormat(true);

        // price unit
        Field f4 = new Field("priceUnit", Field.FieldType.TEXT);
        f4.setCssQuery("span.topAlignedPrice sup:first-child");

        // price prefix
        Field f5 = new Field("price", Field.FieldType.DECIMAL);
        f5.setCssQuery("span.topAlignedPrice");
        f5.setRegex("(\\.|,| |[A-Z]|(-.*))");
        f5.setCoefficient("0.0001");

        third.setFields(Arrays.asList(f1, f2, f3, f4, f5));

        //
        WebPage page = new WebPage();
        page.setNodes(Arrays.asList(node));
        String s = ObjectMapperFactory.writeValueAsString(page);
        System.out.println(s);

        try {
            WebPage r = ObjectMapperFactory.readValue(s, WebPage.class);
            System.out.println(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ProductsNode2Tests t = new ProductsNode2Tests();
        t.a();
    }

}
