package org.apache.http.examples.robot;

import org.junit.Test;
import org.moonframework.crawler.http.WebHttpClientUtils;
import org.moonframework.crawler.robots.RobotRulesFilterAdapter;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/8/3
 */
public class RobotTests {

    @Test
    public void testRobot() throws Exception {
        /**
         User-agent: BLEXBot
         Disallow: /

         User-agent: ICC-Crawler
         Disallow: /

         User-agent: *
         Disallow: /user/
         Disallow: /help/
         Disallow: /home/
         Disallow: /css/
         Disallow: /shoppingCart/
         Disallow: /backMng/
         Disallow: /index/
         Disallow: /test/
         Disallow: /realTimeUpdate/
         Disallow: /category/
         Disallow: /images/
         Disallow: /category/
         Disallow: /appManagement/
         Disallow: /tranManagement/
         Disallow: /diySuitShoppingCart/
         Disallow: /kgeSuitActivity/getAllSuitFavorite
         Disallow: /*?*
         * */

        String url = "http://www.ingping.com/user/a";
        String urlb = "http://www.ingping.com/a";
        String urlc = "http://www.ingping.com/www/a/";
        String urld = "http://www.ingping.com/a";
        String urle = "http://www.ingping.com/a?haha";

        RobotRulesFilterAdapter parser = new RobotRulesFilterAdapter();
        parser.setHttpClient(WebHttpClientUtils.getInstance());
        boolean a = parser.isAllowed(url, "BLEXBot");
        boolean b = parser.isAllowed(urlb, "ICC-Crawler");
        boolean c = parser.isAllowed(urlc, "IE");
        boolean d = parser.isAllowed(urld, "IE");
        boolean e = parser.isAllowed(urle, "IE");

        System.out.println(a ? "Allowed" : "Not allowed");//Not allowed
        System.out.println(b ? "Allowed" : "Not allowed");//Not allowed
        System.out.println(c ? "Allowed" : "Not allowed");//allowed
        System.out.println(d ? "Allowed" : "Not allowed");//allowed
        System.out.println(e ? "Allowed" : "Not allowed");//Not allowed

    }
}
