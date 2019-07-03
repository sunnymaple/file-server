package cn.sunnymaple.file.server.service;

import cn.sunnymaple.file.server.entity.dto.RestGroupFileDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件分组
 * @author wangzb
 * @date 2019/6/27 11:45
 */
public interface FileGroupService {

    /**
     * 单文件上传
     * @param detail 文件备注信息
     * @param groupId 文件组id
     * @param file {@link MultipartFile}
     * @throws Exception
     */
    void upload(String detail, String groupId, MultipartFile file) throws Exception;

    /**
     * 多文件上传
     * @param detail 文件备注信息
     * @param groupId 文件组id
     * @param files {@link MultipartFile}
     * @throws Exception
     */
    void upload(String detail, String groupId, MultipartFile[] files) throws Exception;

    /**
     * 按文件分组获取所有文件的id
     * @param groupId 文件组id
     * @return {@link RestGroupFileDto}
     * @throws Exception
     */
    RestGroupFileDto getFileGroup(String groupId) throws Exception;

    /**
     * 删除文件组
     * @param groupId 组id
     * @throws Exception
     */
    void deleteByGroupId(String groupId) throws Exception;
}
