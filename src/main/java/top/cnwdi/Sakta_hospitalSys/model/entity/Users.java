package top.cnwdi.Sakta_hospitalSys.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@ApiModel("用户基本信息")
@Data
public class Users {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 学号/工号
     */
    @ApiModelProperty(value = "学号/工号",required = true)
    private String num;
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "密码")
    private String pwd;
    @ApiModelProperty(value = "性别，整数型，没有对应逻辑判断是男女")
    private Integer sex;
    /**
     * 所属学院/工作
     */
    @ApiModelProperty(value = "所属学院/工作")
    private String affiliation;
    /**
     * 身份证
     */
    @ApiModelProperty(value = "身份证号")
    @JsonProperty("id_num")
    private String idNum;
    /**
     * 1为学生，2为职工
     */
    @ApiModelProperty(value = "所处角色，1为学生，2为职工")
    private Integer role;

    @ApiModelProperty(value = "用户创建时间")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    @JsonProperty("create_time")
    private Date createTime;

    /**
     * 学生报销请求
     */
    private List<Requests> requests;

}
