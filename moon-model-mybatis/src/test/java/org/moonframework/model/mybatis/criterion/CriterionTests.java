package org.moonframework.model.mybatis.criterion;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public class CriterionTests {

    @Test
    public void testMd5() {
        String s = DigestUtils.md5Hex("hello");
        System.out.println(s);
    }

    @Test
    public void testEq2() {
        Criterion criterion1 = Restrictions.conjunction();
        QueryCondition condition = new QueryCondition();
        criterion1.toSqlString(condition);
        System.out.println(condition);
    }

    @Test
    public void testEq() {
        // 1 conjunction
        Criterion criterion1 = Restrictions.conjunction().add(Restrictions.eq("username", "quzile")).add(Restrictions.eq("version", "12345"));
        QueryCondition condition = new QueryCondition();
        criterion1.toSqlString(condition);

        // 2 allEq
        Map<String, Object> propertyNameValues = new HashMap<>();
        propertyNameValues.put("age", 22);
        propertyNameValues.put("hob", "ABC");
        Criterion criterion2 = Restrictions.allEq(propertyNameValues);

        // 3 and, like, or, ilike
        Criterion criterion3 = Restrictions
                .and(
                        Restrictions.or(Restrictions.like("dogName", "xiaomi", MatchMode.ANYWHERE), Restrictions.like("dogName", "dami")),
                        Restrictions.or(Restrictions.like("dogName222", "hello", MatchMode.ANYWHERE)),
                        Restrictions.or(Restrictions.like("dogNamehhh", "hello22", MatchMode.ANYWHERE))
                );
        // System.out.println(criterion3);
        // (age<? OR name=? OR (username=? AND version=?) OR (gender=? AND hob=?) OR ((dogName LIKE ? OR dogName LIKE ?) AND (dogName222 LIKE ?) AND (dogNamehhh LIKE ?)))
        // (age<? OR name=? OR (username=? AND version=?) OR (gender=? AND hob=?) OR ((dogName LIKE '%xiaomi%' OR dogName LIKE ?) AND (dogName222 LIKE ?) AND (dogNamehhh LIKE ?)))

        // 4 between, in
        Criterion criterion4 = Restrictions.between("date", "2014-01-01", "2016-01-01");
        Criterion criterion5 = Restrictions.in("numbers", new Object[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        // result disjunction
        Criterion criterion0 = Restrictions.disjunction().add(Restrictions.lt("age", "1"))
                .add(Restrictions.eq(new QueryField("users", "name"), "qzl"))
                .add(criterion1)
                .add(criterion2)
                .add(criterion3)
                .add(criterion4)
                .add(criterion5);
        QueryCondition condition2 = new QueryCondition();
        criterion0.toSqlString(condition2);

        System.out.println(criterion0.toString());
        System.out.println();

        condition2.getTokens().forEach(queryToken -> {
            System.out.print(queryToken.getValue());
        });
        System.out.println();
        System.out.println();

        condition2.getTokens().forEach(queryToken -> {
            if (!queryToken.isParam()) {
                System.out.print(queryToken.getValue());
            } else {
                System.out.print("?");
            }

        });
        System.out.println();
        System.out.println();

        System.out.println("END");

        // StringHelper.join("?",sb2.toString().split("?") );

    }

}
