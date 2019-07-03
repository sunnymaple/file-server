package cn.sunnymaple.file.server.service.impl;

import cn.sunnymaple.file.server.common.FileInfoFactory;
import cn.sunnymaple.file.server.entity.FileInfo;
import cn.sunnymaple.file.server.repository.FileRepository;
import cn.sunnymaple.file.server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangzb
 * @date 2019/6/25 13:12
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    /**
     * 单文件上传
     *
     * @param detail 文件备注信息
     * @param file   {@link MultipartFile}
     * @return 文件id
     * @throws Exception
     */
    @Override
    public String upload(String detail, MultipartFile file) throws Exception {
        FileInfo fileInfo = FileInfoFactory.create(detail, file);
        fileRepository.save(fileInfo);
        return fileInfo.getId();
    }

    /**
     * 多文件上传
     *
     * @param detail 文件备注信息
     * @param files  {@link MultipartFile}
     * @return 文件ids
     * @throws Exception
     */
    @Override
    public List<String> upload(String detail, MultipartFile[] files) throws Exception {
        List<FileInfo> fileInfos = FileInfoFactory.create(detail, files);
        fileRepository.saveAll(fileInfos);
        List<String> results = new ArrayList<>();
        fileInfos.forEach(fileInfo -> results.add(fileInfo.getId()));
        return results;
    }

    /**
     * 删除单个文件
     *
     * @param id 文件id
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) throws Exception {
        fileRepository.deleteById(id);
    }

    /**
     * 删除所有的文件
     *
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() throws Exception {
        fileRepository.deleteAll();
    }
}
