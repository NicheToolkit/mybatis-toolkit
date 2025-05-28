package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisMapperFactory;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.mybatis.load.RestParam;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.JsonUtils;
import io.github.nichetoolkit.rice.mapper.FindParamMapper;
import io.github.nichetoolkit.rice.mapper.SuperMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <code>LoadResultTypeHandler</code>
 * <p>The load result type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.apache.ibatis.type.BaseTypeHandler
 * @since Jdk1.8
 */
public abstract class LoadResultTypeHandler extends BaseTypeHandler<Object> {
    /**
     * <code>superTable</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The <code>superTable</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    protected final MybatisTable superTable;

    /**
     * <code>LoadResultTypeHandler</code>
     * <p>Instantiates a new load result type handler.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public LoadResultTypeHandler(MybatisTable superTable) {
        this.superTable = superTable;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        throw new SQLException("The 'setNonNullParameter' method of 'LoadResultTypeHandler' is not supported");
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getLoadResult(rs,columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new SQLException("The 'getNullableResult' method of 'LoadResultTypeHandler' with 'columnIndex' is not supported");
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new SQLException("The 'getNullableResult' method of 'LoadResultTypeHandler' with 'callableStatement' is not supported");
    }

    abstract protected <E> Object parseResult(Class<E> entityType,List<?> result) throws SQLException;

    @SuppressWarnings(value = "unchecked")
    protected Object getLoadResult(ResultSet resultSet, String columnName) throws SQLException {
        Map<Class<?>, MybatisColumn> loadColumns = superTable.getLoadColumns();
        List<MybatisColumn> mybatisColumns = superTable.loadKeyColumns();
        String loadsJson = resultSet.getString(EntityConstants.LOADS);
        List<RestLoad.OfRestLoad> loadPresents = JsonUtils.parseList(loadsJson, RestLoad.OfRestLoad.class);
        Map.Entry<Class<?>, MybatisColumn> loadEntry = destineLoadEntry(columnName, loadPresents, loadColumns);
        Object columnValue = resultSet.getObject(columnName);
        if (GeneralUtils.isNotEmpty(loadEntry) && GeneralUtils.isNotEmpty(columnValue)) {
            List<MybatisColumn> loadParamColumns = superTable.loadParamColumns();
            RestParam[] loadParams = destineLoadParams(resultSet, loadEntry, loadParamColumns);
            MybatisMapperFactory<SuperMapper<?,?>, ?, ?> mapperFactory = MybatisMapperFactory.instanceOfBean();
            Class<?> entryKey = loadEntry.getKey();
            MybatisColumn entryValue = loadEntry.getValue();
            SuperMapper<?, ?> superMapper = mapperFactory.superMapper(entryKey);
            if (GeneralUtils.isNotEmpty(superMapper)) {
                FindParamMapper<?, Object> findParamMapper = (FindParamMapper<?, Object>) superMapper;
                String loadTable = entryValue.getLoadTable();
                List<?> entityList;
                if (GeneralUtils.isNotEmpty(loadTable)) {
                    String tablename = destineTablename(resultSet, loadTable);
                    entityList = findParamMapper.findDynamicAllByIdOrParams(tablename,columnValue,loadParams);
                } else {
                    entityList = findParamMapper.findAllByIdOrParams(columnValue,loadParams);
                }
                return parseResult(entryKey,entityList);
            }
        }
        return null;
    }

    private RestParam[] destineLoadParams(ResultSet resultSet, Map.Entry<Class<?>, MybatisColumn> loadEntry, List<MybatisColumn> loadParamColumns) throws SQLException {
        Class<?> entryKey = loadEntry.getKey();
        MybatisColumn entryValue = loadEntry.getValue();
        Set<String> entryKeys = entryValue.getLoadKeys();
        Map<String,Object> loadParams = new HashMap<>();
        for (MybatisColumn loadParam : loadParamColumns) {
            String columnName = loadParam.getColumn();
            Set<String> loadParamKeys = loadParam.getLoadKeys();
            List<Class<?>> loadParamTypes = loadParam.getLoadTypes();
            if (GeneralUtils.isNotEmpty(loadParamTypes) && loadParamTypes.contains(entryKey)) {
                Object columnValue = resultSet.getObject(columnName);
                loadParams.putIfAbsent(columnName, columnValue);
                continue;
            }
            if (GeneralUtils.isNotEmpty(loadParamKeys)) {
                for (String loadParamKey : loadParamKeys) {
                    if (entryKeys.contains(loadParamKey)) {
                        Object columnValue = resultSet.getObject(columnName);
                        loadParams.putIfAbsent(columnName, columnValue);
                    }
                }
            }
        }
        return loadParams.entrySet().stream().map(RestParam::of).toArray(RestParam[]::new);
    }

    private String destineTablename(ResultSet resultSet,String loadTable) throws SQLException {
        String regex = "\\{[^}]*}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(loadTable);
        String result = null;
        while(matcher.find()){
            String param = matcher.group(0);
            String columnName = param.substring(1, param.length() - 1);
            if (GeneralUtils.isNotEmpty(columnName)) {
                String columnValue = resultSet.getString(columnName);
                if (GeneralUtils.isNotEmpty(columnValue)) {
                    result = loadTable.replaceFirst(regex,columnValue);
                }
            }
        }
        return result;
    }

    private Map.Entry<Class<?>,MybatisColumn> destineLoadEntry(String columnName, List<RestLoad.OfRestLoad> loadPresents, Map<Class<?>, MybatisColumn> loadColumns) {
        if (GeneralUtils.isEmpty(loadPresents) || GeneralUtils.isEmpty(loadColumns)) {
            return null;
        }
        for (Map.Entry<Class<?>, MybatisColumn> entry : loadColumns.entrySet()) {
            Class<?> entryKey = entry.getKey();
            MybatisColumn entryValue = entry.getValue();
            Set<String> entryKeys = entryValue.getLoadKeys();
            if (GeneralUtils.isEmpty(entryKeys) || !entryKeys.contains(columnName)) {
                continue;
            }
            for (RestLoad.OfRestLoad loadPresent : loadPresents) {
                Boolean loadValue = loadPresent.getValue();
                if (GeneralUtils.isEmpty(loadValue) || !loadValue) {
                    continue;
                }
                String loadKey = loadPresent.getKey();
                if (GeneralUtils.isNotEmpty(loadKey) && entryKeys.contains(loadKey)) {
                    return entry;
                }
            }
        }
        return null;
    }

    /**
     * <code>multiResultHandler</code>
     * <p>The multi result handler method.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.handler.LoadResultTypeHandler} <p>The multi result handler return object is <code>LoadResultTypeHandler</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static LoadResultTypeHandler multiResultHandler(MybatisTable superTable) {
        return new LoadMultiTypeHandler(superTable);
    }

    /**
     * <code>singleResultHandler</code>
     * <p>The single result handler method.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.handler.LoadResultTypeHandler} <p>The single result handler return object is <code>LoadResultTypeHandler</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static LoadResultTypeHandler singleResultHandler(MybatisTable superTable) {
        return new LoadSingleTypeHandler(superTable);
    }

    /**
     * <code>arrayResultHandler</code>
     * <p>The array result handler method.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.handler.LoadResultTypeHandler} <p>The array result handler return object is <code>LoadResultTypeHandler</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static LoadResultTypeHandler arrayResultHandler(MybatisTable superTable) {
        return new LoadArrayTypeHandler(superTable);
    }

}
