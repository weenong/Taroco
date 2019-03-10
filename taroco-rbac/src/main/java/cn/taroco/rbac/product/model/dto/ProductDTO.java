package cn.taroco.rbac.product.model.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.*;

/**
 * 产品表
 *
 * @author lumiing
 * @date 2019-03-10 22:38:06
 */
@Data
@Accessors(chain = true)
public class ProductDTO{

    /**
     * 主键
     */
    private Integer id;
    /**
     * 直充产品所属供应商id
     */
    private Integer supplierId;
    /**
     * 产品类型
     */
    private Integer productCategoryId;
    /**
     * 产品名字
     */
    private String name;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 发售类型 1：订购 2：直充
     */
    private Integer saleType;
    /**
     * 采购价格
     */
    private BigDecimal purchasePrice;
    /**
     * 销售价格
     */
    private BigDecimal salePrice;
    /**
     * 市场价格
     */
    private BigDecimal bazaarPrice;
    /**
     * 上架状态  0：下架 1：上架
     */
    private Integer onlineStatus;
    /**
     * 产品备注
     */
    private String keyword;
    /**
     * 产品图片路径
     */
    private String pictureUrl;
    /**
     * 产品说明
     */
    private String productExplain;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date modifyDate;

}