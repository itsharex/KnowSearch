package com.didiglobal.logi.op.manager.interfaces.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author didi
 * @date 2022-07-11 6:57 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "安装包条件查询信息")
public class PackageVO {
    /**
     * 安装包id
     */
    @ApiModelProperty("安装包id")
    private Integer id;
    /**
     * 安装包名字
     */
    @ApiModelProperty("安装包名字")
    private String name;
    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String url;
    /**
     * 版本
     */
    @ApiModelProperty("版本")
    private String version;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String describe;
    /**
     * 类型，0是配置依赖，1是配置独立
     */
    @ApiModelProperty("类型，0是配置依赖，1是配置独立")
    private Integer type;
    /**
     * 脚本id
     */
    @ApiModelProperty("脚本id")
    private Integer scriptId;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Timestamp createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Timestamp updateTime;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String creator;

    /**
     * 关联的默认安装包分组配置
     */
    @ApiModelProperty("关联的默认安装包分组配置")
    private List<PackageGroupConfigVO> groupConfigList;
}
