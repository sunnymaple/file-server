package cn.sunnymaple.file.server.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文件组信息
 * @author wangzb
 * @date 2019/6/26 10:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("文件组信息")
public class RestGroupFileDto {
    /**
     * 该组中所有文件id集合
     */
    @ApiModelProperty(value = "该组中所有文件id集合", dataType = "List")
    private List<String> ids;
    /**
     * 文件访问的根路径，不带文件id
     */
    @ApiModelProperty(value = "文件访问的根路径，不带文件id", dataType = "String")
    private String path;
}
