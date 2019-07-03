package cn.sunnymaple.file.server.service.impl;

import cn.sunnymaple.file.server.common.FileInfoFactory;
import cn.sunnymaple.file.server.entity.FileInfo;
import cn.sunnymaple.file.server.entity.dto.RestGroupFileDto;
import cn.sunnymaple.file.server.repository.FileRepository;
import cn.sunnymaple.file.server.service.FileGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzb
 * @date 2019/6/27 11:50
 */
@Service
public class FileGroupServiceImpl implements FileGroupService {

    @Value("${file.path}")
    private String filePath;

    @Autowired
    private FileRepository fileRepository;

    /**
     * 单文件上传
     *
     * @param detail  文件备注信息
     * @param groupId 文件组id
     * @param file    {@link MultipartFile}
     * @return 文件组id
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upload(String detail, String groupId, MultipartFile file) throws Exception {
        FileInfo fileInfo = FileInfoFactory.create(detail, file, groupId);
        fileRepository.save(fileInfo);
    }


    /**
     * 多文件上传
     *
     * @param detail  文件备注信息
     * @param groupId 文件组id
     * @param files   {@link MultipartFile}
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upload(String detail, String groupId, MultipartFile[] files) throws Exception {
        List<FileInfo> fileInfos = FileInfoFactory.create(detail,files,groupId);
        fileRepository.saveAll(fileInfos);
    }

    /**
     * 按文件分组获取所有文件的id
     *
     * @param groupId 文件组id
     * @return {@link RestGroupFileDto}
     * @throws Exception
     */
    @Override
    public RestGroupFileDto getFileGroup(String groupId) throws Exception {
        List<String> result = new ArrayList<>();
        List<FileInfo> files = fileRepository.findByGroupId(groupId);
        if (files!=null && files.size()>0){
            files.forEach(file -> result.add(file.getId()));
        }
        return new RestGroupFileDto(result,filePath);
    }

    /**
     * 删除文件组
     *
     * @param groupId 组id
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByGroupId(String groupId) throws Exception {
        fileRepository.deleteByGroupId(groupId);
    }
}
