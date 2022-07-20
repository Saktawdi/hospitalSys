package top.cnwdi.Sakta_hospitalSys.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "注册请求对象")
public class RegisterRequest {
    /**
     * 注册学号/职工号
     */
    @ApiModelProperty(name = "nun",value = "注册学号/职工号",required = true,example = "202111701321")
    private String num;
    /**
     * 密码
     */
    @ApiModelProperty(name = "pwd",value = "密码",required = true,example = "666")
    private String pwd;
    /**
     * 身份证号码
     */
    @ApiModelProperty(name = "idNum",value = "身份证号码",required = true,example = "651615455153121")
    private String idNum;
    /**
     * 注册角色
     */
    @ApiModelProperty(name = "role",value = "注册角色,1为学生，2为职工",required = true,example ="1")
    private Integer role;

    /**
     * 所属，可为空
     */
    @ApiModelProperty(name = "affiliation",value = "所属",required = false,example = "数学与计算机学院软件工程")
    private String affiliation;

    /**
     * 姓名,可为空
     */
    @ApiModelProperty(name = "name",value = "姓名",required = false,example = "石大侠")
    private String name;

}
