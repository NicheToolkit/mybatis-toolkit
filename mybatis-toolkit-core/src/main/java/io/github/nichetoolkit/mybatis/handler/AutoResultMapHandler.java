package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.mybatis.MybatisOrder;
import io.github.nichetoolkit.mybatis.MybatisTable;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * <code>AutoResultMapHandler</code>
 * <p>The auto result map handler interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface AutoResultMapHandler extends MybatisOrder {

    /**
     * <code>getTypeHandlerInstance</code>
     * <p>The get type handler instance getter method.</p>
     * @param javaTypeClass    {@link java.lang.Class} <p>The java type class parameter is <code>Class</code> type.</p>
     * @param typeHandlerClass {@link java.lang.Class} <p>The type handler class parameter is <code>Class</code> type.</p>
     * @return {@link org.apache.ibatis.type.TypeHandler} <p>The get type handler instance return object is <code>TypeHandler</code> type.</p>
     * @see java.lang.Class
     * @see org.apache.ibatis.type.TypeHandler
     */
    static TypeHandler<?> getTypeHandlerInstance(Class<?> javaTypeClass, Class<?> typeHandlerClass) {
        if (javaTypeClass != null) {
            try {
                Constructor<?> c = typeHandlerClass.getConstructor(Class.class);
                return (TypeHandler<?>) c.newInstance(javaTypeClass);
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                throw new TypeException("It is failed invoking constructor for handler " + typeHandlerClass, e);
            }
        }
        try {
            Constructor<?> c = typeHandlerClass.getConstructor();
            return (TypeHandler<?>) c.newInstance();
        } catch (Exception e) {
            throw new TypeException("It is unable to find a usable constructor for " + typeHandlerClass, e);
        }
    }

    /**
     * <code>supports</code>
     * <p>The supports method.</p>
     * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @return boolean <p>The supports return object is <code>boolean</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    boolean supports(MybatisTable mybatisTable);

    /**
     * <code>autoResultMapHandler</code>
     * <p>The auto result map handler method.</p>
     * @param configuration  {@link org.apache.ibatis.session.Configuration} <p>The configuration parameter is <code>Configuration</code> type.</p>
     * @param mybatisTable   {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @param resultMappings {@link java.util.List} <p>The result mappings parameter is <code>List</code> type.</p>
     * @see org.apache.ibatis.session.Configuration
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.util.List
     */
    void autoResultMapHandler(Configuration configuration, MybatisTable mybatisTable, List<ResultMapping>  resultMappings);
}
