package cn.taroco.rbac.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.taroco.rbac.product.mapper.ProductMapper;
import cn.taroco.rbac.product.model.entity.Product;
import cn.taroco.rbac.product.model.dto.ProductDTO;
import cn.taroco.rbac.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * @author lumiing
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;

}