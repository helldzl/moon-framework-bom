package org.apache.http.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.moonframework.crawler.storage.Field;
import org.moonframework.crawler.storage.Node;
import org.moonframework.crawler.storage.NodeType;
import org.moonframework.crawler.storage.WebPage;
import org.moonframework.crawler.util.ObjectMapperFactory;

import java.util.Arrays;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/28
 */
public class NodeBuildTests {


    /**
     * https://www.thomann.de
     *
     * @throws Exception
     */
    @Test
    public void test9() throws Exception {
        // test config : div.lr-cat-brands-manufacturers-wrapper:nth-child(1) ul.lr-cat-brands-manufacturers-list a

        // 第一级节点
        // [PRODUCTION]
        Node level_1 = new Node();
        level_1.setNodeType(NodeType.LINKS);
        level_1.setCssQuery("ul.lr-cat-brands-manufacturers-list a");
        // link
        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setAttr("href");
        // brand manufactures
        Field brand = new Field("brand", Field.FieldType.TEXT);
        brand.setCssQuery("a");
        // add to field
        level_1.setFields(Arrays.asList(link, brand));

        // 第二级节点:list节点
        Node level_2 = new Node();
        level_2.setNodeType(NodeType.LINKS);
        level_2.setCssQuery("#defaultResultPage>div");
        // link
        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a.lr-articlelist-article-articleLink");
        link.setIndex(0);
        link.setAttr("href");
        // image
        Field image = new Field("", Field.FieldType.ELEMENT);
        image.setCssQuery(".article-img");
        // desc
        Field description = new Field("description", Field.FieldType.TEXT);
        description.setCssQuery(".article-description");
        // price unit
        Field priceUnit = new Field("priceUnit", Field.FieldType.TEXT);
        priceUnit.setCssQuery(".article-price-primary");
        priceUnit.setRegex("(\\d|\\.|,)");
        // price €1,099
        Field price = new Field("price", Field.FieldType.DECIMAL);
        price.setCssQuery(".article-price-primary");
        price.setRegex("(\\€|,)");
        // add to field
        level_2.setFields(Arrays.asList(link, image, description, priceUnit, price));

        // 第二级节点:next page节点
        Node level_2_1 = new Node();
        level_2_1.setNodeType(NodeType.LINKS);
        level_2_1.setCssQuery("a.lr-search-result-pagination-button");
        // link
        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setIndex(0);
        link.setAttr("href");
        level_2_1.setFields(Arrays.asList(link));

        // 第二级节点:forward 跳转节点
        Node forward = new Node();
        forward.setNodeType(NodeType.LINKS);
        forward.setCssQuery(".lr-manufacturer-header-text a");
        // link
        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setAttr("href");
        forward.setFields(Arrays.asList(link));

        // 第三级节点
        Node level_3 = new Node();
        // content
        level_3.setNodeType(NodeType.CONTENT);
        level_3.setCssQuery("body");
        level_3.setClassName("com.budee.crawler.domain.Products");
        // name
        Field name = new Field("name", Field.FieldType.TEXT);
        name.setCssQuery("[itemprop=name]");
        // content
        Field content = new Field("content", Field.FieldType.HTML);
        content.setCssQuery(".lr-prod-text");
        content.setIndex(-1);
        content.setFormat(true);
        // category
        Field category = new Field("category", Field.FieldType.TEXT);
        category.setCssQuery("ul.lr-breadcrumb-stage-list li:nth-child(n+4):not(li.lr-breadcrumb-stage-last)");
        category.setIndex(-1);
        category.setDelimiter(",");
        // element
        Field element = new Field("", Field.FieldType.ELEMENT);
        element.setCssQuery("[data-media-type]:not([data-media-type=ZOOMIMAGE],[data-media-type=VIDIOTVIDEO])");   // [data-media-type]:not([data-media-type=ZOOMIMAGE],[data-media-type=VIDIOTVIDEO])
        element.setIndex(-1);
        // add to field
        level_3.setFields(Arrays.asList(name, category, content, element));

        // config
        forward.setNext(Arrays.asList(level_2, level_2_1));
        level_1.setNext(Arrays.asList(forward, level_2, level_2_1));
        level_2.setNext(Arrays.asList(level_3));

        print(level_1);
    }

