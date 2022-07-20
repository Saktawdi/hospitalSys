package top.cnwdi.Sakta_hospitalSys.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * 学生报销请求类_用于打印出excel
 * 新增这个实体类是为了防止接口展示多出一个cellStyleMap的model
 */
@Data
@ApiIgnore
public class ExcelRequests extends BaseRowModel{

    @ExcelProperty(value = "ID", index = 0)
    private Integer id;

    @ExcelProperty(value = "请求人学号", index = 1)
    @JsonProperty("student_num")
    private String studentNum;
    /**
     * 报销请求的结果，0为正在审核，1为审核通过，2为审核失败,3为撤销的请求
     */
    @ExcelProperty(value = "报销请求的结果", index = 2)
    private Integer result;

    @ExcelProperty(value = "医院名字", index = 3)
    @JsonProperty("hospital_name")
    private String hospitalName;

    @ExcelProperty(value = "证明图存放地址", index = 4)
    @JsonProperty("prove_img_url")
    private String proveImgUrl;

    @ExcelProperty(value = "账单图存放地址", index = 5)
    @JsonProperty("bill_img_url")
    private String billImgUrl;

    @ExcelProperty(value = "创建时间", index = 6)
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    @JsonProperty("create_time")
    private Date createTime;
}
