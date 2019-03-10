package cn.taroco.rbac.admin.service.impl;

import cn.taroco.rbac.admin.common.util.GenUtil;
import cn.taroco.rbac.admin.model.dto.DbTable;
import cn.taroco.rbac.admin.model.dto.DbTableColumn;
import cn.taroco.rbac.admin.model.dto.GenCodeConfigDTO;
import cn.taroco.rbac.admin.service.GenCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class GenCodeServiceImpl implements GenCodeService{

    @Value("${gencode.jdbc.url}")
    private String url;
    @Value("${gencode.jdbc.name}")
    private String name;
    @Value("${gencode.jdbc.password}")
    private String password;

    @Override
    public List<DbTable> tableList(String tableName) {
        log.info(url);
        log.info(name);
        Connection conn = this.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        List<DbTable> list = new ArrayList<>();
        try {
            //执行SQL查询，并获取结果
            String sql = "select table_name tableName, engine, table_comment tableComment, create_time createTime " +
                    "from information_schema.tables " +
                    "where table_schema = (select database()) ";
            if(StringUtils.isNotBlank(tableName))
                sql += "and table_name like concat('%', #{tableName}, '%') ";
            sql += "order by create_time desc";
            list = queryRunner.query(conn, sql, new BeanListHandler<>(DbTable.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(conn);
        }

        return list;
    }

    @Override
    public byte[] genCodeByTableName(GenCodeConfigDTO configDTO) throws Exception{

        Connection conn = this.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            FileOutputStream outputStream = new FileOutputStream("test.zip");
            ZipOutputStream zip = new ZipOutputStream(outputStream);
            for (DbTable table : configDTO.getTables()) {
                //查询表信息
                String tableSql = "select table_name tableName, engine, table_comment tableComment," +
                        " create_time createTime from information_schema.tables " +
                        " where table_schema = (select database()) " +
                        " and table_name='"+table.getTableName()+"'";
                DbTable t = queryRunner.query(conn,tableSql,new BeanHandler<>(DbTable.class));
                //查询列信息
                String columnSql = "select column_name columnName, data_type dataType, column_comment columnComment," +
                        "column_key columnKey,extra from information_schema.columns " +
                        "where table_name = '"+table.getTableName()+"' and table_schema = (select database())";
                List<DbTableColumn> columns = queryRunner.query(conn,columnSql,new BeanListHandler<>(DbTableColumn.class));
                //生成代码
                GenUtil.generatorCode(configDTO,t, columns, zip);
            }
            IOUtils.closeQuietly(zip);
            return outputStream.toByteArray();

        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally {
            DbUtils.closeQuietly(conn);

        }

    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
