package com.todo.base;

/**
 * @author zjy
 * @date 2024/12/11  11:03
 */
public class BaseConstant {

    /**
     * 应用Redis缓存头
     */
    public static final String APP_REDIS_CACHE_HEAD = "todo:";

    /**
     * user缓存头
     */
    public static final String LOGIN_USER = "login:user:";

    /**
     * token缓存头
     */
    public static final String LOGIN_TOKEN = "login:token:";

    /**
     * 邮箱注册码缓存头
     */
     public static final String EMAIL_REGISTRATION_CODE = "emailRegistrationCode:";

    /**
     * 用户登录过期时间,单位：天
     */
    public static final long LOGIN_EXPIRE_TIME = 30L;

    /**
     * request请求头中的token
     */
    public static final String TOKEN_HEADER = "authorization";

    /**
     * 请求是否进行加密
     */
    public static final String AKS_ENCRYPT = "aksEncrypt";

    /**
     * 请求头aesKey
     */
    public static final String AES_KEY = "aesKey";

    /**
     * Get请求加密参数
     */
    public static final String GET_ENCRYPT_PARAM = "encryptParam";

    /**
     * POST请求类型
     */
    public static final String POST = "POST";

    /**
     * GET请求类型
     */
    public static final String GET = "GET";

    /**
     * 慢请求时间阈值（毫秒）
     */
    public static final long SLOW_REQUEST_TIME = 3000L;

    /**
     * 密码加密后缀
     */
    public static final String PASSWORD_SUFFIX = "boot";
}