    /**
     * http://www.l-acoustics.com/
     */
    @Test
    public void test8() {
        // 第一级节点
        // [PRODUCTION]
        Node level_1 = new Node();
        level_1.setNodeType(NodeType.LINKS);
        level_1.setCssQuery("a.product_link");

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setAttr("href");

        Field name = new Field("name", Field.FieldType.TEXT);
        name.setCssQuery("span");

        level_1.setFields(Arrays.asList(name, link));

        // 第二级节点
        Node level_2 = new Node();
        level_2.setNodeType(NodeType.CONTENT);
        level_2.setCssQuery("body");
        level_2.setClassName("com.budee.crawler.domain.Products");

        Field description = new Field("description", Field.FieldType.TEXT);
        description.setCssQuery("[itemprop=description]");
        description.setIndex(-1);

        Field content = new Field("content", Field.FieldType.HTML);
        content.setCssQuery(".contentInfos");
        content.setIndex(-1);
        content.setFormat(true);

        Field element = new Field("", Field.FieldType.ELEMENT);
        element.setCssQuery("[itemprop=image]");
        element.setIndex(-1);

        level_2.setFields(Arrays.asList(description, content, element));
        level_1.setNext(Arrays.asList(level_2));

        print(level_1);
    }

    /**
     * http://www.tannoypro.com/products/archive
     */
    @Test
    public void test7() {
        // 第一级节点
        // [PRODUCTION]
        Node level_1 = new Node();
        level_1.setNodeType(NodeType.LINKS);
        level_1.setCssQuery(".filteredItem a");

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setAttr("href");

        Field image = new Field("", Field.FieldType.ELEMENT);
        image.setCssQuery("img");

        level_1.setFields(Arrays.asList(link, image));

        // 第二级节点
        Node level_2 = new Node();
        level_2.setNodeType(NodeType.CONTENT);
        level_2.setCssQuery("body");
        level_2.setClassName("com.budee.crawler.domain.Products");

        Field name = new Field("name", Field.FieldType.TEXT);
        name.setCssQuery(".titleText");

        Field category = new Field("category", Field.FieldType.TEXT);
        // category.setCssQuery(".aL a:nth-child(n+3)");
        category.setCssQuery(".aL a:nth-child(4)");
        // category.setIndex(-1);
        // category.setDelimiter(",");
        category.setRegex("/");

        Field content = new Field("content", Field.FieldType.HTML);
        content.setCssQuery(".modelDesc");
        content.setIndex(-1);
        content.setFormat(true);

        level_2.setFields(Arrays.asList(name, category, content));
        level_1.setNext(Arrays.asList(level_2));

        print(level_1);
    }

    /**
     * http://www.jblpro.com/www/products
     */
    @Test
    public void test6() {
        // 第一级节点
        // [PRODUCTION]
        Node level_1 = new Node();
        level_1.setNodeType(NodeType.LINKS);
        level_1.setCssQuery("div.FamilyList ul li a[href~=/products]");

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setAttr("href");

        level_1.setFields(Arrays.asList(link));

        // 第二级节点
        Node level_2 = new Node();
        level_2.setNodeType(NodeType.LINKS);
        level_2.setCssQuery(".FamilyProductWrapper");

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("#ProductNameName a");
        link.setAttr("href");

        Field description = new Field("description", Field.FieldType.TEXT);
        description.setCssQuery("#ProductTitle");

        level_2.setFields(Arrays.asList(link, description));
        level_1.setNext(Arrays.asList(level_2));

        // 第三级节点
        Node level_3 = new Node();
        level_2.setNext(Arrays.asList(level_3));
        level_3.setNodeType(NodeType.CONTENT);
        level_3.setCssQuery("body");
        level_3.setClassName("com.budee.crawler.domain.Products");

        // name
        Field name = new Field("name", Field.FieldType.TEXT);
        name.setCssQuery("div.ProductPageTitle");

        // description
        Field category = new Field("category", Field.FieldType.TEXT);
        category.setCssQuery("ul.rsmFlow>li.sfBreadcrumbNavigation:nth-child(n+3) a");
        category.setIndex(-1);
        category.setDelimiter(",");

        // content
        Field content = new Field("content", Field.FieldType.HTML);
        content.setCssQuery(".OverviewSection,.FeaturedContent,div+style+.SectionContentNormal");
        content.setIndex(-1);
        content.setFormat(true);

        // element
        Field element = new Field("", Field.FieldType.ELEMENT);
        element.setCssQuery(".cloudzoomthumbs img,img.cloudzoom");
        element.setIndex(-1);

        level_3.setFields(Arrays.asList(name, category, content, element));
        print(level_1);
    }

