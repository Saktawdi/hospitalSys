package top.cnwdi.Sakta_hospitalSys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import top.cnwdi.Sakta_hospitalSys.model.entity.ExcelRequests;
import top.cnwdi.Sakta_hospitalSys.service.FileService;
import top.cnwdi.Sakta_hospitalSys.utils.CommonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件操作接口
 */
@Api(tags = "文件操作模块",value = "FilesController")
@Slf4j
@RestController
@RequestMapping("api/v1/pri/files")
public class FilesController {
    @Autowired
    private FileService fileService;
    /**
     * 单文件上传
     * @param file
     * @return
     */
    @ApiIgnore
    @ApiOperation("单文件上传")
    @PostMapping("uploadOneFile")
    public CommonResult uploadFiles(@RequestParam("file") MultipartFile file){
        return saveFile(file);
    }

    /**
     *多文件上传
     * @param files
     * @param request
     * @return
     */
    @ApiIgnore
    @ApiOperation("多文件上传")
    @PostMapping("multiUpload")
    public CommonResult uploadMultiFiles(@RequestParam("file") MultipartFile [] files, HttpServletRequest request){
        ExcelRequests requests=new ExcelRequests();
        for (MultipartFile f : files){
            saveFile(f);
        }
        return CommonResult.ok().setResult(requests);
    }
    public CommonResult saveFile(MultipartFile file){

        String fileName = file.getOriginalFilename();
        String code = UUID.randomUUID().toString().replaceAll("-", "");
        String newFileName = code + fileName.substring(fileName.lastIndexOf('.'));

        String resourcesRootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath();

        String destFilePath=resourcesRootPath+ "public/CommonFiles/" +File.separator+newFileName;
        try{
            File destFile = new File(destFilePath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            file.transferTo(destFile);
            return CommonResult.ok().setResult(destFilePath);
        }catch(Exception e){
            e.printStackTrace();
        }
        return CommonResult.fail();
    }

    @ApiOperation(value = "下载所有报销记录到本地", produces = "application/octet-stream")
    @GetMapping("download")
    @ResponseBody
    public CommonResult downloadList(HttpServletResponse response) throws IOException {
        try {
            fileService.excelExport(response);
        }catch (Exception e){
            log.info("文件下载接口：下载失败");
            e.printStackTrace();
            return CommonResult.fail("-100","文件下载失败");
        }
        return  CommonResult.ok();
    }

}
