package cn.sunnymaple.file.server.common.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Swagger相关配置
 * @author wangzb
 * @date 2019/7/3 11:22
 */
@Component(value = "fileServerSwaggerProperties")
@ConfigurationProperties(value = "swagger")
@Data
public class SwaggerProperties {
    /**
     * 标题
     */
    private String title = "文件服务器";
    /**
     * 描述
     */
    private String description = "SpringBoot集成MongoDB做文件服务器，更多请关注:https://www.sunnymaple.cn";
    /**
     * 版本
     */
    private String version = "1.0.0";
    /**
     * 创建人名称
     */
    private String name = "SunnyMaple";
    /**
     * 地址
     */
    private String url = "https://www.sunnymaple.cn";
    /**
     * email
     */
    private String email = "wzb0127@163.com";
    /**
     * 指定扫描的包
     */
    private String basePackage = "cn.sunnymaple.file.server";
}
