package org.moonframework.model.mybatis.domain;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/9
 */
public class Html {

    private String title;
    private String description;
    private String author;

    private String version = "1.4.4";
    private String themes = "bootstrap";

    private List<String> links = new ArrayList<>();
    private List<String> scripts = new ArrayList<>();

    public Html() {
    }

    public String format(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    public Html addCss(String css) {
        links.add(format("/css/{0}.css", css));
        return this;
    }

    public Html addScript(String script) {
        scripts.add(format("/js/{0}.js", script));
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    public String getThemes() {
        return themes;
    }

    public List<String> getLinks() {
        return links;
    }

    public List<String> getScripts() {
        return scripts;
    }

}
