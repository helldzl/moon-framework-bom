/**
 * Copyright (C) 2015 BUDEE
 *
 *
 * @className:com.budee.qr.webutil.LoginContext
 * @description:登录上下文常量
 *
 * @version:v0.0.1
 * @author:hongyang
 *
 * Modification History:
 * Date Author Version Description
 * -----------------------------------------------------------------
 * 2015年7月14日 hongyang v0.0.1 create
 *
 *
 */
package org.moonframework.web.constants;

import java.io.Serializable;

public class LoginContextConstants implements Serializable {

    /**
    *
    */

    private static final long serialVersionUID = -3548246696258328270L;
    /**
    * 单点登录系统的登录名
    */
    public static final String KEY_UC_NAME = "_uc_name";
    /**
    * 用户昵称
    */
    public static final String KEY_UC_LOGIN_NAME = "_uc_nick_name";
    /**
    *UC系统的用户ID(1000000000000001)
    */
    public static final String KEY_UC_ID = "_uc_id";

    private LoginContextConstants() {
    }
}
