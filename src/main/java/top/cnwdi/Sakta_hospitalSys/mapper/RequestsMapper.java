package top.cnwdi.Sakta_hospitalSys.mapper;

import org.apache.ibatis.annotations.Param;
import top.cnwdi.Sakta_hospitalSys.model.entity.Requests;
import top.cnwdi.Sakta_hospitalSys.model.entity.Users;

import java.util.List;

public interface RequestsMapper {
    List<Requests> listRequestsByStu(@Param("student_num") String num);
    List<Requests> listAllRequests();

    /**
     * 更新单个报销请求结果
     * @param newResult
     * @param requestId
     * @return
     */
    int updateRequestsResult(@Param("new_result") Integer newResult,@Param("request_id") Integer requestId);
    /**
     * 职工-》通过学号查找报销请求
     * @param studentNum
     * @return
     */
    Users Wor_findRequestedByNum(@Param("student_num")String studentNum);

    /**
     * 新增一条报销请求
     * @return
     */
    int saveRequest(Requests requests);

    List<Requests> searchRequestsByResult(Integer result);
}

