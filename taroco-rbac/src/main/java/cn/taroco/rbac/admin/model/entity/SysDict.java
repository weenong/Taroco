package cn.taroco.rbac.admin.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author liuht
 * @since 2017-11-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dict")
public class SysDict extends Model<SysDict> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 数据值
     */
	private String value;
    /**
     * 标签名
     */
	private String label;
    /**
     * 类型
     */
	private String type;
    /**
     * 描述
     */
	private String description;
    /**
     * 排序（升序）
     */
	private BigDecimal sort;
    /**
     * 创建时间
     */
	@TableField(value = "create_time",fill = FieldFill.INSERT)
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField(value = "update_time" ,fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
    /**
     * 备注信息
     */
	private String remarks;
    /**
     * 删除标记
     */
	@TableField("del_flag")
	private String delFlag;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysDict{" +
			", id=" + id +
			", value=" + value +
			", label=" + label +
			", type=" + type +
			", description=" + description +
			", sort=" + sort +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", remarks=" + remarks +
			", delFlag=" + delFlag +
			"}";
	}
}
