package cn.taroco.rbac.admin.model.dto;

import cn.taroco.rbac.admin.model.entity.SysDept;
import cn.taroco.rbac.admin.model.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author liuht
 * @date 2018/1/20
 * 角色Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDTO extends SysRole {

    private List<SysDept> deptList;
}
