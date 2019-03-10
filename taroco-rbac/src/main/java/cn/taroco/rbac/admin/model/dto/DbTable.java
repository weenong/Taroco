package cn.taroco.rbac.admin.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class DbTable {

    private DbColumnInfo pk;

    private String tableName;

    private String engine;

    private String tableComment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    private List<DbColumnInfo> columnInfoList = new ArrayList<>();

    private String className;

    private String lowerClassName;

}
