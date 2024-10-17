package io.github.nichetoolkit.mybatis.builder;

import io.github.nichetoolkit.mybatis.MybatisTableStyle;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.RestReckon;
import io.github.nichetoolkit.rest.util.GeneralUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class SqlUtils {

    public static void reverseArray(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }

    public static List<Field> fieldsOfIdentity(Class<?> identityType, List<String> excludeFields, List<Class<?>> excludeSuperClasses) {
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

    public static <I> Map<Integer, List<I>> sliceOfIdentity(List<I> idList, List<Field> fieldList) {
        return idList.stream()
                .collect(Collectors.groupingBy(id -> {
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

    public static <I> boolean valueOfIdentity(I id, List<Field> fieldList) {
        Optional<Boolean> logicalOrOptional = fieldList.stream().map(field -> {
            try {
                Object fieldValue = field.get(id);
                return GeneralUtils.isNotEmpty(fieldValue);
            } catch (IllegalAccessException ignored) {
                return false;
            }
        }).reduce(Boolean::logicalOr);
        return logicalOrOptional.orElse(false);
    }

    public static <I> String whereSqlOfIdentities(Collection<I> ids, Class<I> idType, StyleType styleType) {
        List<Field> fieldList = fieldsOfIdentity(idType, Collections.emptyList(), Collections.emptyList());
        if (GeneralUtils.isEmpty(fieldList)) {
            return SqlBuilder.EMPTY;
        }
        List<I> idList = ids.stream().filter(id -> valueOfIdentity(id, fieldList)).collect(Collectors.toList());
        Map<Integer, List<I>> identitiesOfMap = sliceOfIdentity(idList, fieldList);
        return whereSqlOfIdentities(identitiesOfMap, fieldList, styleType);
    }

    @SuppressWarnings("Duplicates")
    public static <I> String whereSqlOfIdentities(Map<Integer, List<I>> idsOfMap, List<Field> fieldList, StyleType styleType) {
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
                    List<Field> denexFields = indices.stream()
                            .map(index -> fieldList.get(index.intValue()))
                            .collect(Collectors.toList());
                    MybatisTableStyle tableStyle = MybatisTableStyle.style(styleType);
                    boolean isMultiColumns = denexFields.size() > 1;
                    boolean isSingleValue = valueList.size() == 1;
                    if (isSingleValue) {
                        if (isMultiColumns) {
                            sqlBuilder.append(SQLConstants.BRACE_LT);
                        }
                        boolean isNotFirst = false;
                        I value = valueList.get(0);
                        for (Field field : denexFields) {
                            try {
                                Object fieldValue = field.get(value);
                                sqlBuilder.eq(tableStyle.columnName(field), fieldValue, isNotFirst ? true : null);
                                isNotFirst = true;
                            } catch (IllegalAccessException ignored) {
                            }
                        }
                        if (isMultiColumns) {
                            sqlBuilder.append(SQLConstants.BRACE_GT);
                        }
                    } else {
                        String fieldSql = denexFields.stream().map(tableStyle::columnName).collect(Collectors.joining(SQLConstants.COMMA));
                        if (isMultiColumns) {
                            sqlBuilder.append(SQLConstants.BRACE_LT).append(fieldSql).append(SQLConstants.BRACE_GT).in().append(SQLConstants.BRACE_LT);
                        } else {
                            sqlBuilder.append(fieldSql).in().append(SQLConstants.BRACE_LT);
                        }
                        valueList.forEach(value -> {
                            if (isMultiColumns) {
                                sqlBuilder.append(SQLConstants.BRACE_LT);
                            }
                            denexFields.forEach(field -> {
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

                }
                sqlBuilder.or();
            }
        }
        sqlBuilder.delete(sqlBuilder.length() - 4, sqlBuilder.length()).append(SQLConstants.BRACE_GT);
        return sqlBuilder.toString();
    }
}
