package top.cnwdi.Sakta_hospitalSys.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 学生报销请求类
 */
@Data
@ApiModel("报销请求基本信息")
public class Requests {

    @ApiModelProperty(name = "id",value = "主键")
    private Integer id;

    @JsonProperty("student_num")
    @ApiModelProperty(name = "student_num",value = "请求人学号")
    private String studentNum;
    /**
     * 报销请求的结果，0为正在审核，1为审核通过，2为审核失败,3为撤销的请求
     */
    @ApiModelProperty(name = "result",value = "报销请求的结果，0为正在审核，1为审核通过，2为审核失败,3为撤销的请求")
    private Integer result;

    @ApiModelProperty(name = "hospital_name",value = "医院名字")
    @JsonProperty("hospital_name")
    private String hospitalName;

    @ApiModelProperty(name = "prove_img_url",value = "证明图存放地址")
    @JsonProperty("prove_img_url")
    private String proveImgUrl;

    @ApiModelProperty(name = "bill_img_url",value = "账单图存放地址")
    @JsonProperty("bill_img_url")
    private String billImgUrl;

    @ApiModelProperty(name = "create_time",value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    @JsonProperty("create_time")
    private Date createTime;
}