    /**
     * http://eaw.com/
     */
    @Test
    public void test5() {
        // 第一级节点
        // [PRODUCTION]
        Node level_1 = new Node();
        level_1.setNodeType(NodeType.LINKS);
        level_1.setCssQuery("ul#menu-main-menu>li:nth-child(2) a[href~=http://eaw.com/products/]:not(a[href=http://eaw.com/products/])");

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setAttr("href");

        level_1.setFields(Arrays.asList(link));

        // 第二级节点
        Node level_2 = new Node();
        level_2.setNodeType(NodeType.LINKS);
        level_2.setCssQuery("article");

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("h5.portfolio_title a");
        link.setAttr("href");

        Field category = new Field("category", Field.FieldType.TEXT);
        category.setCssQuery(".project_category");

        Field image = new Field("", Field.FieldType.ELEMENT);
        image.setCssQuery("img");

        level_2.setFields(Arrays.asList(link, category, image));
        level_1.setNext(Arrays.asList(level_2));

        // 第三级节点
        Node level_3 = new Node();
        level_2.setNext(Arrays.asList(level_3));
        level_3.setNodeType(NodeType.CONTENT);
        level_3.setCssQuery("body");
        level_3.setClassName("com.budee.crawler.domain.Products");

        // name
        Field name = new Field("name", Field.FieldType.TEXT);
        name.setCssQuery("div.title_holder h1");

        // description
        Field description = new Field("description", Field.FieldType.TEXT);
        description.setCssQuery("div.title_holder h6");

        // content
        Field content = new Field("content", Field.FieldType.HTML);
        content.setCssQuery(".tabs-container .tab-content");
        content.setIndex(-1);
        content.setFormat(true);

        // element
        Field element = new Field("", Field.FieldType.ELEMENT);
        element.setCssQuery("ul.slides li img");
        element.setIndex(-1);

        level_3.setFields(Arrays.asList(name, description, content, element));
        print(level_1);

    }

    /**
     * https://martin-audio.com
     */
    @Test
    public void test4() {
        // 第一级节点
        // [PRODUCTION]
        Node level_1 = new Node();
        level_1.setNodeType(NodeType.LINKS);
        level_1.setCssQuery(".column a");

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setAttr("href");

        level_1.setFields(Arrays.asList(link));

        // 第二级节点
        Node level_2 = new Node();
        level_2.setNodeType(NodeType.CONTENT);
        level_2.setCssQuery("body");
        level_2.setClassName("com.budee.crawler.domain.Products");


        // name
        Field name = new Field("name", Field.FieldType.TEXT);
        name.setCssQuery("h1.product-header");

        // description
        Field description = new Field("description", Field.FieldType.TEXT);
        description.setCssQuery("h2.product-subhead");

        // content
        Field content = new Field("content", Field.FieldType.HTML);
        content.setCssQuery("div.product-description,div.product-features,div.mamainpageleft>[id~=collapsible]");
        content.setIndex(-1);
        content.setFormat(true);

        // element
        Field element = new Field("", Field.FieldType.ELEMENT);
        element.setCssQuery("#product-main-image a");
        element.setIndex(-1);

        level_2.setFields(Arrays.asList(name, description, content, element));
        level_1.setNext(Arrays.asList(level_2));

        print(level_1);
    }

