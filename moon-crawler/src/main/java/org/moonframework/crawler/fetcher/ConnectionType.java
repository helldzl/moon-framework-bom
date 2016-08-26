package org.moonframework.crawler.fetcher;

import org.moonframework.crawler.http.WebHttpClientUtils;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/11
 */
public enum ConnectionType {

    HTTP_CLIENT {

        private final HttpConnection connection = new HttpClientConnection(WebHttpClientUtils.getInstance());

        @Override
        public HttpConnection getInstance() {
            return connection;
        }
    },

    JSOUP {
        private final HttpConnection connection = new JsoupConnection();

        @Override
        public HttpConnection getInstance() {
            return connection;
        }
    };

    public abstract HttpConnection getInstance();

}
