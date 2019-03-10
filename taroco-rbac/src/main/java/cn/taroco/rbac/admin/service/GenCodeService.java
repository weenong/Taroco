package cn.taroco.rbac.admin.service;

import cn.taroco.rbac.admin.model.dto.DbTable;
import cn.taroco.rbac.admin.model.dto.GenCodeConfigDTO;

import java.util.List;

public interface GenCodeService {
    List<DbTable> tableList(String tableName);

    byte[] genCodeByTableName(GenCodeConfigDTO configDTO) throws Exception;
}