    /**
     * http://vintageking.com
     */
    @Test
    public void test3() throws JsonProcessingException {
        // 第一级节点
        // [PRODUCTION] ul#nav>li>ul li         a                       0
        // [TEST]       body                    ul#nav>li>ul li a       2
        Node level_1 = new Node();
        level_1.setNodeType(NodeType.LINKS);
        level_1.setCssQuery("ul#nav>li>ul li");

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setAttr("href");

        level_1.setFields(Arrays.asList(link));

        // 第二级节点
        Node level_2_1 = new Node();
        level_2_1.setNodeType(NodeType.LINKS);
        level_2_1.setCssQuery("ul.products-grid");

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery(".product-name a");
        link.setAttr("href");

        Field image = new Field("", Field.FieldType.ELEMENT);
        image.setCssQuery("li img");

        level_2_1.setFields(Arrays.asList(link, image));

        // page next
        Node level_2_2 = new Node();
        level_2_2.setNodeType(NodeType.LINKS);
        level_2_2.setCssQuery("body");

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("ol li a.next");
        link.setAttr("href");
        level_2_2.setFields(Arrays.asList(link));

        level_1.setNext(Arrays.asList(level_2_1, level_2_2));

        //
        // 第三级节点
        Node level_3 = new Node();
        level_2_1.setNext(Arrays.asList(level_3));
        level_3.setNodeType(NodeType.CONTENT);
        level_3.setCssQuery("body");
        level_3.setClassName("com.budee.crawler.domain.Products");

        // name
        Field name = new Field("name", Field.FieldType.TEXT);
        name.setCssQuery("h1[itemprop~=name]");

        // description
        Field description = new Field("description", Field.FieldType.TEXT);
        description.setCssQuery("[itemprop=description]");

        // category
        Field category = new Field("category", Field.FieldType.TEXT);
        category.setCssQuery("li[class~=category]");
        category.setIndex(-1);
        category.setDelimiter(",");
        category.setRegex(" :");

        // content
        Field content = new Field("content", Field.FieldType.HTML);
        content.setCssQuery("ul.contained>li");
        content.setIndex(-1);
        content.setFormat(true);

        // brand
        Field brand = new Field("brand", Field.FieldType.ATTRIBUTE);
        brand.setCssQuery(".brand-link img");
        brand.setAttr("title");

        // price unit
        Field priceUnit = new Field("priceUnit", Field.FieldType.TEXT);
        priceUnit.setCssQuery("div.price-box span.price");
        priceUnit.setRegex("(\\d|\\.|,)");

        // price
        Field price = new Field("price", Field.FieldType.DECIMAL);
        price.setCssQuery("div.price-box span.price");
        price.setRegex("(\\$|,)");

        // element
        Field element = new Field("", Field.FieldType.ELEMENT);
        element.setCssQuery("#thumbnail-list a");
        element.setIndex(-1);
        // TODO set element css query

        level_3.setFields(Arrays.asList(name, description, category, brand, content, priceUnit, price, element));

        print(level_1);
    }

    /**
     * http://www.guitarcenter.com
     */
    @Test
    public void test2() {
        // 第一级节点   ul li.mm-item      ul li.mm-item:first-child
        Node node = new Node();
        node.setNodeType(NodeType.LINKS);
        node.setCssQuery("ul li.mm-item a");

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
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
        print(node);
    }


    /**
     * http://www.sweetwater.com
     */
    @Test
    public void test1() {
        // 第一级节点
        Node node = new Node();
        // #manuList div:nth-child(2) li a       #productGridWrap ul li
        node.setCssQuery("#manuList ul li a");
        node.setNodeType(NodeType.LINKS);

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setIndex(0);
        link.setAttr("href");

        Field brand = new Field("brand", Field.FieldType.TEXT);
        brand.setCssQuery("a");
        brand.setRegex(" \\([\\d|,]+\\)"); //"65amps (123,567)".replaceAll(" \\([\\d|,]+\\)", "")
        node.setFields(Arrays.asList(link, brand));


        // 第二级节点, page 节点
        Node second = new Node();
        second.setCssQuery(".products div[data-itemid]");
        second.setNodeType(NodeType.LINKS);

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery(".product__name a");
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

        Field f3_2 = new Field("category", Field.FieldType.TEXT);
        f3_2.setCssQuery("a[itemprop=item]");
        f3_2.setDelimiter(", ");
        f3_2.setIndex(-1);

        // price unit
        Field f4 = new Field("priceUnit", Field.FieldType.TEXT);
        f4.setCssQuery("span.amount span b");

        // price prefix
        Field f5 = new Field("price", Field.FieldType.DECIMAL);
        f5.setCssQuery("span.amount span");
        f5.setRegex("(\\$|,)");

//        Field f6 = new Field("price", Field.FieldType.DECIMAL);
//        f6.setCssQuery("span.amount span b");
//        f6.setIndex(1);
//        f6.setRegex("\\.(\\d)+");

        third.setFields(Arrays.asList(f1, f2, f3, f3_2, f4, f5));

        print(node);
    }

