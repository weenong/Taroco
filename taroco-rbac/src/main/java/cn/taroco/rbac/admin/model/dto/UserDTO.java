package cn.taroco.rbac.admin.model.dto;

import cn.taroco.rbac.admin.model.entity.SysRole;
import cn.taroco.rbac.admin.model.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author liuht
 * @date 2017/11/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {
    /**
     * 角色ID
     */
    private List<SysRole> roleList;

    private Integer deptId;

    /**
     * 新密码
     */
    private String newpassword1;
}
