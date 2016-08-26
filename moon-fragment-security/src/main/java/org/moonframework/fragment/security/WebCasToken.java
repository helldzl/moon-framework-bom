package org.moonframework.fragment.security;

import org.apache.shiro.cas.CasToken;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/21
 */
public class WebCasToken extends CasToken {

    private String appKey;

    public WebCasToken(String ticket, String appKey) {
        super(ticket);
        this.appKey = appKey;
    }

    public String getAppKey() {
        return appKey;
    }

}
