package org.apache.http.examples;

import org.moonframework.crawler.fetcher.Fetcher;
import org.moonframework.crawler.parse.DefaultParser;
import org.moonframework.crawler.parse.ParserAdapter;
import org.moonframework.crawler.storage.Field;
import org.moonframework.crawler.storage.Node;
import org.moonframework.crawler.storage.WebPage;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/15
 */
public class WebCrawlerTests {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int count = 0;
        Random random = new Random();
        List<String> urls = Arrays.asList(
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
                "http://zx.ingping.com/c_1_11.html"
        );

        // URL队列
        BlockingQueue<WebPage> url = new LinkedBlockingQueue<>();
        BlockingQueue<WebPage> result = new LinkedBlockingQueue<>();

        List<Field> fields = new ArrayList<>();

        Field title = new Field("title");
        title.setCssQuery("a");
        title.setIndex(0);
        title.setAttr("title");
        title.setType(Field.FieldType.ATTRIBUTE);

        Field link = new Field("link");
        link.setCssQuery("a");
        link.setIndex(0);
        link.setAttr("href");
        link.setType(Field.FieldType.ATTRIBUTE);

        Field image = new Field("image");
        image.setCssQuery("img");
        image.setIndex(0);
        image.setAttr("src");
        image.setType(Field.FieldType.ATTRIBUTE);

        Field date = new Field("date");
        date.setCssQuery("div .text-r");
        date.setIndex(0);
        date.setType(Field.FieldType.TEXT);
        date.setDateFormat("yyyy年MM月dd日");

        Field summary = new Field("summary");
        summary.setCssQuery("p.summary");
        summary.setIndex(0);
        summary.setType(Field.FieldType.TEXT);
        summary.setFormat(true);

        fields.add(title);
        fields.add(link);
        fields.add(image);
        fields.add(date);
        // nodes.add(summary);

        Node node = new Node();
        node.setCssQuery(".art_four>li");
        node.setFields(fields);

        new Thread() {
            @Override
            public void run() {
                IntStream.range(0, 1).forEach(value -> {
                    WebPage page = new WebPage(URI.create(urls.get(random.nextInt(urls.size()))));
                    // WebPage page = new WebPage("http://zx.ingping.com/c_1/4397.html");

                    page.setNodes(Arrays.asList(node));
                    // try {
                    // TimeUnit.SECONDS.sleep(random.nextInt(5));
                    // TimeUnit.SECONDS.sleep(5);
                    //} catch (InterruptedException e) {
                    //    e.printStackTrace();
                    //}
                    url.add(page);
                });
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) try {
                    WebPage webPage = result.take();
                    DefaultParser parser = new ParserAdapter();
                    // List<Map<String, Object>> parse = parser.apply(webPage);
                    // System.out.println(parse);
                    // System.out.println(parse);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //
        Fetcher w = new Fetcher(url, result, 6);
        w.execute();
    }

}
