package org.moonframework.crawler.parse;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class HTMLClean {

    private static final HtmlCompressor compressor = new HtmlCompressor();

    static {
        // default replace all multiple whitespace characters with single spaces.
        // Then compress remove all inter-tag whitespace characters <code>&amp;nbsp;</code>
        compressor.setRemoveIntertagSpaces(true);
    }

    /**
     * Sanitizes HTML from input HTML, by parsing input HTML and filtering it
     * through a basic white-list of permitted tags and attributes.
     * Additionally, the resulting HTML is minified.
     */
    public static String cleanHtml(String str) {
        if (str == null) {
            return null;
        }
        //clean html tag
        String bodyHtml = Jsoup.clean(str, Whitelist.none());
        return compressor.compress(bodyHtml);
    }
}
