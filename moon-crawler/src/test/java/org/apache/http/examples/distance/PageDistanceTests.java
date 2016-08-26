package org.apache.http.examples.distance;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.moonframework.core.util.Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/5
 */
public class PageDistanceTests {

    @Test
    public void testLcs(){
        List<String> x = Arrays.asList("a", "b", "c", "b", "d", "a", "b");
        List<String> y = Arrays.asList("b", "d", "c", "a", "b", "a");
        int[][] lcs = Algorithm.lcs(x, y);
        for (int i = 0; i <= x.size(); i++) {
            for (int j = 0; j <= y.size(); j++) {
                System.out.printf(lcs[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println(lcs[x.size()][y.size()]);
        System.out.println(Algorithm.printLcs(x, y));
    }

    @Test
    public void testDistance() throws Exception {
        Document document1 = Jsoup.connect("http://www.guitarcenter.com/Hofner/Ignition-Club-Bass-with-Case.gc").get();
        Document document2 = Jsoup.connect("http://www.guitarcenter.com/Rogue/LX205B-5-String-Series-III-Electric-Bass-Guitar.gc").get();

        Elements aa = document1.body().getAllElements();
        Elements bb = document2.body().getAllElements();

        List<Tag> l1 = new ArrayList<>();
        for (Element element : aa) {
            Tag tag = element.tag();
            //l1.add(tag.getName());
            l1.add(tag);
        }
        List<Tag> l2 = new ArrayList<>();
        for (Element element : bb) {
            Tag tag = element.tag();
            l2.add(tag);
            //l2.add(tag.getName());
        }

        long start = System.currentTimeMillis();
        int[][] lcs = Algorithm.lcs(l1, l2);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(lcs[l1.size()][l2.size()]);
        double ret = 0.0D;
        if (l1.size() > l2.size()) {
            ret = (double) lcs[l1.size()][l2.size()] / (double) l1.size();
        } else {
            ret = (double) lcs[l1.size()][l2.size()] / (double) l2.size();
        }
        System.out.println(ret);
    }


}
