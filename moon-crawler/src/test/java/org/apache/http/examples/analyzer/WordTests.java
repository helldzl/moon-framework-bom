package org.apache.http.examples.analyzer;

import org.junit.Test;
import org.moonframework.crawler.lucene.WordSegment;
import org.moonframework.crawler.util.ObjectMapperFactory;
import org.moonframework.intelligence.hash.SimHash;
import org.moonframework.intelligence.hash.Word;
import org.wltea.analyzer.core.Lexeme;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/8/3
 */
public class WordTests {

    @Test
    public void testWord() throws Exception {
        String text = "曲子乐你好啊, 工作作风上的问题绝对不是小事，如果不坚决纠正不良风气，任其发展下去，就会像一座无形的墙把我们党和人民群众隔开，我们党就会失去根基、失去血脉、失去力量。";
        List<Lexeme> segment = WordSegment.segment(text);

        List<Word> words = segment
                .stream().collect(Collectors.groupingBy(lexeme -> lexeme.getLexemeText())).entrySet()
                .stream().map(this::newInstance)
                .sorted((w1, w2) -> Integer.compare(w2.getWeight(), w1.getWeight())).limit(12).collect(Collectors.toList());

        // 二次排序
        Collections.sort(words, (w1, w2) -> Integer.compare(w1.getBegin(), w2.getBegin()));
        SimHash simHash = new SimHash();
        System.out.println(simHash.applyAsLong(words));
        System.out.println(ObjectMapperFactory.writeValueAsString(words));
    }

    private Word newInstance(Map.Entry<String, List<Lexeme>> entry) {
        List<Lexeme> value = entry.getValue();
        Lexeme lexeme = value.get(0);
        int length = lexeme.getLength();
        int type = lexeme.getLexemeType();
        int freq = value.size();

        int weight;
        switch (type) {
            case 1: // TYPE_ENGLISH
                weight = 2;
                break;
            case 2: // TYPE_ARABIC
                weight = freq;
                break;
            case 3: // TYPE_LETTER
                weight = 2;
                break;
            case 4: // TYPE_CNWORD
                weight = length;
                break;
            case 8: // TYPE_OTHER_CJK
                weight = length;
                break;
            case 16: // TYPE_CNUM
                weight = length;
                break;
            case 32: // TYPE_COUNT
                weight = length;
                break;
            case 48: // TYPE_CQUAN
                weight = freq;
                break;
            case 64: // TYPE_CNCHAR
                weight = freq;
                break;
            default:
                weight = freq;
                break;
        }
        weight = (freq * 4) + (weight * 6);
        return new Word(entry.getKey(), weight, lexeme.getBegin());
    }

}
