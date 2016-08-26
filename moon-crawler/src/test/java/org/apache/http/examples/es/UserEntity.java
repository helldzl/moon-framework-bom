package org.apache.http.examples.es;

import org.moonframework.crawler.elasticsearch.Searchable;

import java.util.Date;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/5
 */
public class UserEntity implements Searchable {

    private String user;
    private Date postDate;
    private String message;
    private List<String> avatars;
    private Integer age;

    @Override
    public String getDocId() {
        return null;
    }

    @Override
    public void setDocId(String docId) {

    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<String> avatars) {
        this.avatars = avatars;
    }
}
