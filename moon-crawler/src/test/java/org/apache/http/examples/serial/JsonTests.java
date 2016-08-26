package org.apache.http.examples.serial;

import org.junit.Test;
import org.moonframework.crawler.elasticsearch.SearchWithClustersResult;
import org.moonframework.crawler.util.ObjectMapperFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/28
 */
public class JsonTests {

    @Test
    public void b()throws Exception{
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("_id", 1);
        list.add(map);

        SearchWithClustersResult result = new SearchWithClustersResult(100L);

        String s = ObjectMapperFactory.writeValueAsString(result);
        System.out.println(s);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void a() throws Exception {

        String json = "{\"000000\":0.0014898932353611398,\"0000FF\":0.0016290788714444424,\"FF0000\":0.0016992028000096205,\"FF00FF\":0.0019685444934579117,\"00FF00\":0.001807928591072558,\"00FFFF\":0.002338461890390652,\"FFFF00\":0.0025692686229170708,\"FFFFFF\":0.010454244491114987}";
        Map<String, Double> map = ObjectMapperFactory.readValue(json, Map.class);
        map.forEach((s, d) -> System.out.println(s + " " + d));

    }

}