    /**
     * http://www.music123.com
     */
    @Test
    public void test0() {
        // 第一级节点
        // [PRODUCTION] .select(".shopByBrand div:first-child div.pager ol li").select("a")
        // [TEST] .select(".shopByBrand div:first-child div.pager ol li").select("li:first-child a")
        Node level_1 = new Node();
        level_1.setNodeType(NodeType.LINKS);
        level_1.setCssQuery(".shopByBrand div:first-child div.pager ol li");

        Field link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setAttr("href");

        level_1.setFields(Arrays.asList(link));

        // 第二级节点
        Node level_2 = new Node();
        level_2.setNodeType(NodeType.LINKS);
        level_2.setCssQuery("ul#topBrands li");

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a");
        link.setAttr("href");

        Field brand = new Field("brand", Field.FieldType.TEXT);
        brand.setCssQuery("p>a");

//        Field image = new Field("", Field.FieldType.ELEMENT);
//        image.setCssQuery("a>img");

        level_2.setFields(Arrays.asList(link, brand));
        level_1.setNext(Arrays.asList(level_2));

        // 第三级节点
        Node level_3 = new Node();
        level_3.setNodeType(NodeType.LINKS);
        level_3.setCssQuery(".productGrid ol.products li[class]");
        // level_3.setClassName("com.budee.crawler.domain.Products");

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery(".product strong a");
        link.setAttr("href");

        Field image = new Field("", Field.FieldType.ELEMENT);
        image.setCssQuery("img[data-original]");

        level_3.setFields(Arrays.asList(link, image));

        // page next
        Node page = new Node();
        page.setNodeType(NodeType.LINKS);
        page.setCssQuery("body");

        link = new Field("link", Field.FieldType.ATTRIBUTE);
        link.setCssQuery("a.next_link");
        link.setAttr("href");
        page.setFields(Arrays.asList(link));

        level_2.setNext(Arrays.asList(page, level_3));

        // 节点4
        Node level_4 = new Node();
        level_4.setNodeType(NodeType.CONTENT);
        level_4.setCssQuery("body");
        level_4.setClassName("com.budee.crawler.domain.Products");

        Field category = new Field("category", Field.FieldType.TEXT);
        category.setCssQuery(".breadcrumbs li:nth-child(n+2) a");
        category.setIndex(-1);
        category.setDelimiter(",");

        // name
        Field name = new Field("name", Field.FieldType.TEXT);
        name.setCssQuery("h1.fn");

        // content
        Field content = new Field("content", Field.FieldType.HTML);
        content.setCssQuery(".details");
        content.setFormat(true);

        // element
        Field element = new Field("", Field.FieldType.ELEMENT);
        element.setCssQuery("#productMedia a img");
        element.setIndex(-1);

        // price unit
        Field priceUnit = new Field("priceUnit", Field.FieldType.TEXT);
        priceUnit.setCssQuery("[itemprop=price]");
        priceUnit.setRegex("(\\d|\\.|,)");

        // price
        Field price = new Field("price", Field.FieldType.DECIMAL);
        price.setCssQuery("[itemprop=price]");
        price.setRegex("(\\$|,)");

        level_4.setFields(Arrays.asList(name, category, content, element, price, priceUnit));
        level_3.setNext(Arrays.asList(level_4));
        print(level_1);
    }

    private void print(Node node) {
        try {
            WebPage page = new WebPage();
            page.setNodes(Arrays.asList(node));
            String s = ObjectMapperFactory.writeValueAsString(page);
            WebPage r = ObjectMapperFactory.readValue(s, WebPage.class);
            System.out.println(r);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
