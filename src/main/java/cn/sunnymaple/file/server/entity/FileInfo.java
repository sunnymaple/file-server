package cn.sunnymaple.file.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

/**
 * @author wangzb
 * @date 2019/6/25 13:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo implements File{
    /**
     * 主键，文件ID
     */
    @Id
    private String id;
    /**
     * 组id，对于多文件上传，分为一个组，单文件groupId即为id
     */
    private String groupId;

    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件类型
     */
    private String contentType;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 文件上传时间
     */
    private Long timestamp = System.currentTimeMillis();
    /**
     * 文件Hash值，使用md5算法
     * 用于文件校验，如果下载的md5值和服务器返回的md5值不一致，说明文件有损
     */
    private String md5Value;
    /**
     * 文件内容
     */
    private Binary content;
    /**
     * 文件备注信息
     */
    private String detail;

    public FileInfo(String name, String contentType, long size, String detail,Binary content) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.content = content;
        this.detail = detail;
    }

    public FileInfo(String groupId) {
        this.groupId = groupId;
    }
}
