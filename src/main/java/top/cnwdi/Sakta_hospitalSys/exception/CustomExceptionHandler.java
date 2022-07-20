package top.cnwdi.Sakta_hospitalSys.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import top.cnwdi.Sakta_hospitalSys.utils.JsonData;

/**
 * 异常处理类
 */
@ControllerAdvice
public class CustomExceptionHandler {
    private final static Logger logger =  LoggerFactory.getLogger(CustomExceptionHandler.class);

    private JsonData jsonData=new JsonData();
    @ExceptionHandler(value = Exception.class)
    public Object handler(Exception e){
        if ( e instanceof CustomException){
            logger.error("[ 系统异常 ]{}",e);
            CustomException customException=(CustomException)e;
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error.html");
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("code",((CustomException) e).getCode());
            return modelAndView;
        }else{
         return new JsonData().buildError("全局异常，未知错误");
        }
    }

    /**
     * 文件上传文件大小超出异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public JsonData MultipartFileHandler(MaxUploadSizeExceededException exception){

        return jsonData.buildError("上传文件超过限制大小");
    }

}
