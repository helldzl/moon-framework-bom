package org.apache.http.examples;

import org.moonframework.crawler.analysis.Analysable;
import org.moonframework.crawler.analysis.PageAnalyzer;

import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/27
 */
public class PageAnalyzerTests {

    public static class A implements PageAnalyzer {
        @Override
        public boolean analyze(Iterable<? extends Analysable> analysable) {
            analysable.iterator().forEachRemaining(a -> {
                Map<String, Object> content = a.getContent();
                content.get("content"); // 可能为空
                content.get("title");   // 可能为空

                //

                content.put("fingerprint", 223345);
                content.put("featureVector", "word1 word2");
            });
            return false;
        }
    }

}
