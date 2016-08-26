package org.apache.http.examples;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/24
 */
public class ReduceTests {

    public static void main(String[] args) {

        //List<String> elements = Arrays.asList("aaa", "bbb", "ccc");
        List<String> elements = Collections.emptyList();
        Optional<StringBuilder> reduce = elements.stream()
                .map(element -> new StringBuilder(element))
                .reduce((builder, builder2) -> builder.append(",").append(builder2));

        if (reduce.isPresent())
            System.out.println(reduce.get());

    }

}
