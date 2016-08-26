package org.apache.http.examples.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.moonframework.crawler.elasticsearch.AbstractSearchEngine;

import java.io.IOException;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/7
 */
public class EngineTests extends AbstractSearchEngine<UserEntity> {

    public EngineTests(Client client) {
        super(client);
    }

    public EngineTests(Client client, String index, String type) {
        super(client, index, type);
    }

    @Override
    protected void buildSource(XContentBuilder builder, UserEntity userEntity) throws IOException {

    }

    public static void main(String[] args) {
        new EngineTests(null);
    }
}
