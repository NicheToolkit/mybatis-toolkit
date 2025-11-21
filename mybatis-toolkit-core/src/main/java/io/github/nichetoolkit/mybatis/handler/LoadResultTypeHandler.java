package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisMapperFactory;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.mybatis.load.RestParam;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.JsonPurityUtils;
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
        return getLoadResult(rs, columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new SQLException("The 'getNullableResult' method of 'LoadResultTypeHandler' with 'columnIndex' is not supported");
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new SQLException("The 'getNullableResult' method of 'LoadResultTypeHandler' with 'callableStatement' is not supported");
    }

    /**
     * <code>parseResult</code>
     * <p>The parse result method.</p>
     * @param <E>        {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param result     {@link java.util.List} <p>The result parameter is <code>List</code> type.</p>
     * @return {@link java.lang.Object} <p>The parse result return object is <code>Object</code> type.</p>
     * @throws SQLException {@link java.sql.SQLException} <p>The sql exception is <code>SQLException</code> type.</p>
     * @see java.lang.Class
     * @see java.util.List
     * @see java.lang.Object
     * @see java.sql.SQLException
     */
    abstract protected <E> Object parseResult(Class<E> entityType, List<?> result) throws SQLException;

    /**
     * <code>getLoadResult</code>
     * <p>The get load result getter method.</p>
     * @param resultSet  {@link java.sql.ResultSet} <p>The result set parameter is <code>ResultSet</code> type.</p>
     * @param columnName {@link java.lang.String} <p>The column name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.Object} <p>The get load result return object is <code>Object</code> type.</p>
     * @throws SQLException {@link java.sql.SQLException} <p>The sql exception is <code>SQLException</code> type.</p>
     * @see java.sql.ResultSet
     * @see java.lang.String
     * @see java.lang.Object
     * @see java.lang.SuppressWarnings
     * @see java.sql.SQLException
     */
    @SuppressWarnings(value = "unchecked")
    protected Object getLoadResult(ResultSet resultSet, String columnName) throws SQLException {
        String loadsJson;
        try {
            loadsJson = resultSet.getString(EntityConstants.LOADS);
        } catch (SQLException ignored) {
            return null;
        }
        List<String> loadKeys = JsonPurityUtils.parseList(loadsJson, String.class);
        Map<Class<?>, MybatisColumn> loadColumns = superTable.getLoadColumns();
        List<MybatisColumn> mybatisColumns = superTable.loadKeyColumns();
        Map.Entry<Class<?>, MybatisColumn> loadEntry = destineLoadEntry(columnName, loadKeys, loadColumns);
        if (GeneralUtils.isEmpty(loadEntry)) {
            return null;
        }
        List<MybatisColumn> loadParamColumns = superTable.loadParamColumns();
        Object columnValue = null;
        if (!EntityConstants.LOADS.equals(columnName)) {
            columnValue = resultSet.getObject(columnName);
        }
        RestParam[] loadParams = destineLoadParams(resultSet, loadEntry, loadParamColumns);
        if (GeneralUtils.isEmpty(columnValue) && GeneralUtils.isEmpty(loadParams)) {
            return null;
        }
        MybatisMapperFactory<SuperMapper<?, ?>, ?, ?> mapperFactory = MybatisMapperFactory.instanceOfBean();
        Class<?> entryKey = loadEntry.getKey();
        MybatisColumn entryValue = loadEntry.getValue();
        SuperMapper<?, ?> superMapper = mapperFactory.superMapper(entryKey);
        if (GeneralUtils.isEmpty(superMapper)) {
            return null;
        }
        FindParamMapper<?, Object> findParamMapper = (FindParamMapper<?, Object>) superMapper;
        String loadTable = entryValue.getLoadTable();
        String entryColumnName = entryValue.getColumn();
        List<?> entityList;
        if (GeneralUtils.isNotEmpty(loadTable)) {
            String tableName = destineTableName(resultSet, loadTable);
            if (entryValue.isLoadRecursive()) {
                entityList = findParamMapper.findDynamicAllLoadByIdOrParams(tableName, columnValue, loadParams, RestLoad.of(entryColumnName));
            } else {
                entityList = findParamMapper.findDynamicAllByIdOrParams(tableName, columnValue, loadParams);
            }
        } else {
            if (entryValue.isLoadRecursive()) {
                entityList = findParamMapper.findAllLoadByIdOrParams(columnValue, loadParams, RestLoad.of(entryColumnName));
            } else {
                entityList = findParamMapper.findAllByIdOrParams(columnValue, loadParams);
            }
        }
        return parseResult(entryKey, entityList);
    }

    /**
     * <code>destineLoadParams</code>
     * <p>The destine load params method.</p>
     * @param resultSet        {@link java.sql.ResultSet} <p>The result set parameter is <code>ResultSet</code> type.</p>
     * @param loadEntry        {@link java.util.Map.Entry} <p>The load entry parameter is <code>Entry</code> type.</p>
     * @param loadParamColumns {@link java.util.List} <p>The load param columns parameter is <code>List</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.load.RestParam} <p>The destine load params return object is <code>RestParam</code> type.</p>
     * @throws SQLException {@link java.sql.SQLException} <p>The sql exception is <code>SQLException</code> type.</p>
     * @see java.sql.ResultSet
     * @see java.util.Map.Entry
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.load.RestParam
     * @see java.sql.SQLException
     */
    private RestParam[] destineLoadParams(ResultSet resultSet, Map.Entry<Class<?>, MybatisColumn> loadEntry, List<MybatisColumn> loadParamColumns) throws SQLException {
        Class<?> entryKey = loadEntry.getKey();
        MybatisColumn entryValue = loadEntry.getValue();
        Set<String> entryKeys = entryValue.getLoadKeys();
        Map<String, Object> loadParams = new HashMap<>();
        for (MybatisColumn loadParam : loadParamColumns) {
            String columnName = loadParam.getColumn();
            String paramName = loadParam.getParamName();
            Set<String> loadParamKeys = loadParam.getLoadKeys();
            List<Class<?>> loadParamTypes = loadParam.getLoadTypes();
            if (GeneralUtils.isNotEmpty(loadParamTypes) && loadParamTypes.contains(entryKey)) {
                Object columnValue = resultSet.getObject(columnName);
                loadParams.putIfAbsent(paramName, columnValue);
                continue;
            }
            if (GeneralUtils.isNotEmpty(loadParamKeys)) {
                for (String loadParamKey : loadParamKeys) {
                    if (entryKeys.contains(loadParamKey)) {
                        Object columnValue = resultSet.getObject(columnName);
                        loadParams.putIfAbsent(paramName, columnValue);
                    }
                }
            }
        }
        return loadParams.entrySet().stream().map(RestParam::of).toArray(RestParam[]::new);
    }

    /**
     * <code>destineTableName</code>
     * <p>The destine tableName method.</p>
     * @param resultSet {@link java.sql.ResultSet} <p>The result set parameter is <code>ResultSet</code> type.</p>
     * @param loadTable {@link java.lang.String} <p>The load table parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The destine tableName return object is <code>String</code> type.</p>
     * @throws SQLException {@link java.sql.SQLException} <p>The sql exception is <code>SQLException</code> type.</p>
     * @see java.sql.ResultSet
     * @see java.lang.String
     * @see java.sql.SQLException
     */
    private String destineTableName(ResultSet resultSet, String loadTable) throws SQLException {
        String regex = "\\{[^}]*}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(loadTable);
        String result = loadTable;
        while (matcher.find()) {
            String param = matcher.group(0);
            String columnName = param.substring(1, param.length() - 1);
            if (GeneralUtils.isNotEmpty(columnName)) {
                String columnValue = resultSet.getString(columnName);
                if (GeneralUtils.isNotEmpty(columnValue)) {
                    result = loadTable.replaceFirst(regex, columnValue);
                }
            }
        }
        return result;
    }

    /**
     * <code>destineLoadEntry</code>
     * <p>The destine load entry method.</p>
     * @param columnName  {@link java.lang.String} <p>The column name parameter is <code>String</code> type.</p>
     * @param loadKeys    {@link java.util.List} <p>The load keys parameter is <code>List</code> type.</p>
     * @param loadColumns {@link java.util.Map} <p>The load columns parameter is <code>Map</code> type.</p>
     * @return {@link java.util.Map.Entry} <p>The destine load entry return object is <code>Entry</code> type.</p>
     * @see java.lang.String
     * @see java.util.List
     * @see java.util.Map
     * @see java.util.Map.Entry
     */
    private Map.Entry<Class<?>, MybatisColumn> destineLoadEntry(String columnName, List<String> loadKeys, Map<Class<?>, MybatisColumn> loadColumns) {
        if (GeneralUtils.isEmpty(loadKeys) || GeneralUtils.isEmpty(loadColumns)) {
            return null;
        }
        for (Map.Entry<Class<?>, MybatisColumn> entry : loadColumns.entrySet()) {
            Class<?> entryKey = entry.getKey();
            MybatisColumn entryValue = entry.getValue();
            Set<String> entryKeys = entryValue.getLoadKeys();
            if (GeneralUtils.isEmpty(entryKeys) || !entryKeys.contains(columnName)) {
                continue;
            }
            for (String loadKey : loadKeys) {
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
