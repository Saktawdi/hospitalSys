package top.cnwdi.Sakta_hospitalSys.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileService {
    void excelExport(HttpServletResponse response) throws IOException;
}
