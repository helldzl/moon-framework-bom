package org.moonframework.crawler.storage;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/15
 */
public class Node implements Serializable {

    private static final long serialVersionUID = -8307303626942880435L;

    /**
     * 节点类型
     */
    private NodeType nodeType;

    /**
     * class全限定类名称
     */
    private String className;

    /**
     * css选择器
     */
    private String cssQuery;

    /**
     * 字段集合
     */
    private List<Field> fields;

    /**
     * 对下一级节点的描述
     */
    private List<Node> next;

    public Node() {
    }

    public Node(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public boolean hasNext() {
        return !CollectionUtils.isEmpty(next);
    }

    // get and set method

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getCssQuery() {
        return cssQuery;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setCssQuery(String cssQuery) {
        this.cssQuery = cssQuery;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Node> getNext() {
        return next;
    }

    public void setNext(List<Node> next) {
        this.next = next;
    }

}
