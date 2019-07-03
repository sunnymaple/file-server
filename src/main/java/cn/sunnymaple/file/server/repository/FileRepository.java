package cn.sunnymaple.file.server.repository;

import cn.sunnymaple.file.server.entity.FileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * File文件创库
 * @author wangzb
 * @date 2019/6/25 13:07
 */
public interface FileRepository extends MongoRepository<FileInfo, String> {
    /**
     * 按文件组查询
     * @param groupId 文件组Id
     * @return
     */
    List<FileInfo> findByGroupId(String groupId);

    /**
     * 删除文件组
     * @param groupId 文件组id
     */
    void deleteByGroupId(String groupId);
}
