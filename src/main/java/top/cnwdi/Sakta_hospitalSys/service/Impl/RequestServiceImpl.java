package top.cnwdi.Sakta_hospitalSys.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.cnwdi.Sakta_hospitalSys.config.CacheKeyConfig;
import top.cnwdi.Sakta_hospitalSys.mapper.RequestsMapper;
import top.cnwdi.Sakta_hospitalSys.model.entity.Requests;
import top.cnwdi.Sakta_hospitalSys.model.entity.Users;
import top.cnwdi.Sakta_hospitalSys.service.RequestService;
import top.cnwdi.Sakta_hospitalSys.utils.BashCache;
import top.cnwdi.Sakta_hospitalSys.utils.JsonData;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private  RequestsMapper requestsMapper;

    @Autowired
    private BashCache bashCache;

    private JsonData jsonData=new JsonData();
    @Override
    public Users Wor_findRequestedByNum(String studentNum) {
        return requestsMapper.Wor_findRequestedByNum(studentNum);
    }

    @Override
    public List<Requests> listRequestsByStu(String num) {
        return requestsMapper.listRequestsByStu(num);
    }

    @Override
    public List<Requests> listAllRequests() {
        try {
            Object cacheObj = bashCache.getOneMinuteCache().get(CacheKeyConfig.REQUEST_KEY,()->{
                List<Requests> requestsList=requestsMapper.listAllRequests();
                return requestsList;
            });
            if (cacheObj instanceof List){
                List<Requests> requestsList=(List<Requests>) cacheObj;
                return requestsList;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateRequestsResult(Integer newResult, Integer requestId) {
        return requestsMapper.updateRequestsResult(newResult,requestId);
    }

    @Override
    public int saveRequest(String hospitalName,MultipartFile[] files,HttpServletRequest request) {
        Requests requests =uploadMultiFiles(files,request);
        requests.setHospitalName(hospitalName);
        requests.setCreateTime(new Date());
        return requestsMapper.saveRequest(requests);
    }

    public Requests uploadMultiFiles(@RequestParam("file") MultipartFile[] files, HttpServletRequest request){
        int index=0;
        Requests requests=new Requests();
        for (MultipartFile f : files){
            index++;
            saveFile(f,request,index,requests);
        }
        requests.setResult(0);
        return requests;
    }

    public JsonData saveFile(MultipartFile file, HttpServletRequest request, int index, Requests requests){
        String userNum=(String) request.getAttribute("user_num");
        requests.setStudentNum(userNum);
        String fileName = file.getOriginalFilename();
        String code = UUID.randomUUID().toString().replaceAll("-", "");
        String newFileName = code + fileName.substring(fileName.lastIndexOf('.'));
        String resourcesRootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String destFilePath;
        if (index==1){
            destFilePath=  "public/userFiles/" +userNum+ File.separator+"proveImg"+newFileName;
            requests.setProveImgUrl(destFilePath);
        }else if(index==2){
            destFilePath= "public/userFiles/" +userNum+File.separator+"billImg"+newFileName;
            requests.setBillImgUrl(destFilePath);
        }else{
            destFilePath= "public/userFiles/" +userNum+File.separator+newFileName;
        }
        try{
            File destFile = new File(resourcesRootPath+destFilePath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            file.transferTo(destFile);
            return jsonData.buildSuccess(destFilePath);
        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonData.buildError("失败");
    }

    @Override
    public List<Requests> searchRequestsByResult(Integer result){
        return requestsMapper.searchRequestsByResult(result);
    }
}
