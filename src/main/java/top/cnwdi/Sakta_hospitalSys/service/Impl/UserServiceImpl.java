package top.cnwdi.Sakta_hospitalSys.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cnwdi.Sakta_hospitalSys.config.CacheKeyConfig;
import top.cnwdi.Sakta_hospitalSys.model.entity.Users;
import top.cnwdi.Sakta_hospitalSys.mapper.UserMapper;
import top.cnwdi.Sakta_hospitalSys.model.request.RegisterRequest;
import top.cnwdi.Sakta_hospitalSys.service.UserService;
import top.cnwdi.Sakta_hospitalSys.utils.BashCache;
import top.cnwdi.Sakta_hospitalSys.utils.CommonUtils;
import top.cnwdi.Sakta_hospitalSys.utils.JWTUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BashCache bashCache;

    @Override
    public List<Users> listStudents() {
        try {
            //guava本地缓存
            Object cacheObj=bashCache.getOneMinuteCache().get(CacheKeyConfig.STUDENTLIST_KEY,()->{
                List<Users> usersList=userMapper.listStudents();
                System.out.println("调用数据库");
                return usersList;
            });
            if (cacheObj instanceof List){
                List<Users> usersList=(List<Users>)cacheObj;
                return usersList;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加用户
     * @param registerRequest
     * @return
     */
    @Override
    public int save(RegisterRequest registerRequest) {
       Users users =parseToStu(registerRequest);
       if (users !=null){
           return userMapper.save(users);
       }else{
           return -1;
       }
    }
    /**
     * 封装json数据到users类
     * @param registerRequest
     * @return
     */
    private Users parseToStu(RegisterRequest registerRequest) {
        if (!registerRequest.getNum().isEmpty() && !registerRequest.getPwd().isEmpty() && !registerRequest.getIdNum().isEmpty() && registerRequest.getRole()!=null){
            Users users =new Users();
            users.setNum(registerRequest.getNum());
            users.setName(registerRequest.getName());
            //MD5加密
            String pwd=registerRequest.getPwd();
            users.setPwd(CommonUtils.MD5(pwd));
            users.setRole(Integer.valueOf(registerRequest.getRole()));
            users.setIdNum(registerRequest.getIdNum());
            users.setAffiliation(registerRequest.getAffiliation());
            users.setCreateTime(new Date());
            return users;
        }else{
            return null;
        }
    }

    @Override
    public String findByNumAndPwd(String num, String pwd) {
        Users users=userMapper.findByNumAndpwd(num,pwd);
        if (users!=null){
            String token=JWTUtil.getJsonToken(users);
            return token;
        }else{
            return null;
        }
    }

    @Override
    public Users findByNum(String num) {
        return userMapper.findByNum(num);
    }


}
