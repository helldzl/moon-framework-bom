package org.moonframework.crawler.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.select.Elements;
import org.moonframework.crawler.parse.AbstractParser;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.List;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/16
 */
public abstract class PersistenceAdapter implements Persistence {

    protected static Logger logger = LogManager.getLogger(PersistenceAdapter.class);

    @Override
    public void persist(String className, List<PageSegment> segments) {
        try {
            for (PageSegment segment : segments) {
                Map<String, Object> data = segment.getData();
                Object o = Class.forName(className).newInstance();
                BeanWrapper beanWrapper = new BeanWrapperImpl(o);
                beanWrapper.setPropertyValues(data);
                Long id = save(o);
                if (id == null)
                    continue;

                segment.getMedias().forEach((media, elements) -> {
                    switch (media) {
                        case IMAGE:
                            image(id, elements);
                            break;
                        case IMAGE_LINK:
                            imageLink(id, elements);
                            break;
                        case VIDEO:
                            video(id, elements);
                            break;
                        case AUDIO:
                            audio(id, elements);
                            break;
                        case ATTACHMENT:
                            attachment(id, elements);
                            break;
                        default:
                            break;
                    }
                });

                visited((String) data.get(AbstractParser.ORIGIN));

            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            logger.error(() -> "Data persist error", e);
        }
    }

    /**
     * <p>执行持久化操作</p>
     *
     * @param obj 待持久化的数据
     * @return entity id
     */
    protected abstract Long save(Object obj);

    // hook method

    protected void image(Long id, Elements elements) {

    }

    protected void imageLink(Long id, Elements elements) {

    }

    protected void video(Long id, Elements elements) {

    }

    protected void audio(Long id, Elements elements) {

    }

    protected void attachment(Long id, Elements elements) {

    }

    protected void visited(String url) {

    }

}
