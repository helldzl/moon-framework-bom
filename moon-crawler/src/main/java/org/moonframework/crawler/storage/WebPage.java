package org.moonframework.crawler.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jsoup.select.Elements;
import org.moonframework.crawler.fetcher.ConnectionType;

import java.net.URI;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/13
 */
public class WebPage {

    /**
     * 主机名称
     */
    @JsonIgnore
    private String host;

    /**
     * URI
     */
    @JsonIgnore
    private URI uri;

    /**
     * HTTP状态码
     */
    @JsonIgnore
    private int statusCode;

    /**
     * HTML页面
     */
    @JsonIgnore
    private String html;

    /**
     * The system time in milliseconds for when the page was fetched.
     */
    @JsonIgnore
    private long fetchTime;

    /**
     * data
     */
    @JsonIgnore
    private Map<String, Object> data;

    /**
     * 其他数据部分
     */
    @JsonIgnore
    private Map<Media, Elements> medias = new EnumMap<>(Media.class);

    /**
     * 链接类型
     */
    @JsonIgnore
    private ConnectionType connectionType = ConnectionType.HTTP_CLIENT;

    /**
     * <p>抓取页面的dom节点配置信息, Parser根据节点配置信息进行处理</p>
     * <ul>
     * <li>发现URL</li>
     * <li>处理文本</li>
     * </ul>
     */
    private List<Node> nodes;

    public WebPage() {
    }

    public WebPage(URI uri) {
        this.uri = uri;
    }

    public WebPage(String uri) {
        this.uri = URI.create(uri);
    }

    //

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public long getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(long fetchTime) {
        this.fetchTime = fetchTime;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<Media, Elements> getMedias() {
        return medias;
    }

    public void setMedias(Map<Media, Elements> medias) {
        this.medias = medias;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * @param node node
     */
    public void setNode(Node node) {
        if (this.nodes == null)
            this.nodes = new ArrayList<>();
        nodes.add(node);
    }
}
