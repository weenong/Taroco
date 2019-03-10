package cn.taroco.rbac.admin.model.dto;

import lombok.Data;

@Data
public class DbColumnInfo {
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
     * 属性类型
     */
    private String attrType;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 首字符大写的属性名
     */
    private String upAttrName;
}
