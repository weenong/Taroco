package cn.taroco.rbac.product.controller;

import cn.taroco.rbac.product.model.entity.Product;
import cn.taroco.rbac.product.service.ProductService;

import cn.taroco.common.utils.Query;
import cn.taroco.common.web.Response;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lumiing
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 通过ID查询信息
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Product get(@PathVariable Integer id) {
        return productService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public IPage dictPage(@RequestParam Map<String, Object> params) {
        return productService.page(new Query<>(params), new QueryWrapper<>());
    }

    /**
     * 添加
     *
     * @param product
     * @return success、false
     */
    @PostMapping
    public Response add(@RequestBody Product product) {
        return Response.success(productService.save(product));
    }

    /**
     * 删除
     *
     * @param id   ID
     * @return R
     */
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Integer id) {
        return Response.success(productService.removeById(id));
    }

    /**
     * 修改
     *
     * @param product
     * @return success/false
     */
    @PutMapping
    public Response editDict(@RequestBody Product product) {
        return Response.success(productService.updateById(product));
    }
}
