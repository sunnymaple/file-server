package cn.sunnymaple.file.server.common;

import cn.sunnymaple.file.server.entity.FileInfo;
import cn.sunnymaple.rest.exception.ParamException;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * FileInfo文件信息对象生产工厂
 * @author wangzb
 * @date 2019/6/25 14:00
 */
public class FileInfoFactory {

    /**
     * 创建一个FileInfo文件信息对象
     * @param detail 文件其他信息
     * @param file {@link MultipartFile}
     * @return
     * @throws Exception
     */
    public static FileInfo create(String detail , MultipartFile file, String groupId) throws Exception {
        if (file.isEmpty()){
            throw new ParamException("请上传一个文件！");
        }
        byte[] bytes = file.getBytes();
        FileInfo fileInfo = new FileInfo(file.getOriginalFilename(), file.getContentType(),
                file.getSize(),detail, new Binary(bytes));
        fileInfo.setGroupId(groupId);
        fileInfo.setMd5Value(getMD5(file.getInputStream()));
        return fileInfo;
    }

    /**
     * 创建一个FileInfo文件信息对象
     * @param detail 文件其他信息
     * @param file {@link MultipartFile}
     * @return
     * @throws Exception
     */
    public static FileInfo create(String detail , MultipartFile file) throws Exception {
        return create(detail,file,"");
    }

    /**
     * 创建一个多个FileInfo文件信息对象
     * @param detail 文件描述
     * @param files {@link MultipartFile}
     * @return
     * @throws Exception
     */
    public static List<FileInfo> create(String detail , MultipartFile[] files,String groupId) throws Exception {
        if (files == null || files.length == 0){
            throw new ParamException("请至少上传一个文件！");
        }
        List<FileInfo> fileInfos = new ArrayList<>();
        for (MultipartFile file : files){
            FileInfo fileInfo = create(detail,file,groupId);
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }

    public static List<FileInfo> create(String detail , MultipartFile[] files, String groupId, HttpServletRequest request) throws Exception {
        if (files == null || files.length == 0){
            request.getParameter("files");
            throw new ParamException("请至少上传一个文件！");
        }
        List<FileInfo> fileInfos = new ArrayList<>();
        for (MultipartFile file : files){
            FileInfo fileInfo = create(detail,file,groupId);
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }

    /**
     * 创建一个多个FileInfo文件信息对象
     * @param detail 文件描述
     * @param files {@link MultipartFile}
     * @return
     * @throws Exception
     */
    public static List<FileInfo> create(String detail , MultipartFile[] files) throws Exception {
        return create(detail , files,"");
    }


    /**
     * 转md5
     * @param is
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuffer md5 = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        byte[] mdBytes = md.digest();
        // convert the byte to hex format
        for (int i = 0; i < mdBytes.length; i++) {
            md5.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return md5.toString();
    }
}
