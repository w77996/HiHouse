package com.w77996.seed.house.core.constant;

/**
 * 项目常量
 */
public final class ProjectConstant {
    public static final String BASE_PACKAGE = "com.w77996.seed.house";//生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）

    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model.bean";//生成的Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";//生成的Mapper所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//生成的Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".service.impl";//生成的ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//生成的Controller所在包

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.base.Mapper";//Mapper插件基础接口的完全限定名


    /**
     * jwt clientId
     */
    public static final String JWT_CLIENT_ID = "098f6bcd4621d373cade4e832627b4f6";
    /**
     * base密钥
     */
    public static final String JWT_BASE64_SECRET = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";
    /**
     * jwt name
     */
    public static final String JWT_NAME = "api";
    /**
     * token有效期
     */
    public static final Long JWT_EXPIRES_SECOND = 172800L;

}
