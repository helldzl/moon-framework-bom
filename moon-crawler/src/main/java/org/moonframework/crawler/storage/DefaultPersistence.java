package org.moonframework.crawler.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Random;

/**
 * Created by quzile on 2016/8/23.
 */
public class DefaultPersistence extends PersistenceAdapter {

    protected static Logger logger = LogManager.getLogger(DefaultPersistence.class);

    private Random random = new Random(47);

    @Override
    protected Long save(Object obj) {
        long id = random.nextLong();
        logger.info(() -> String.format("ID : %s, entity : %s", id, obj.toString()));
        return id;
    }

    @Override
    protected void image(Long id, Elements elements) {
        for (Element element : elements) {
            logger.info(() -> String.format("ID : %s, image element : %s", id, element));
        }
    }

    @Override
    protected void imageLink(Long id, Elements elements) {
        for (Element element : elements) {
            logger.info(() -> String.format("ID : %s, image link element : %s", id, element));
        }
    }

    @Override
    protected void video(Long id, Elements elements) {
        for (Element element : elements) {
            logger.info(() -> String.format("ID : %s, video element : %s", id, element));
        }
    }

    @Override
    protected void audio(Long id, Elements elements) {
        for (Element element : elements) {
            logger.info(() -> String.format("ID : %s, audio element : %s", id, element));
        }
    }

    @Override
    protected void attachment(Long id, Elements elements) {
        for (Element element : elements) {
            logger.info(() -> String.format("ID : %s, attachment element : %s", id, element));
        }
    }

    @Override
    protected void visited(String url) {
        logger.info(() -> "URL : " + url);
    }
}
