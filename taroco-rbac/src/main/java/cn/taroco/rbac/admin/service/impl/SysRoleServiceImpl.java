package cn.taroco.rbac.admin.service.impl;

import cn.taroco.common.utils.Query;
import cn.taroco.rbac.admin.mapper.SysRoleDeptMapper;
import cn.taroco.rbac.admin.mapper.SysRoleMapper;
import cn.taroco.rbac.admin.model.dto.RoleDTO;
import cn.taroco.rbac.admin.model.entity.SysDept;
import cn.taroco.rbac.admin.model.entity.SysRole;
import cn.taroco.rbac.admin.model.entity.SysRoleDept;
import cn.taroco.rbac.admin.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liuht
 * @since 2017-10-29
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return 成功、失败
     */
    @Override
    public Boolean insertRole(RoleDTO roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.insert(sysRole);
        List<SysDept> deptList = roleDto.getDeptList();
        if(null != deptList && !deptList.isEmpty()) {
            for(int i=0;i<deptList.size();i++) {
                SysDept dept = deptList.get(i);
                if(null != dept) {
                    SysRoleDept roleDept = new SysRoleDept();
                    roleDept.setRoleId(sysRole.getRoleId());
                    roleDept.setDeptId(dept.getDeptId());
                    sysRoleDeptMapper.insert(roleDept);
                }
            }
        }
        return true;
    }

    /**
     * 分页查角色列表
     *
     * @param query   查询条件
     * @param wrapper wapper
     * @return page
     */
    @Override
    public Page selectwithDeptPage(Query<Object> query, QueryWrapper<Object> wrapper) {
        query.setRecords(sysRoleMapper.selectRolePage(query, query.getCondition()));
        return query;
    }

    /**
     * 更新角色
     *
     * @param roleDto 含有部门信息
     * @return 成功、失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateRoleById(RoleDTO roleDto) {
        //删除原有的角色部门关系
        SysRoleDept condition = new SysRoleDept();
        condition.setRoleId(roleDto.getRoleId());
        sysRoleDeptMapper.delete(new QueryWrapper<>(condition));

        //更新角色信息
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.updateById(sysRole);

        //维护角色部门关系
        List<SysDept> deptList = roleDto.getDeptList();
        if(null != deptList && !deptList.isEmpty()) {
            for(int i=0;i<deptList.size();i++) {
                SysDept dept = deptList.get(i);
                if(null != dept) {
                    SysRoleDept roleDept = new SysRoleDept();
                    roleDept.setRoleId(sysRole.getRoleId());
                    roleDept.setDeptId(dept.getDeptId());
                    sysRoleDeptMapper.insert(roleDept);
                }
            }
        }

        return true;
    }

    /**
     * 通过部门ID查询角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectListByDeptId(Integer deptId) {
        return sysRoleMapper.selectListByDeptId(deptId);
    }
}
