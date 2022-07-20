package top.cnwdi.Sakta_hospitalSys.service;

import top.cnwdi.Sakta_hospitalSys.model.entity.Users;
import top.cnwdi.Sakta_hospitalSys.model.request.RegisterRequest;

import java.util.List;

public interface UserService {
    List<Users> listStudents();
    Users findByNum(String Num);

    /**
     * 新增用户
     * @param registerRequest
     * @return
     */
    int save(RegisterRequest registerRequest);


    /**
     * 用于登录
     * @param num
     * @param pwd
     * @return
     */
    String findByNumAndPwd(String num, String pwd);


}
