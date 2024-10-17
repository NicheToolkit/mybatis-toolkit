package io.github.nichetoolkit.mybatis.builder;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestReckon;
import io.github.nichetoolkit.rest.stream.RestCollectors;
import io.github.nichetoolkit.rest.stream.RestStream;
import io.github.nichetoolkit.rest.util.GeneralUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * <code>SqlUtils</code>
 * <p>The type sql utils class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class SqlUtils {

    /**
     * <code>reverseArray</code>
     * <p>The array method.</p>
     * @param array {@link java.lang.Object} <p>The array parameter is <code>Object</code> type.</p>
     * @see java.lang.Object
     */
    public static void reverseArray(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }

    /**
     * <code>fieldsOfIdentity</code>
     * <p>The of identity method.</p>
     * @param identityType        {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
     * @param excludeFields       {@link java.util.List} <p>The exclude fields parameter is <code>List</code> type.</p>
     * @param excludeSuperClasses {@link java.util.List} <p>The exclude super classes parameter is <code>List</code> type.</p>
     * @return {@link java.util.List} <p>The of identity return object is <code>List</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.Class
     * @see java.util.List
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static List<Field> fieldsOfIdentity(Class<?> identityType, List<String> excludeFields, List<Class<?>> excludeSuperClasses) throws RestException {
        List<Field> fieldList = new ArrayList<>();
        /* 未处理的需要获取字段 */
        Class<?> declaredClass = identityType;
        boolean isSuperclass = true;
        while (declaredClass != null && declaredClass != Object.class) {
            Field[] declaredFields = declaredClass.getDeclaredFields();
            reverseArray(declaredFields);
            for (Field declaredField : declaredFields) {
                int modifiers = declaredField.getModifiers();
                /* 排除 static 和 transient 修饰的字段 */
                if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                    /* 是否需要排除字段 */
                    if (GeneralUtils.isNotEmpty(excludeFields) && excludeFields.contains(declaredField.getName())) {
                        continue;
                    }
                    declaredField.setAccessible(true);
                    fieldList.add(declaredField);
                }
            }
            /* 排除父类,迭代获取父类 */
            do {
                declaredClass = declaredClass.getSuperclass();
            } while (GeneralUtils.isNotEmpty(excludeSuperClasses) && excludeSuperClasses.contains(declaredClass) && declaredClass != Object.class);
        }
        return fieldList;
    }

    /**
     * <code>sliceOfIdentity</code>
     * <p>The of identity method.</p>
     * @param <I>       {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param idList    {@link java.util.List} <p>The id list parameter is <code>List</code> type.</p>
     * @param fieldList {@link java.util.List} <p>The field list parameter is <code>List</code> type.</p>
     * @return {@link java.util.Map} <p>The of identity return object is <code>Map</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.List
     * @see java.util.Map
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> Map<Integer, List<I>> sliceOfIdentity(List<I> idList, List<Field> fieldList) throws RestException {
        return RestStream.stream(idList)
                .collect(RestCollectors.groupingBy(id -> {
                    int indexValue = 0;
                    for (int index = 0; index < fieldList.size(); index++) {
                        Field field = fieldList.get(index);
                        Object object = null;
                        try {
                            object = field.get(id);
                        } catch (IllegalAccessException ignored) {
                        }
                        if (GeneralUtils.isValid(object)) {
                            indexValue = indexValue | -(-1 << index);
                        }
                    }
                    return indexValue;
                }));
    }

    /**
     * <code>valueOfIdentity</code>
     * <p>The of identity method.</p>
     * @param <I>       {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param id        I <p>The id parameter is <code>I</code> type.</p>
     * @param fieldList {@link java.util.List} <p>The field list parameter is <code>List</code> type.</p>
     * @return boolean <p>The of identity return object is <code>boolean</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> boolean valueOfIdentity(I id, List<Field> fieldList) throws RestException {
        return RestStream.stream(fieldList).map(field -> {
            try {
                Object fieldValue = field.get(id);
                return GeneralUtils.isNotEmpty(fieldValue);
            } catch (IllegalAccessException ignored) {
                return false;
            }
        }).collect(RestCollectors.logicalOr());
    }

    /**
     * <code>whereSqlOfIdentities</code>
     * <p>The sql of identities method.</p>
     * @param <I>       {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param ids       {@link java.util.Collection} <p>The ids parameter is <code>Collection</code> type.</p>
     * @param idType    {@link java.lang.Class} <p>The id type parameter is <code>Class</code> type.</p>
     * @param styleType {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>The style type parameter is <code>StyleType</code> type.</p>
     * @return {@link java.lang.String} <p>The sql of identities return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.Collection
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String whereSqlOfIdentities(Collection<I> ids, Class<I> idType, StyleType styleType) throws RestException {
        List<Field> fieldList = fieldsOfIdentity(idType, Collections.emptyList(), Collections.emptyList());
        if (GeneralUtils.isEmpty(fieldList)) {
            return SqlBuilder.EMPTY;
        }
        List<I> idList = RestStream.stream(ids).filter(id -> valueOfIdentity(id, fieldList))
                .collect(RestCollectors.toList());
        Map<Integer, List<I>> identitiesOfMap = sliceOfIdentity(idList, fieldList);
        return whereSqlOfIdentities(identitiesOfMap, fieldList, styleType);
    }

    /**
     * <code>whereSqlOfIdentities</code>
     * <p>The sql of identities method.</p>
     * @param <I>       {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param idsOfMap  {@link java.util.Map} <p>The ids of map parameter is <code>Map</code> type.</p>
     * @param fieldList {@link java.util.List} <p>The field list parameter is <code>List</code> type.</p>
     * @param styleType {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>The style type parameter is <code>StyleType</code> type.</p>
     * @return {@link java.lang.String} <p>The sql of identities return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.Map
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     * @see java.lang.String
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
     */
    @SuppressWarnings("Duplicates")
    public static <I> String whereSqlOfIdentities(Map<Integer, List<I>> idsOfMap, List<Field> fieldList, StyleType styleType) throws RestException {
        if (GeneralUtils.isEmpty(idsOfMap)) {
            return SqlBuilder.EMPTY;
        }
        SqlBuilder sqlBuilder = new SqlBuilder(SQLConstants.BRACE_LT);
        for (Map.Entry<Integer, List<I>> entry : idsOfMap.entrySet()) {
            Integer key = entry.getKey();
            List<I> valueList = entry.getValue();
            if (GeneralUtils.isNotEmpty(key) && GeneralUtils.isNotEmpty(valueList)) {
                List<Number> indices = RestReckon.denexNumber(key);
                if (GeneralUtils.isNotEmpty(indices)) {
                    List<Field> denexFields = RestStream.stream(indices)
                            .map(index -> fieldList.get(index.intValue()))
                            .collect(RestCollectors.toList());
                    MybatisTableStyle tableStyle = MybatisTableStyle.style(styleType);
                    String fieldSql = RestStream.stream(denexFields)
                            .map(tableStyle::columnName)
                            .collect(RestCollectors.joining(SQLConstants.COMMA));
                    boolean isMultiColumns = denexFields.size() > 1;
                    if (isMultiColumns) {
                        sqlBuilder.append(SQLConstants.BRACE_LT).append(fieldSql).append(SQLConstants.BRACE_GT).in().append(SQLConstants.BRACE_LT);
                    } else {
                        sqlBuilder.append(fieldSql).in().append(SQLConstants.BRACE_LT);
                    }
                    RestStream.stream(valueList).forEach(value -> {
                        if (isMultiColumns) {
                            sqlBuilder.append(SQLConstants.BRACE_LT);
                        }
                        RestStream.stream(denexFields).forEach(field -> {
                            try {
                                Object indexValue = field.get(value);
                                sqlBuilder.value(indexValue).append(SQLConstants.COMMA);
                            } catch (IllegalAccessException ignored) {
                            }
                        });
                        sqlBuilder.deleteLastChar();
                        if (isMultiColumns) {
                            sqlBuilder.append(SQLConstants.BRACE_GT).append(SQLConstants.COMMA);
                        } else {
                            sqlBuilder.append(SQLConstants.COMMA);
                        }
                    });
                    sqlBuilder.deleteLastChar().append(SQLConstants.BRACE_GT);
                }
                sqlBuilder.or();
            }
        }
        sqlBuilder.delete(sqlBuilder.length() - 4, sqlBuilder.length()).append(SQLConstants.BRACE_GT);
        return sqlBuilder.toString();
    }
}
