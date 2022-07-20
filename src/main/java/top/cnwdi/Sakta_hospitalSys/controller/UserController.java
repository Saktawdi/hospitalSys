package top.cnwdi.Sakta_hospitalSys.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import top.cnwdi.Sakta_hospitalSys.model.entity.Requests;
import top.cnwdi.Sakta_hospitalSys.model.entity.Users;
import top.cnwdi.Sakta_hospitalSys.model.request.CommonRequest;
import top.cnwdi.Sakta_hospitalSys.model.request.LoginRequest;
import top.cnwdi.Sakta_hospitalSys.model.request.RegisterRequest;
import top.cnwdi.Sakta_hospitalSys.service.RequestService;
import top.cnwdi.Sakta_hospitalSys.service.UserService;
import top.cnwdi.Sakta_hospitalSys.utils.CommonResult;
import top.cnwdi.Sakta_hospitalSys.utils.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
 */
@Api(tags ="用户模块",value="UserController")
@Slf4j
@RestController
@RequestMapping("api/v1/pri/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

//    private JsonData jsonData = new JsonData();

    /**
     * 注册用户
     * @param registerRequest
     * @return
     */
    @ApiOperation(value = "注册用户")
    @PostMapping("register")
    public CommonResult<String> register(@RequestBody RegisterRequest registerRequest){
        if (registerRequest.getRole()!=null){
            int rows=userService.save(registerRequest);
            return rows==1?CommonResult.ok().setResult(registerRequest):CommonResult.fail("-11","新增用户失败");
        }else{
            return CommonResult.fail("-12","新增用户角色选择错误");
        }
    }

    /**
     * 用户登录
     * @param loginRequest
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping("login")
    public CommonResult<String> login(@RequestBody LoginRequest loginRequest){
        String pwd=loginRequest.getPwd();
        String token=userService.findByNumAndPwd(loginRequest.getNum(), CommonUtils.MD5(pwd));
        if (token!=null){
            return CommonResult.ok().setResult(token);
        }else{
            return CommonResult.fail("-1","登录失败，账号或密码错误");
        }
    }

    /**
     * 查找所有学生
     * @param request
     * @return
     */
    @ApiOperation("查找所有学生")
    @GetMapping("list")
    public CommonResult<Users> listStudents(HttpServletRequest request) {
        Integer userRole=(Integer) request.getAttribute("user_role");
        if(userRole.equals(1)){
            return CommonResult.fail("-1","无权限操作");
        }else if(userRole.equals(2)){
                    List<Users> usersList =userService.listStudents();
                    return CommonResult.ok().setResult(usersList);
        }else{
            return CommonResult.fail("-1","userRole为未知值");
        }
    }

    /**
     * 通过学号找报销请求(返回的是学生与请求连接的json数据)
     * @param commonRequest
     * @param userRole
     * @return
     */
    @ApiOperation("通过学号找报销请求(返回的是学生与请求连接的json数据)")
    @PostMapping("search_Request_ByStu")
    public CommonResult<Users> requestByStudents(@RequestBody CommonRequest commonRequest, @RequestAttribute("user_role") Integer userRole) {
//        Integer userRole=(Integer) request.getAttribute("user_role");
        if(userRole.equals(1)){
            return CommonResult.fail("-1","无权限操作");
        }else if(userRole.equals(2)){
            if(commonRequest.getStudentnum().isEmpty()){
                return CommonResult.fail("-13","未输入要查询的学生号");
            }
            Users users =requestService.Wor_findRequestedByNum(commonRequest.getStudentnum());
            return  CommonResult.ok().setResult(users);
        }else{
            return CommonResult.fail("-1","userRole为未知值");
        }
    }

    /**
     * 查找所有报销请求
     * @param request
     * @return
     */
    @ApiOperation("查找所有报销请求")
    @GetMapping("find_allRequests")
    public CommonResult<Requests> findAllRequests(HttpServletRequest request){
        Integer userRole=(Integer) request.getAttribute("user_role");
        if(userRole.equals(1)){
            return CommonResult.fail("-1","无权限操作");
        }else if(userRole.equals(2)){
            List<Requests> requestsList=requestService.listAllRequests();
            return CommonResult.ok().setResult(requestsList);
        }else{
            return CommonResult.fail("-1","userRole为未知值");
        }
    }

    /**
     * 通过学生号查找请求(只返回报销请求json数据)
     * @param commonRequest
     * @return
     */
    @ApiOperation("通过学生号查找请求(只返回报销请求json数据)")
    @PostMapping("find_requests_byStudentNum")
    public CommonResult<Requests> findRequestByStu(@RequestBody CommonRequest commonRequest){
        if (!commonRequest.getStudentnum().isEmpty()){
            List<Requests> requestsList=requestService.listRequestsByStu(commonRequest.getStudentnum());
            return CommonResult.ok().setResult(requestsList);
        }else{
            return CommonResult.fail("-13","未输入要查询的学生号");
        }
    }

    /**
     * 更新报销请求的状态结果
     * @param commonRequest
     * @param request
     * @return
     */
    @ApiOperation("更新报销请求的状态结果,需要id和result")
    @PutMapping("updata_request_result")
    public CommonResult updataRequest(@RequestBody CommonRequest commonRequest,HttpServletRequest request){
        Integer userRole=(Integer) request.getAttribute("user_role");
        if(userRole.equals(1)){
            if (commonRequest.getRequestResult().equals(3)){
                int rows=requestService.updateRequestsResult(commonRequest.getRequestResult(),commonRequest.getRequestId());
                return rows==1?CommonResult.ok("100","更新成功"):CommonResult.fail("-1","更新失败");
            }else{
                return CommonResult.fail("-1","无权限操作");
            }
        }else if(userRole.equals(2)){
            int rows=requestService.updateRequestsResult(commonRequest.getRequestResult(),commonRequest.getRequestId());
            return rows==1?CommonResult.ok("100","更新成功"):CommonResult.fail("-1","更新失败");
        }else{
            return CommonResult.fail("-1","userRole为未知值");
        }
    }

    /**
     * 增加报销单，融合了上传图片
     * @param hospitalName 医院名，必须
     * @param files 需要两个文件，一个证明图，一个账单图
     * @return
     */
    @ApiOperation(value = "增加报销单，融合了上传图片",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospitalName",value = "医院名",dataTypeClass = String.class,required = true),
            @ApiImplicitParam(name="files",value = "证明图和账单图，请开启动态请求，自行添加另一个同名参数框",dataTypeClass = MultipartFile.class,required = true),
    })
    @PostMapping(value = "add_request",consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public CommonResult addRequest(@RequestParam("hospitalName") String hospitalName, @RequestPart("files") MultipartFile[] files,HttpServletRequest request){

        int rows=requestService.saveRequest(hospitalName,files,request);
        return rows==1?CommonResult.ok("101","新增报销记录成功"):CommonResult.fail("-1","更新失败");
    }

    /**
     * 通过报销单当前结果查找报销单记录
     * @param commonRequest
     * @return
     */
    @ApiOperation(value = "通过报销单当前结果查找报销单记录")
    @PostMapping("search_requests_ByResult")
    public CommonResult searchRequestByResult(@RequestBody CommonRequest commonRequest){
        List<Requests> requests=requestService.searchRequestsByResult(commonRequest.getRequestResult());
        return CommonResult.ok().setResult(requests);
    }
}
