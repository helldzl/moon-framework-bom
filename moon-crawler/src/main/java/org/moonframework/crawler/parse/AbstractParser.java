package org.moonframework.crawler.parse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.moonframework.crawler.storage.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/16
 */
public abstract class AbstractParser implements Parser<WebPage, ParseResult> {

    protected static Logger logger = LogManager.getLogger(AbstractParser.class);

    /**
     * 原始URL链接
     */
    public static final String ORIGIN = "origin";

    /**
     * 根据该属性指向的链接, 加入link queue队列中给fetcher使用
     */
    public static final String LINK = "link";

    /**
     * img标签的css query
     */
    public static final String SELECTOR_IMG = "img[src]";

    /**
     * a标签的图片类型链接的css query
     */
    public static final String SELECTOR_LINK = "a[href~=(?i)\\.(gif|png|jpe?g)]";

    /**
     * <p>core method</p>
     *
     * @param page page
     * @return ParseResult 解析结果
     */
    @Override
    public ParseResult apply(WebPage page) {
        Document document = optimize(Jsoup.parse(page.getHtml()), page);
        ParseResult parseResult = new ParseResult();
        parseResult.setDocument(document);
        page.getNodes().forEach(node -> node(parseResult, document, page, node));
        return parseResult;
    }

    /**
     * @param parseResult parseResult
     * @param document    document
     * @param page        page
     * @param node        node
     */
    protected void node(ParseResult parseResult, Document document, WebPage page, Node node) {
        List<PageSegment> segments = new ArrayList<>();
        parseResult.put(node, segments);

        Elements elements = document.select(node.getCssQuery());
        List<Field> fields = node.getFields();
        for (Element element : elements) {
            PageSegment segment = new PageSegment();
            preProcess(page, segment);
            for (Field field : fields) {
                String cssQuery = field.getCssQuery();
                if (cssQuery == null)
                    continue;

                Elements select = element.select(cssQuery);
                if (select.isEmpty())
                    continue;

                if (field.getIndex() >= 0 && (select.size() <= field.getIndex()))
                    continue;

                // lifecycle method
                Object obj = field.getType().get(service(segment, select, field), field);
                if (obj != null)
                    segment.merge(field.getName(), obj);
            }

            // add to list if not empty
            if (!segment.isEmpty()) {
                // post
                postProcess(page, segment);
                segment.put(ORIGIN, page.getUri().toString());
                segments.add(segment);
                logger.debug(segment::getData);
            }
        }
    }

    /**
     * <p>前置处理器</p>
     *
     * @param page    page
     * @param segment segment
     */
    protected void preProcess(WebPage page, PageSegment segment) {
        if (page.getMedias() != null) {
            segment.getMedias().putAll(page.getMedias());
        }
    }

    /**
     * <p>后置处理器</p>
     *
     * @param page    page
     * @param segment segment
     */
    protected void postProcess(WebPage page, PageSegment segment) {
        if (page.getData() != null) {
            segment.putAll(page.getData());
        }
    }

    /**
     * <p>优化文档结构, 将相对链接补全为绝对链接</p>
     *
     * @param document document
     * @param page     page
     * @return Document
     */
    protected Document optimize(Document document, WebPage page) {
        String uri = page.getUri().toString();
        String baseUri = page.getHost();

        // 链接补全:优化a标签
        optimize(document.select("a[href]"), uri, baseUri, "href");

        // 延迟加载图片文档优化
        Elements elements = document.select("img[src]");
        elements.stream()
                .filter(element -> element.hasAttr("data-original") && !"".equals(element.attr("data-original")))
                .forEach(element -> element.attr("src", element.attr("data-original")));

        // 链接补全:优化img标签
        optimize(elements, uri, baseUri, "src");
        return document;
    }

    /**
     * <p>优化文档结构, 将相对链接补全为绝对链接</p>
     *
     * @param elements elements
     * @param baseUri  baseUri
     * @param attr     attr
     */
    protected void optimize(Elements elements, String uri, String baseUri, String attr) {
        elements.stream().forEach(element -> {
            String s = element.attr(attr).replaceAll("(\\.\\./|\\./)", "/");
            if (s.startsWith("#")) {
                element.attr(attr, uri + s);
            } else if ("/".equals(s)) {
                element.attr(attr, baseUri);
            } else if (s.matches("/[^/].*") && baseUri != null) {
                element.attr(attr, baseUri + s);
            } else if (s.matches("//.*")) {
                element.attr(attr, "http:" + s);
            } else if (!s.matches("(?i)http.*")) {
                element.attr(attr, baseUri + "/" + s);
            }
        });
    }

    /**
     * <p>处理HTML或ELEMENT类型中的图片、音频、视频等多媒体格式数据</p>
     *
     * @param segment  segment
     * @param elements elements
     * @param field    field
     */
    protected Elements service(PageSegment segment, Elements elements, Field field) {
        int index = field.getIndex();
        if (Field.FieldType.HTML == field.getType() || Field.FieldType.ELEMENT == field.getType()) {
            Elements clone = elements.clone();

            image(segment, clone, index);
            imageLink(segment, clone, index);
            video(segment, clone, index);
            audio(segment, clone, index);
            attachment(segment, clone, index);
            return clone;
        }
        return elements;
    }

    // lifecycle template method

    /**
     * <p>图片</p>
     *
     * @param segment  segment
     * @param elements elements
     * @param index    index
     */
    protected void image(PageSegment segment, Elements elements, int index) {
        addAll(segment, Media.IMAGE, elements, index, SELECTOR_IMG);
    }

    /**
     * <p>图片</p>
     *
     * @param segment  segment
     * @param elements elements
     * @param index    index
     */
    protected void imageLink(PageSegment segment, Elements elements, int index) {
        addAll(segment, Media.IMAGE_LINK, elements, index, SELECTOR_LINK);
    }

    /**
     * <p>视频</p>
     * <p>object[type=application/x-shockwave-flash]</p>
     *
     * @param segment  segment
     * @param elements elements
     * @param index    index
     */
    protected void video(PageSegment segment, Elements elements, int index) {

    }

    /**
     * <p>音频</p>
     *
     * @param segment  segment
     * @param elements elements
     * @param index    index
     */
    protected void audio(PageSegment segment, Elements elements, int index) {

    }

    /**
     * <p>附件: doc, pdf</p>
     *
     * @param segment  segment
     * @param elements elements
     * @param index    index
     */
    protected void attachment(PageSegment segment, Elements elements, int index) {

    }

    //

    protected boolean addAll(PageSegment segment, Media media, Elements elements, int index, String query) {
        if (index < 0) {
            return addAll(segment, media, elements.select(query));
        } else {
            return addAll(segment, media, elements.get(index).select(query));
        }
    }

    protected boolean addAll(PageSegment segment, Media media, Elements elements) {
        replacement(elements);
        return segment.addAll(media, elements);
    }

//    protected boolean add(PageSegment segment, Media media, Element element) {
//        return segment.add(media, element);
//    }

    // hook method

    /**
     * <p>属性替换</p>
     *
     * @param elements
     */
    protected void replacement(Elements elements) {

    }

}
