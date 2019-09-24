package cn.taroco.common.utils;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.bytecode.FieldInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author liuht
 * @date 2017/12/10
 */
public class Query<T> extends Page<T> {
    private static final String PAGE = "page";
    private static final String LIMIT = "limit";
    private static final String ORDER_BY_FIELD = "orderByField";
    private static final String IS_ASC = "isAsc";
    private Map<String, Object> condition = new HashMap<>();

    public Query(Map<String, Object> params,Class clz) {
        super(Integer.parseInt(params.getOrDefault(PAGE, 1).toString())
                , Integer.parseInt(params.getOrDefault(LIMIT, 10).toString()));

        String orderByField = params.getOrDefault(ORDER_BY_FIELD, "").toString();
        if (StringUtils.isNotEmpty(orderByField)) {
            Boolean isAsc = Boolean.parseBoolean(params.getOrDefault(IS_ASC, Boolean.TRUE).toString());
            if(isAsc)
                this.setAsc(orderByField);
            else
                this.setDesc(orderByField);
        }



        params.remove(PAGE);
        params.remove(LIMIT);
        params.remove(ORDER_BY_FIELD);
        params.remove(IS_ASC);
        if(params.size()>0)
            this.setCondition(params,clz);
    }

    public Map<String, Object> getCondition() {
        return this.condition;
    }

    public Page<T> setCondition(Map<String, Object> condition,Class<T> clz) {
        this.condition = new HashMap<>();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clz);
        String tableName = tableInfo.getTableName();
        List<TableFieldInfo> list = tableInfo.getFieldList();
        if(list != null && !list.isEmpty()){
            Iterator<String> iter = condition.keySet().iterator();
            while(iter.hasNext()){
                String key = iter.next();
                Object val = condition.get(key);
                for(TableFieldInfo fieldInfo:list){
                    if(StringUtils.equalsIgnoreCase(fieldInfo.getProperty(),key)){
                        this.condition.put(tableName + "." + fieldInfo.getColumn(),val);
                        break;
                    }
                }
            }
        }
//        this.condition = condition;
        return this;
    }
}
