package top.cnwdi.Sakta_hospitalSys.mapper;

import top.cnwdi.Sakta_hospitalSys.model.entity.ExcelRequests;

import java.util.List;

public interface ExcelMapper {
    List<ExcelRequests> listAllRequestsToExcel();
}
