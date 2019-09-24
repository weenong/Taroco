package cn.taroco.rbac.admin.service;

import cn.taroco.rbac.admin.model.dto.DbColumnInfo;
import cn.taroco.rbac.admin.model.dto.DbTable;
import cn.taroco.rbac.admin.model.dto.GenCodeConfigDTO;

import java.util.List;

public interface GenCodeService {
    List<DbTable> tableList(String tableName);

    byte[] genCodeByTableName(GenCodeConfigDTO configDTO) throws Exception;

    List<DbColumnInfo> columnList(String tableName) throws Exception;
}
