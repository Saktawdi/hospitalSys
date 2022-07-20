package top.cnwdi.Sakta_hospitalSys.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;
import top.cnwdi.Sakta_hospitalSys.model.entity.Requests;
import top.cnwdi.Sakta_hospitalSys.model.entity.Users;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RequestService {
    /**
     * 职工-》通过学号查找报销请求
     * @param studentNum
     * @return
     */
    Users Wor_findRequestedByNum(String studentNum);
    /**
     * 查报销单，学生用
     * @param num
     * @return
     */
    List<Requests> listRequestsByStu(@Param("student_num") String num);

    /**
     * 查所有报销单
     * @return
     */
    List<Requests> listAllRequests();

    /**
     * 更新单个报销请求
     * @param newResult
     * @param requestId
     * @return
     */
    int updateRequestsResult(Integer newResult,Integer requestId);

    /**
     * 新增订单，包含上传图片
     * @param hospitalName
     * @param files
     * @param request
     * @return
     */
    int saveRequest(String hospitalName, MultipartFile [] files,HttpServletRequest request);


    List<Requests> searchRequestsByResult(Integer result);
}
