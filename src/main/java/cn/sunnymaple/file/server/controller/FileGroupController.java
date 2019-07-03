package cn.sunnymaple.file.server.controller;

import cn.sunnymaple.file.server.entity.dto.RestGroupFileDto;
import cn.sunnymaple.file.server.service.FileGroupService;
import cn.sunnymaple.rest.response.DefaultRestResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 文件分组
 * @author wangzb
 * @date 2019/6/27 11:40
 */
@RestController
@RequestMapping("/group")
@ApiResponses({@ApiResponse(code = 500, message = "服务器内部错误", response = DefaultRestResult.class)})
@Api(value = "文件分组",description = "文件分组，文件会被分配成一组，可以按组获取所有文件",tags = {"文件分组"})
@Slf4j
public class FileGroupController {

    @Autowired
    private FileGroupService fileGroupService;

    /**
     * 文件上传（单文件）
     * 如果groupId为空，则该文件单独分配一个组，否则添加到指定的组
     * @param file {@link MultipartFile}
     * @param detail 文件描述信息
     * @param groupId 文件组id
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload",produces = "application/json")
    @ApiOperation(value = "文件上传（单文件）", notes = "如果groupId为空，则该文件单独分配一个组，否则添加到指定的组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "detail", value = "文件备注信息",dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "groupId", value = "文件组id",dataType = "String",paramType="query")
    })
    @ApiModelProperty(value = "文件组id",dataType = "String")
    public String upload(String detail, String groupId, @RequestParam("file") MultipartFile file) throws Exception {
        if (!StringUtils.hasText(groupId)){
            groupId = UUID.randomUUID().toString();
        }
        fileGroupService.upload(detail,groupId,file);
        return groupId;
    }

    /**
     * 文件上传（多文件）
     * 多文件上传请使用PostMan等工具测试.多文件上传会分配一个组，如果groupId为空，则会新分配个组，否则添加到指定的组
     * @param detail 文件描述信息
     * @param files MultipartFile数组 {@link MultipartFile}
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "文件上传（多文件）", notes = "多文件上传请使用PostMan等工具测试.多文件上传会分配一个组，如果groupId为空，则会新分配个组，否则添加到指定的组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "detail", value = "文件组备注信息",dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "groupId", value = "文件组id",dataType = "String",paramType="query")
    })
    @ApiModelProperty(value = "文件组id",dataType = "String")
    @PostMapping(value = "/uploads")
    public String upload(String detail, String groupId, @RequestParam("files") MultipartFile[] files) throws Exception{
        if (!StringUtils.hasText(groupId)){
            groupId = UUID.randomUUID().toString();
        }
        fileGroupService.upload(detail, groupId, files);
        return groupId;
    }

    /**
     * 按文件分组获取所有文件的id以及访问路径
     * @param groupId 组id
     * @return
     */
    @ApiOperation(value = "按文件分组获取所有文件的id", notes = "按文件分组获取所有文件的id")
    @ApiImplicitParam(name = "groupId", value = "文件组id", required = true,dataType = "String",paramType="path")
    @ApiModelProperty(value = "组id", dataType = "String")
    @GetMapping("/{groupId}")
    public RestGroupFileDto group(@PathVariable(value = "groupId") String groupId) throws Exception {
        return fileGroupService.getFileGroup(groupId);
    }

    /**
     * 删除文件组
     * @param groupId 文件组id
     * @return
     */
    @DeleteMapping(value = "/delete/{groupId}",produces = "application/json")
    @ApiOperation(value = "删除文件组", notes = "删除文件组")
    @ApiImplicitParam(name = "id", value = "按文件id删除文件", required = true,dataType = "String",paramType="path")
    public String deleteGroup(@PathVariable(value = "groupId") String groupId) throws Exception {
        fileGroupService.deleteByGroupId(groupId);
        return "已删除该文件组";
    }
}
