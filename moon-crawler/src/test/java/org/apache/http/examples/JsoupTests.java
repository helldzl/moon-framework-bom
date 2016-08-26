package org.apache.http.examples;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.moonframework.crawler.http.WebHttpClientUtils;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/17
 */
public class JsoupTests {

    private static CloseableHttpClient httpClient = WebHttpClientUtils.getInstance();

    private static <T> T httpGet(HttpGet httpGet, final ResponseHandler<? extends T> responseHandler) {
        try {
            return httpClient.execute(httpGet, responseHandler);
        } catch (IOException e) {
            return null;
        }
    }

    public static String http(URI uri) {
        return httpGet(new HttpGet(uri), response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String result = entity != null ? EntityUtils.toString(entity) : null;
                return result;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        });
    }

    public static void m2(String url) {
        // https://www.thomann.de/gb/access_virus_ti2_keyboard.htm
        // https://www.thomann.de/gb/livid_ds_1_b_stock.htm
        Document parse = Jsoup.parse(http(URI.create(url)));
        System.out.println();
    }

    public static void m(String url) throws IOException {
        Connection connect = Jsoup.connect(url);
        // String body = Jsoup.connect(url).ignoreContentType(true).execute().body();

        Document document = connect.get();
        Document document2 = Jsoup.parseBodyFragment(document.toString());
        // Document document2 = Jsoup.parseBodyFragment(body);

        Elements select = document2.select("ul.paging li.next:first-child");
        System.out.println(document2);
        System.out.println();
    }

    public void a() throws IOException {
        // String url = "http://www.sweetwater.com/store/manufacturer/Allen__and__Heath/popular/pn1";
        // String url = "http://www.guitarcenter.com/Alvarez/Grateful-Dead-50th-Anniversary-Acoustic-Guitar.gc";
        // String url = "http://www.sweetwater.com/store/detail/Whiskey112Cab";
        // String url = "http://www.sweetwater.com/store/manufacturer/all";
        // String url = "http://vintageking.com/a-designs-audio-em-blue";

        // String url = "https://www.thomann.de/gb/cat_brands.html";
        // String url = "https://www.thomann.de/gb/2box.html";
        // String url = "https://www.thomann.de/gb/search_BF_2box.html?ref=mm_htx_ltm";
        String url = "https://www.thomann.de/gb/livid_ds_1_b_stock.htm";
        m2(url);
    }

    public void b() throws IOException {
        String url = "";
        m(url);
    }

    public static void main(String[] args) throws IOException {
        JsoupTests t = new JsoupTests();
        t.a();
        String s = "CNY 23,456778.00DF".replaceAll("(\\.|,|[A-Z])", "").trim();
        System.out.println(s);

        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(s);
        String value = "";
        while (matcher.find()) {
            value += matcher.group();
        }
        System.out.println(value);


        s = "$45678.09".replaceAll("\\$", "").trim();
        System.out.println(s);

    }
}
