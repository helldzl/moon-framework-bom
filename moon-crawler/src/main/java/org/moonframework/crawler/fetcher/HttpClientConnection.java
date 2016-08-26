package org.moonframework.crawler.fetcher;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moonframework.crawler.storage.WebPage;

import java.io.IOException;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/11
 */
public class HttpClientConnection implements HttpConnection {

    protected final Logger logger = LogManager.getLogger(HttpClientConnection.class);

    private CloseableHttpClient httpClient;

    public HttpClientConnection(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void accept(WebPage page) {
        httpGet(new HttpGet(page.getUri()), response -> {
            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String result = entity != null ? EntityUtils.toString(entity) : null;

                page.setStatusCode(status);
                page.setHtml(result);

                logger.info(() -> "fetching URL [Successful] Code : " + status + ", URL : " + page.getUri());
                return result;
            } else {
                logger.error(() -> "fetching URL Error!, Code : " + status + ", URL : " + page.getUri());
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        });
    }

    protected <T> T httpGet(HttpGet httpGet, final ResponseHandler<? extends T> responseHandler) {
        try {
            return httpClient.execute(httpGet, responseHandler);
        } catch (IOException e) {
            return null;
        }
    }

}
