package cn.taroco.rbac.admin.common.util;

import cn.taroco.rbac.admin.model.dto.*;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GenUtil {

    private static Boolean hasBigDecimal = false;

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("templates/entity.java.vm");
        templates.add("templates/dto.java.vm");
//        templates.add("templates/vo.java.vm");
//        templates.add("templates/converter.java.vm");
        templates.add("templates/controller.java.vm");
        templates.add("templates/service.java.vm");
        templates.add("templates/serviceImpl.java.vm");
        templates.add("templates/mapper.java.vm");
        templates.add("templates/mapper.xml.vm");
        templates.add("templates/index.vue.vm");
        templates.add("templates/api.js.vm");
        templates.add("templates/menu.sql.vm");

        return templates;
    }

    public static Map<String,String> getDataTypes(){
        Map<String,String> map = new HashMap<>();
        map.put("tinyint","Integer");
        map.put("smallint","Integer");
        map.put("mediumint","Integer");
        map.put("int","Integer");
        map.put("integer","Integer");
        map.put("bigint","Integer");
        map.put("float","Float");
        map.put("double","Double");
        map.put("decimal","BigDecimal");
        map.put("bit","Boolean");
        map.put("char","String");
        map.put("varchar","String");
        map.put("tinytext","String");
        map.put("text","String");
        map.put("mediumtext","String");
        map.put("longtext","String");
        map.put("date","Date");
        map.put("datetime","Date");
        map.put("timestamp","Date");
        return map;
    }

    /**
     * 生成代码
     * @param tableInfo
     * @param zip
     */
    public static void generatorCode(GenCodeConfigDTO buildConfigDTO, DbTable tableInfo, ZipOutputStream zip) {
        hasBigDecimal = false;
        //  构建表基本信息
        buildTableInfo(buildConfigDTO,tableInfo);

        // 生成代码
        gen(buildConfigDTO, tableInfo, zip);

    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[] { '_' }).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template,DbTable dbTable, GenCodeConfigDTO tableInfoConfig) {
        String modelPackagePath = "main" + File.separator + "java" + File.separator
                + (tableInfoConfig.getBasePackageName() + "." + tableInfoConfig.getModelPackageName()).replace(".", File.separator);
        String resourcesPath = "main" + File.separator + "resources";
        String frontPath = "main" + File.separator + "front";
        String sqlPath = "main" + File.separator + "sql";
        String className = dbTable.getClassName();
        if (template.contains("entity.java.vm")) {
            return modelPackagePath + File.separator + "entity" + File.separator + className
                    + ".java";
        }

        if (template.contains("dto.java.vm")) {
            return modelPackagePath + File.separator + "dto" + File.separator + className
                    + "DTO.java";
        }

        if (template.contains("vo.java.vm")) {
            return modelPackagePath + File.separator + "vo" + File.separator + className
                    + "VO.java";
        }

        if (template.contains("converter.java.vm")) {
            return modelPackagePath + File.separator + "converter" + File.separator + className
                    + "Converter.java";
        }

        if (template.contains("dao.java.vm")) {
            String path = "main" + File.separator + "java" + File.separator
                    + (tableInfoConfig.getBasePackageName() + "." + tableInfoConfig.getDaoPackageName()).replace(".", File.separator);
            return path + File.separator+ className
                    + "Dao.java";
        }

        if (template.contains("mapper.java.vm")) {
            String path = "main" + File.separator + "java" + File.separator
                    + (tableInfoConfig.getBasePackageName() + "." + tableInfoConfig.getDaoPackageName()).replace(".", File.separator);
            return path + File.separator+ className
                    + "Mapper.java";
        }

        if (template.contains("daoImpl.java.vm")) {
            String path = "main" + File.separator + "java" + File.separator
                    + (tableInfoConfig.getBasePackageName() + "." + tableInfoConfig.getDaoPackageName()).replace(".", File.separator);
            return path + File.separator+ "impl" + File.separator + className
                    + "DaoImpl.java";
        }
        if (template.contains("service.java.vm")) {
            String path = "main" + File.separator + "java" + File.separator
                    + (tableInfoConfig.getBasePackageName() + "." + tableInfoConfig.getServicePackageName()).replace(".", File.separator);
            return path + File.separator+ className
                    + "Service.java";
        }
        if (template.contains("serviceImpl.java.vm")) {
            String path = "main" + File.separator + "java" + File.separator
                    + (tableInfoConfig.getBasePackageName() + "." + tableInfoConfig.getServicePackageName()).replace(".", File.separator);
            return path + File.separator + "impl" + File.separator + className
                    + "ServiceImpl.java";
        }
        if (template.contains("controller.java.vm")) {
            String path = "main" + File.separator + "java" + File.separator
                    + (tableInfoConfig.getBasePackageName() + "." + tableInfoConfig.getControllerPackageName()).replace(".", File.separator);
            return path + File.separator+ className
                    + "Controller.java";
        }
        if (template.contains("query.java.vm")) {
            String path = "main" + File.separator + "java" + File.separator
                    + (tableInfoConfig.getBasePackageName() + "." + tableInfoConfig.getModelPackageName()).replace(".", File.separator);
            return path + File.separator + "query" + File.separator + className
                    + "Query.java";
        }

        if (template.contains("IBatis_sql_map.xml.vm")) {
            return resourcesPath + File.separator+ className
                    + "_sql_map.xml";
        }

        if (template.contains("mapper.xml.vm")) {
            String path = "main" + File.separator + "resources" + File.separator
                    + "mapper";
            return path + File.separator + className
                    + "Mapper.xml";
        }

        if(template.contains("index.vue.vm")){
            return frontPath + File.separator + "vue" + File.separator + "index.vue";
        }

        if(template.contains("api.js.vm")){
            return frontPath + File.separator + "api" + File.separator + dbTable.getLowerClassName() + ".js";
        }
        if(template.contains("menu.sql.vm")){
            return sqlPath + File.separator + File.separator + dbTable.getLowerClassName() + ".sql";
        }
        return null;
    }


    /**
     * 构建表基本数据信息
     * @param tableConfig
     */
    public static void  buildTableInfo(GenCodeConfigDTO configDTO, DbTable tableConfig) {
        // 表名转换成Java类名
        String className = tableToJava(tableConfig.getTableName(), configDTO.getTablePrefix());
        tableConfig.setClassName(className);
        tableConfig.setLowerClassName(StringUtils.uncapitalize(className));
        List<DbColumnInfo> dbColumnInfoList = new ArrayList<>();
        // 列信息
        List<DbColumnInfo> columns = configDTO.getTable().getColumnInfoList();
        for (DbColumnInfo column : columns) {
            DbColumnInfo columnEntity = new DbColumnInfo();
            BeanUtils.copyProperties(column,columnEntity);

            // 列名转换成Java属性名
            String upAttrName = columnToJava(column.getColumnName());
            columnEntity.setUpAttrName(upAttrName);
            String temp = columnEntity.getUpAttrName();
            temp = (new StringBuilder()).append(Character.toLowerCase(temp.charAt(0)))
                    .append(temp.substring(1)).toString();
            columnEntity.setAttrName(temp);
            // 列的数据类型，转换成Java类型
            String attrType = getDataTypes().get(column.getDataType());
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && StringUtils.equals("BigDecimal", column.getDataType())) {
                hasBigDecimal = true;
            }
            // 是否主键
            if ( tableConfig.getPk() == null && StringUtils.equalsIgnoreCase("PRI", column.getColumnKey()) ) {
                tableConfig.setPk(columnEntity);
            }

            String[] casTable = column.getCasTable();
            if(null != casTable){
                String tableName = casTable[0];
                DbColumnInfo casColumn = new DbColumnInfo();
                casColumn.setAttrType(tableToJava(tableName, configDTO.getTablePrefix()));
                temp = casColumn.getAttrType();
                casColumn.setAttrName((new StringBuilder()).append(Character.toLowerCase(temp.charAt(0)))
                        .append(temp.substring(1)).toString());
                dbColumnInfoList.add(casColumn);
            }

            dbColumnInfoList.add(columnEntity);
        }
        tableConfig.setColumnInfoList(dbColumnInfoList);

        // 没主键，则第一个字段为主键
        if (tableConfig.getPk() == null) {
            tableConfig.setPk(tableConfig.getColumnInfoList().get(0));
        }
    }

    /**
     * 生成代码
     * @param buildConfigDTO
     * @param tableConfig
     * @param zip
     */
    public static void gen(GenCodeConfigDTO buildConfigDTO,DbTable tableConfig, ZipOutputStream zip) {
        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        LocalDateTime now = LocalDateTime.now();
        // 封装模板数据
        Map<String, Object> map = new HashMap<>();

        GenCodeInfo genCodeInfo = new GenCodeInfo();
        BeanUtils.copyProperties(tableConfig,genCodeInfo);
        genCodeInfo.setModelPackageName(buildConfigDTO.getBasePackageName() + "." + buildConfigDTO.getModelPackageName());
        genCodeInfo.setControllerPackageName(buildConfigDTO.getBasePackageName() + "." + buildConfigDTO.getControllerPackageName());
        genCodeInfo.setServicePackageName(buildConfigDTO.getBasePackageName() + "." + buildConfigDTO.getServicePackageName());
        genCodeInfo.setDaoPackageName(buildConfigDTO.getBasePackageName() + "." + buildConfigDTO.getDaoPackageName());
        genCodeInfo.setAuthorName(buildConfigDTO.getAuthorName());
        map.put("tableInfo", genCodeInfo);
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("datetime", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        VelocityContext context = new VelocityContext(map);

        // 获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            try {
                // 添加到zip
                String fileName = getFileName(template, tableConfig,buildConfigDTO);
                zip.putNextEntry(new ZipEntry(fileName));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("渲染模板失败，表名：" + tableConfig.getTableName(), e);
            }
        }
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }
}
