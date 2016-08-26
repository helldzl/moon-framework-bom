package org.moonframework.crawler.analysis;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/27
 */
public interface PageAnalyzer {

    boolean analyze(Iterable<? extends Analysable> analysable);

}
