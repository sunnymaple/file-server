package cn.sunnymaple.file.server.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author wangzb
 * @date 2019/6/25 13:14
 */
public interface FileService {

    /**
     * 单文件上传
     * @param detail 文件备注信息
     * @param file {@link MultipartFile}
     * @return 文件id
     * @throws Exception
     */
    String upload(String detail, MultipartFile file) throws Exception;

    /**
     * 多文件上传
     * @param detail 文件备注信息
     * @param files {@link MultipartFile}
     * @return 文件id
     * @throws Exception
     */
    List<String> upload(String detail, MultipartFile[] files) throws Exception;

    /**
     * 删除单个文件
     * @param id 文件id
     * @throws Exception
     */
    void deleteById(String id) throws Exception;

    /**
     * 删除所有的文件
     * @throws Exception
     */
    void deleteAll() throws Exception;
}
