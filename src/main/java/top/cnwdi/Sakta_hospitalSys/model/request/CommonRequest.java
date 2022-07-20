package top.cnwdi.Sakta_hospitalSys.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用请求封装
 */
@Data
@ApiModel(value = "通用请求封装对象",description = "请根据接口所需来填写需要的属性")
public class CommonRequest {
    /**
     * 学号
     */
    @ApiModelProperty(name = "studentnum",value = "学生学号",required = false)
    @JsonProperty("student_num")
    private String studentnum;
    /**
     * 报销单的结果状态
     */
    @ApiModelProperty(name = "request_result",value = "报销单的状态结果,0为审核中,1为通过,2为退回,3为学生撤回",required = false)
    @JsonProperty("request_result")
    private Integer requestResult;
    /**
     * 报销单的ID
     */
    @ApiModelProperty(name = "request_id",value = "报销单的ID",required = false)
    @JsonProperty("request_id")
    private Integer requestId;

}
