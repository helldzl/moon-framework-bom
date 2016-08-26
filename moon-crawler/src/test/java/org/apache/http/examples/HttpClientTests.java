package org.apache.http.examples;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.moonframework.crawler.http.WebHttpClientUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/23
 */
public class HttpClientTests {

    @Test
    public void a() {
        System.out.println();
        assertEquals(true, 1 == 1);
    }

    private static Random random = new Random();
    private static List<String> urls = Arrays.asList(
            "http://zx.ingping.com/c_1_1.html",
            "http://zx.ingping.com/c_1_2.html",
            "http://zx.ingping.com/c_1_3.html",
            "http://zx.ingping.com/c_1_4.html",
            "http://zx.ingping.com/c_1_5.html",
            "http://zx.ingping.com/c_1_6.html",
            "http://zx.ingping.com/c_1_7.html",
            "http://zx.ingping.com/c_1_8.html",
            "http://zx.ingping.com/c_1_9.html",
            "http://zx.ingping.com/c_1_10.html",
            "http://zx.ingping.com/c_1_12.html",
            "http://zx.ingping.com/c_1_13.html",
            "http://zx.ingping.com/c_1_14html",
            "http://zx.ingping.com/c_1_15.html",
            "http://zx.ingping.com/c_1_16.html",
            "http://zx.ingping.com/c_1_17.html",
            "http://zx.ingping.com/c_1_18.html",
            "http://zx.ingping.com/c_1_19.html",
            "http://zx.ingping.com/c_1_20.html"
    );

    public static void main(String[] args) throws InterruptedException {
        CloseableHttpClient httpClient = WebHttpClientUtils.getInstance();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {

            Thread t = new Thread() {
                @Override
                public void run() {
                    HttpGet httpGet = new HttpGet(URI.create(urls.get(random.nextInt(urls.size()))));
                    try {
                        httpClient.execute(httpGet, response -> {
                            int status = response.getStatusLine().getStatusCode();
                            if (status >= 200 && status < 300) {
                                HttpEntity entity = response.getEntity();
                                String result = entity != null ? EntityUtils.toString(entity) : null;
                                System.out.println("finished!");
                                return result;
                            } else {
                                throw new ClientProtocolException("Unexpected response status: " + status);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            threads.add(t);
        }

        long start = System.currentTimeMillis();

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000 + " S");

    }

}
