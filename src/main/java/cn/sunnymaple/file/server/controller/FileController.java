package cn.sunnymaple.file.server.controller;

import cn.sunnymaple.file.server.service.FileService;
import cn.sunnymaple.rest.response.DefaultRestResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wangzb
 * @date 2019/6/25 12:00
 */
@RestController
@RequestMapping("/")
@ApiResponses({@ApiResponse(code = 500, message = "服务器内部错误", response = DefaultRestResult.class)})
@Api(value = "单文件上传",description = "单文件上传",tags = {"单文件上传"})
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传（单文件）
     * @param file {@link MultipartFile}
     * @param detail 文件描述信息
     * @return
     * @throws Exception
     */
    @PostMapping(value = "upload",produces = "application/json")
    @ApiOperation(value = "文件上传（单文件）", notes = "文件上传（单文件）")
    @ApiImplicitParam(name = "detail", value = "文件备注信息",dataType = "String",paramType="query")
    @ApiModelProperty(value = "文件id",dataType = "String")
    public String upload(String detail, @RequestParam("file") MultipartFile file) throws Exception {
        return fileService.upload(detail,file);
    }

    /**
     * 文件上传（多文件）
     * @param detail 文件描述信息
     * @param files MultipartFile数组 {@link MultipartFile}
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "文件上传（多文件）", notes = "文件上传（多文件）")
    @ApiImplicitParam(name = "detail", value = "文件组备注信息",dataType = "String",paramType="query")
    @ApiModelProperty(value = "文件id集合",dataType = "String")
    @PostMapping(value = "uploads")
    public List<String> upload(String detail, @RequestParam("files") MultipartFile[] files) throws Exception{
        return fileService.upload(detail, files);
    }

    /**
     * 按文件id删除文件
     * @param id 文件id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}",produces = "application/json")
    @ApiOperation(value = "按文件id删除文件", notes = "按文件id删除文件")
    @ApiImplicitParam(name = "id", value = "按文件id删除文件", required = true,dataType = "String",paramType="path")
    public String delete(@PathVariable(value = "id") String id) throws Exception {
        fileService.deleteById(id);
        return "已删除该文件";
    }

    /**
     * 删除所有文件，该接口慎用
     * @return
     */
    @DeleteMapping(value = "/delete",produces = "application/json")
    @ApiOperation(value = "删除所有文件，慎用", notes = "删除所有文件，慎用")
    public String delete() throws Exception {
        fileService.deleteAll();
        return "已删除所有文件";
    }

}
