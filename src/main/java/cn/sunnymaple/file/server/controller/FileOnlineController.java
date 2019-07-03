package cn.sunnymaple.file.server.controller;

import cn.sunnymaple.file.server.entity.FileInfo;
import cn.sunnymaple.file.server.repository.FileRepository;
import cn.sunnymaple.rest.response.NonResponseHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 文件在线查看
 * @author wangzb
 * @date 2019/6/27 14:19
 */
@RestController
@RequestMapping("/online")
@Api(value = "在线查看文件",description = "在线查看文件",tags = {"在线查看文件"})
@Slf4j
public class FileOnlineController {

    @Autowired
    private FileRepository fileRepository;

    /**
     * 在线查看文件
     * @param id 文件id
     * @return
     * @throws Exception
     */
    @GetMapping("/{id}")
    @NonResponseHandler
    @ApiOperation(value = "在线查看文件", notes = "直接使用浏览器或者其他客户端在线浏览")
    @ApiImplicitParam(name = "id", value = "文件id", required = true,dataType = "String",paramType="path")
    public ResponseEntity<Object> online(@PathVariable(value = "id") String id) {
        Optional<FileInfo> op = fileRepository.findById(id);
        if (op.isPresent()) {
            FileInfo file = op.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.getSize() + "").header("Connection", "close")
                    .body(file.getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }
    }
}
