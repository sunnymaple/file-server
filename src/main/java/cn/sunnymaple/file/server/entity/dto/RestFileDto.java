package cn.sunnymaple.file.server.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * file对象的Rest响应参数
 * @author wangzb
 * @date 2019/6/25 15:28
 */
@Data
@NoArgsConstructor
@ApiModel("文件信息")
public class RestFileDto {
    /**
     * 文件在线访问路径
     */
    @ApiModelProperty(value = "文件下载路径", dataType = "String")
    private String path;
    /**
     * 文件id
     */
    @ApiModelProperty(value = "文件ID", dataType = "String")
    private String id;
    /**
     * 组id，对于多文件上传，分为一个组，单文件groupId即为id
     */
    @ApiModelProperty(value = "组id，对于多文件上传，分为一个组，单文件groupId即为id", dataType = "String")
    private String groupId;
    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称", dataType = "String")
    private String name;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型", dataType = "String")
    private String contentType;
    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小", dataType = "Long")
    private Long size;
    /**
     * 文件上传时间
     */
    @ApiModelProperty(value = "文件上传时间", dataType = "Long")
    private Long timestamp;
    /**
     * 文件Hash值，使用md5算法
     *  用于文件校验，如果下载的md5值和服务器返回的md5值不一致，说明文件有损
     */
    @ApiModelProperty(value = "文件Hash值，使用md5算法", dataType = "String")
    private String md5Value;
    /**
     * 文件备注信息
     */
    @ApiModelProperty(value = "文件备注信息", dataType = "String")
    private String detail;

    public RestFileDto(String groupId, String detail) {
        this.groupId = groupId;
        this.detail = detail;
    }
}
