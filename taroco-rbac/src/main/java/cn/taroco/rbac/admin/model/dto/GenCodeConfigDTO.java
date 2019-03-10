package cn.taroco.rbac.admin.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class GenCodeConfigDTO {
    private List<DbTable> tables;
    private String basePackageName;
    private String modelPackageName;
    private String controllerPackageName;
    private String servicePackageName;
    private String daoPackageName;
    private String authorName;
    private String tablePrefix;
}
