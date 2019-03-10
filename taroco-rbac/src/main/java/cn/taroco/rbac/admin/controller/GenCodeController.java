package cn.taroco.rbac.admin.controller;

import cn.taroco.common.web.BaseController;
import cn.taroco.common.web.Response;
import cn.taroco.rbac.admin.model.dto.DbTable;
import cn.taroco.rbac.admin.common.config.GenCodeConfig;
import cn.taroco.rbac.admin.model.dto.GenCodeConfigDTO;
import cn.taroco.rbac.admin.service.GenCodeService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/gencode")
public class GenCodeController extends BaseController {

    @Autowired
    private GenCodeService genCodeService;

    @Autowired
    private GenCodeConfig genCodeConfig;

    @GetMapping("/tables")
    public Response page(String tableName){
        List<DbTable> tableList = genCodeService.tableList(tableName);
        return Response.success(tableList);
    }

    @GetMapping("/configinfo")
    public Response configInfo(){
        GenCodeConfigDTO genCodeConfigDTO = new GenCodeConfigDTO();
        BeanUtils.copyProperties(genCodeConfig,genCodeConfigDTO);
        return Response.success(genCodeConfigDTO);
    }


    @PostMapping("/code")
    public void code(@RequestBody GenCodeConfigDTO configDTO, HttpServletResponse response) throws Exception {

        byte[] data = genCodeService.genCodeByTableName(configDTO);
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
