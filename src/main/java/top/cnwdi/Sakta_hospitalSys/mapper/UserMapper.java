package top.cnwdi.Sakta_hospitalSys.mapper;

import org.apache.ibatis.annotations.Param;
import top.cnwdi.Sakta_hospitalSys.model.entity.Users;

import java.util.List;

public interface UserMapper {
    List<Users> listStudents();

    /**
     * 注册用户
     * @param users
     * @return
     */
    int save(Users users);

    /**
     * 通过学号找学生信息
     * @param num
     * @return
     */
    Users findByNum(@Param("num")String num);



    /**
     * 通过账号密码查找用户，用于登录
     * @param num
     * @param pwd
     * @return
     */
    Users findByNumAndpwd(@Param("num") String num,@Param("pwd") String pwd);
}
