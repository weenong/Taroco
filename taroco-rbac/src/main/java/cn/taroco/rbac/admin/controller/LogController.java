package cn.taroco.rbac.admin.controller;


import cn.taroco.common.constants.CommonConstant;
import cn.taroco.common.entity.SysLog;
import cn.taroco.common.exception.ClientException;
import cn.taroco.common.utils.Query;
import cn.taroco.common.web.BaseController;
import cn.taroco.common.web.Response;
import cn.taroco.rbac.admin.service.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author liuht
 * @since 2017-11-20
 */
@RestController
@RequestMapping("/log")
public class LogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 分页查询日志信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/logPage")
    public IPage logPage(@RequestParam Map<String, Object> params) {
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        return sysLogService.page(new Query<>(params,SysLog.class), new QueryWrapper<>());
    }

    /**
     * 根据ID
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(sysLogService.updateByLogId(id));
    }

    /**
     * 添加日志
     *
     * @param log 日志实体
     * @param result 错误信息
     */
    @PostMapping
    public void add(@Valid @RequestBody SysLog log, BindingResult result) {
        if (result.hasErrors()) {
            throw new ClientException(result.getAllErrors().get(0).getDefaultMessage());
        }
        sysLogService.save(log);
    }
}
