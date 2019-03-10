package cn.taroco.rbac.product.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 产品表
 *
 * @author lumiing
 * @date 2019-03-10 22:38:06
 */
@Data
@Accessors(chain = true)
@TableName(value = "product")
public class Product extends Model<Product> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 直充产品所属供应商id
     */
    @TableField(value = "supplier_id",exist = true)
    private Integer supplierId;
    /**
     * 产品类型
     */
    @TableField(value = "product_category_id",exist = true)
    private Integer productCategoryId;
    /**
     * 产品名字
     */
    @TableField(value = "name",exist = true)
    private String name;
    /**
     * 产品编号
     */
    @TableField(value = "product_code",exist = true)
    private String productCode;
    /**
     * 发售类型 1：订购 2：直充
     */
    @TableField(value = "sale_type",exist = true)
    private Integer saleType;
    /**
     * 采购价格
     */
    @TableField(value = "purchase_price",exist = true)
    private BigDecimal purchasePrice;
    /**
     * 销售价格
     */
    @TableField(value = "sale_price",exist = true)
    private BigDecimal salePrice;
    /**
     * 市场价格
     */
    @TableField(value = "bazaar_price",exist = true)
    private BigDecimal bazaarPrice;
    /**
     * 上架状态  0：下架 1：上架
     */
    @TableField(value = "online_status",exist = true)
    private Integer onlineStatus;
    /**
     * 产品备注
     */
    @TableField(value = "keyword",exist = true)
    private String keyword;
    /**
     * 产品图片路径
     */
    @TableField(value = "picture_url",exist = true)
    private String pictureUrl;
    /**
     * 产品说明
     */
    @TableField(value = "product_explain",exist = true)
    private String productExplain;
    /**
     * 创建时间
     */
    @TableField(value = "create_date",exist = true)
    private Date createDate;
    /**
     * 修改时间
     */
    @TableField(value = "modify_date",exist = true)
    private Date modifyDate;

}