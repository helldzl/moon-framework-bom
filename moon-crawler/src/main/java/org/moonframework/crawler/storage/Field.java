package org.moonframework.crawler.storage;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/16
 */
public class Field implements Serializable {

    private static final long serialVersionUID = -7963220476697909565L;

    /**
     * field name
     */
    private String name;

    /**
     * css query
     */
    private String cssQuery;

    /**
     * element index
     */
    private int index;

    /**
     * field type
     */
    private FieldType type;

    /**
     * attribute name
     */
    private String attr;

    /**
     * if true, format content
     */
    private boolean format;

    /**
     * date format
     */
    private String dateFormat;

    /**
     * regex
     */
    private String regex;

    /**
     * 系数, 和货币类型一起使用
     */
    private String coefficient;

    /**
     * delimiter
     */
    private String delimiter;

    public Field() {
    }

    public Field(String name) {
        this.name = name;
    }

    public Field(String name, FieldType type) {
        this.name = name;
        this.type = type;
    }

    // get and set

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCssQuery() {
        return cssQuery;
    }

    public void setCssQuery(String cssQuery) {
        this.cssQuery = cssQuery;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public boolean isFormat() {
        return format;
    }

    public void setFormat(boolean format) {
        this.format = format;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public enum FieldType {

        /**
         * 日期类型
         */
        DATE {
            @Override
            public Date get(Elements elements, Field field) {
                String value = text(elements, field);

                if (field.getDateFormat() == null)
                    return null;

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(field.getDateFormat());
                    return sdf.parse(value);
                } catch (ParseException e) {
                    return null;
                }
            }
        },

        /**
         * 货币类型
         */
        DECIMAL {
            @Override
            public BigDecimal get(Elements elements, Field field) {
                String value = text(elements, field);

                try {
                    BigDecimal result = new BigDecimal(value);
                    if (field.getCoefficient() != null)
                        return result.multiply(new BigDecimal(field.getCoefficient()));
                    return result;
                } catch (Exception e) {
                    return null;
                }
            }
        },

        /**
         * 文本类型
         */
        TEXT {
            @Override
            public String get(Elements elements, Field field) {
                return text(elements, field);
            }
        },

        /**
         * HTML文档类型
         */
        HTML {
            @Override
            public String get(Elements elements, Field field) {
                int index = field.getIndex();
                String value;
                if (index < 0) {
                    value = elements.html();
                } else {
                    value = elements.get(index).html();
                }
                return Jsoup.clean(format(value, field), Whitelist.relaxed());
            }
        },

        /**
         * 标签属性类型
         */
        ATTRIBUTE {
            @Override
            public String get(Elements elements, Field field) {
                int index = field.getIndex();
                String value;
                if (index < 0) {
                    value = elements.attr(field.getAttr());
                } else {
                    value = elements.get(index).attr(field.getAttr());
                }
                return format(value, field);
            }
        },

        /**
         * 原始元素类型
         */
        ELEMENT {
            @Override
            public Object get(Elements elements, Field field) {
                // 返回空, 不参与到普通文本数据处理流程中
                return null;
            }
        };

        /**
         * <p>分隔符, 默认是<strong>,</strong></p>
         */
        private static final String DELIMITER = ",";

        protected String text(Elements elements, Field field) {
            int index = field.getIndex();
            String value = null;
            if (index < 0) {
                String delimiter = field.getDelimiter() == null ? FieldType.DELIMITER : field.getDelimiter();
                Optional<StringBuilder> optional = elements
                        .stream()
                        .map(element -> new StringBuilder(element.text()))
                        .reduce((left, right) -> left.append(delimiter).append(right));
                if (optional.isPresent())
                    value = optional.get().toString();
            } else {
                value = elements.get(index).text();
            }
            value = format(value, field);
            if (field.getRegex() == null)
                return value;
            return value.replaceAll(field.getRegex(), "").trim();
        }

        protected String format(String value, Field field) {
            if (field.isFormat() && value != null)
                return value.trim().replaceAll("　", "");
            return value;
        }

        protected boolean addAll(PageSegment segment, Media media, Elements elements, int index, String query) {
            if (index < 0) {
                return addAll(segment, media, elements.select(query));
            } else {
                return addAll(segment, media, elements.get(index).select(query));
            }
        }

        protected boolean addAll(PageSegment segment, Media media, Elements elements) {
            return segment.addAll(media, elements);
        }

        public abstract Object get(Elements elements, Field field);

    }

}
