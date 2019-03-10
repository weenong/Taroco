package cn.taroco.rbac.admin.model.dto;

import lombok.Data;

@Data
public class DbTableColumn {
    /**
     * 列名
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 字段注释
     */
    private String columnComment;

    /**
     * 字段key类型
     */
    private String columnKey;

    private String extra;
}
