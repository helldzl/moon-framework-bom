package org.moonframework.crawler.elasticsearch;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/6
 */
public final class PropertyEditorFactory {

    private static final PropertyEditor DATE_PROPERTY_EDITOR;

    static {
        DATE_PROPERTY_EDITOR = new PropertyEditorSupport() {
            public void setAsText(String value) {
                try {
                    setValue(format().parse(value));
                } catch (ParseException e) {
                    setValue(null);
                }
            }

            public String getAsText() {
                return format().format((Date) getValue());
            }

            private SimpleDateFormat format() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        };
    }

    public static PropertyEditor getDatePropertyEditor() {
        return DATE_PROPERTY_EDITOR;
    }

}
