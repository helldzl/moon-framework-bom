package org.moonframework.crawler.storage;

import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/24
 */
public class ParseResult {

    /**
     *
     */
    private Document document;

    /**
     *
     */
    private Map<Node, List<PageSegment>> map = new HashMap<>();

    public ParseResult() {
    }

    public List<PageSegment> put(Node node, List<PageSegment> list) {
        return map.put(node, list);
    }

    // get and set method

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Map<Node, List<PageSegment>> getMap() {
        return map;
    }

    public void setMap(Map<Node, List<PageSegment>> map) {
        this.map = map;
    }
}
