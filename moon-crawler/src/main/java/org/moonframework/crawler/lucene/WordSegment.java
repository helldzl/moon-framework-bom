package org.moonframework.crawler.lucene;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/8/3
 */
public class WordSegment {

    /**
     * <p>给如文本进行分词</p>
     *
     * @param text 输入文本
     * @return 分词结果
     */
    public static List<Lexeme> segment(String text) {
        try {
            List<Lexeme> lexemes = new ArrayList<>();
            IKSegmenter ik = new IKSegmenter(new StringReader(text), true);
            Lexeme lexeme;
            while ((lexeme = ik.next()) != null)
                lexemes.add(lexeme);
            return lexemes;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
