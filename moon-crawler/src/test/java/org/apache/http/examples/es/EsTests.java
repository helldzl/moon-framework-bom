package org.apache.http.examples.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.moonframework.core.util.*;
import org.moonframework.core.util.BeanUtils;
import org.moonframework.crawler.elasticsearch.PropertyEditorFactory;
import org.moonframework.crawler.elasticsearch.TransportClientFactoryBean;
import org.moonframework.crawler.storage.Node;
import org.springframework.beans.*;
import org.springframework.validation.DataBinder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/5
 */
public class EsTests {

    @Test
    public void testProduct() {
        Node node = new Node();
        node.setClassName("ddd");
        node.setCssQuery("aaa");
        Map<String, Object> stringObjectMap = BeanUtils.toMap(node);
        System.out.println(stringObjectMap);
    }

    @Test
    public void testSearch2() throws Exception {
        Client client = newInstance();
        SearchResponse response = client
                .prepareSearch()
                .execute().actionGet();
        SearchHits hits = response.getHits();
        ObjectMapper mapper =  new ObjectMapper();
        for (SearchHit hit : hits) {
            System.out.println(mapper.writeValueAsString(hit.getSource()));

        }
    }

    @Test
    public void testSearch() throws Exception {
        Client client = newInstance();

        SearchResponse response = client.prepareSearch("twitter", "customer")
                .setTypes("external", "tweet")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        //.setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
                        //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            if (hit.getIndex().equals("twitter")) {
                UserEntity user = new UserEntity();
                BeanWrapper beanWrapper = new BeanWrapperImpl(user);
                beanWrapper.setPropertyValues(conversion(hit.getSource()));

                System.out.println(user.getAvatars());
            }
            System.out.println(hit.getSourceAsString());
        }
    }

    @Test
    public void testClient() throws Exception {
        TransportClientFactoryBean transportClientFactoryBean = new TransportClientFactoryBean();
        transportClientFactoryBean.setHost("192.168.1.228");
        transportClientFactoryBean.setPort(9300);

        // Client client = newInstance();
        Client client = transportClientFactoryBean.getObject();

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
                        .setSource(jsonBuilder()
                                        .startObject()
                                        .field("user", "kimchy")
                                        .field("age", 11)
                                        .field("postDate", new Date())
                                        .field("message", "trying out Elasticsearch")
                                        .field("avatars", Arrays.asList("/avatar_01.jpg", "avatar_02.jpg", "avatar_03.jpg"))
                                        .endObject()
                        )
        );

        bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
                        .setSource(jsonBuilder()
                                        .startObject()
                                        .field("user", "kimchy")
                                        .field("postDate", new Date())
                                        .field("message", "another post")
                                        .endObject()
                        )
        );

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println("fail");
        } else {
            System.out.println("success");
        }

    }

    public MutablePropertyValues conversion(Map<?, ?> original) {
        // We can optimize this because it's all new:
        // There is no replacement of existing property values.
        MutablePropertyValues result = new MutablePropertyValues();
        if (original != null) {
            for (Map.Entry<?, ?> entry : original.entrySet()) {
                PropertyValue pv = new PropertyValue(entry.getKey().toString(), entry.getValue());
                if (entry.getKey().toString().equals("postDate")) {
                    pv.setConvertedValue(new Date());
                }
                result.addPropertyValue(pv);
            }
            // propertyValueList = original.entrySet().stream().map(entry -> new PropertyValue(entry.getKey().toString(), entry.getValue())).collect(Collectors.toList());
        }
        return result;
    }

    @Test
    public void testBinder() {
        MutablePropertyValues values = new MutablePropertyValues();
        values.add("postDate", "2012-12-14 23:22:59");

        UserEntity target = new UserEntity();
        DataBinder binder = new DataBinder(target);
        binder.registerCustomEditor(Date.class, PropertyEditorFactory.getDatePropertyEditor());

        binder.bind(values);
        System.out.println(target);

    }

    private Client newInstance() throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)
                .put("client.transport.ping_timeout", 5, TimeUnit.SECONDS)
                .build();
        return TransportClient.builder()
                .settings(settings)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.228"), 9300));
    }

}
