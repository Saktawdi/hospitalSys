package top.cnwdi.Sakta_hospitalSys.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "登录封装对象")
@Data
public class LoginRequest {
    @ApiModelProperty(name = "num",value = "账号",example = "202111701301",required = true)
    private String num;
    @ApiModelProperty(name = "pwd",value = "密码",example = "111",required = true)
    private String pwd;
}
