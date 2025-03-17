package io.github.nichetoolkit.mybatis.interceptor;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class})
})
public class MybatisResultInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> resList = new ArrayList<>();
        DefaultResultSetHandler defaultResultSetHandler = (DefaultResultSetHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(defaultResultSetHandler);
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("mappedStatement");
        List<ResultMap> resultMaps = mappedStatement.getResultMaps();
        Configuration configuration = (Configuration)metaStatementHandler.getValue("configuration");
        Class<?> resultType = resultMaps.get(0).getType();
        //获取mybatis返回的实体类类型名
        int resultMapCount = resultMaps.size();
        if (resultMapCount > 0) {
            Statement statement = (Statement) invocation.getArgs()[0];
            ResultSet resultSet = statement.getResultSet();
//            if (resultSet != null) {
//                //获得对应列名
//                ResultSetMetaData rsmd = resultSet.getMetaData();
//                List<String> columnList = new ArrayList<String>();
//                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//                    columnList.add(rsmd.getColumnName(i));
//                }
//                while (resultSet.next()) {
//                    LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
//                    for (String colName : columnList) {
//                        resultMap.put(colName, resultSet.getString(colName));
//                        //具体些要转换的码值这里就做个演示
//                        if(colName.equals("username")){
//                            resultMap.put(colName, "iui");
//                        }
//                    }
//                    Object o = resultType.newInstance();
//                    //将转换后的map转换为实体类中
//                    BeanUtils.populate(o,resultMap);
//                    resList.add(o);
//                }
//                return resList;
//            }
        }
        return invocation.proceed();
    }

}
