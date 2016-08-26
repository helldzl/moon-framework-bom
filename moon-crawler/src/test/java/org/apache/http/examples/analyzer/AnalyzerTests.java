package org.apache.http.examples.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.moonframework.crawler.lucene.WordSegment;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/30
 */
public class AnalyzerTests {



    @Test
    public void test() throws IOException {
        a();
        long start = System.currentTimeMillis();
        a();
        a();
        a();
        a();
        a();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        start = System.currentTimeMillis();
        aa();
        aa();
        aa();
        aa();
        aa();
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void aa() {
        //分词器采用智能切分
        Analyzer analyzer = new IKAnalyzer();
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("name", "工作作风上的问题绝对不是小事，如果不坚决纠正不良风气，任其发展下去，就会像一座无形的墙把我们党和人民群众隔开，我们党就会失去根基、失去血脉、失去力量。");
            CharTermAttribute ta = ts.addAttribute(CharTermAttribute.class);
            ts.reset();
            while (ts.incrementToken()) {
                // System.out.println(ta.toString());
            }
            ts.end();
        } catch (IOException e) {

        } finally {
            if (ts != null) {
                try {
                    ts.close();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }
            }

        }
    }

    @Test
    public void a() throws IOException {
        String text = "曲子乐你好啊, 工作作风上的问题绝对不是小事，如果不坚决纠正不良风气，任其发展下去，就会像一座无形的墙把我们党和人民群众隔开，我们党就会失去根基、失去血脉、失去力量。";
        Configuration configuration = DefaultConfig.getInstance();
        configuration.setUseSmart(true);

        IKSegmenter ik = new IKSegmenter(new StringReader(text), configuration);
        Lexeme lexeme = null;
        while ((lexeme = ik.next()) != null) {
            System.out.println(lexeme.getLexemeText());
            System.out.println(lexeme.getBegin());
            System.out.println(lexeme.getBeginPosition());
            System.out.println(lexeme.getEndPosition());
            System.out.println(lexeme.getLength());
            System.out.println(lexeme.getLexemeType());
            System.out.println(lexeme.getLexemeTypeString());
            System.out.println(lexeme.getOffset());
            System.out.println();
        }

        WordSegment.segment(text);
    }

    @Test
    public void bit(){
        long l = 10L ^ 10L;
        System.out.println(Long.bitCount(l));
    }

}
