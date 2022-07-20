package top.cnwdi.Sakta_hospitalSys.service.Impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cnwdi.Sakta_hospitalSys.mapper.ExcelMapper;
import top.cnwdi.Sakta_hospitalSys.mapper.RequestsMapper;
import top.cnwdi.Sakta_hospitalSys.model.entity.ExcelRequests;
import top.cnwdi.Sakta_hospitalSys.service.FileService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private ExcelMapper excelMapper;
    @Override
    public void excelExport(HttpServletResponse response) throws IOException {
        List<ExcelRequests> list = excelMapper.listAllRequestsToExcel();
        String fileName = "报销记录";
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS,true);
        Sheet sheet = new Sheet(1,0, ExcelRequests.class);
        //设置自适应宽度
        sheet.setAutoWidth(Boolean.TRUE);
        sheet.setSheetName("报销记录");
        writer.write(list,sheet);
        writer.finish();
        out.flush();
        response.getOutputStream().close();
        out.close();
    }
}
