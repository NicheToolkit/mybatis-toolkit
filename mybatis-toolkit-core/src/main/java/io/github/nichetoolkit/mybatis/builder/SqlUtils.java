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

    public static List<Field> fieldsOfType(Class<?> declaredType, List<String> excludeFields, List<Class<?>> excludeSuperClasses) {
        List<Field> fieldList = new ArrayList<>();
        /* 未处理的需要获取字段 */
        Class<?> declaredClass = declaredType;
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

    public static <T> Map<Integer, List<T>> sliceOfType(List<T> typeList, List<Field> fieldList) {
        return typeList.stream()
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

    public static <T> boolean valueOfType(T type, List<Field> fieldList) {
        Optional<Boolean> logicalOrOptional = fieldList.stream().map(field -> {
            try {
                Object fieldValue = field.get(type);
                return GeneralUtils.isNotEmpty(fieldValue);
            } catch (IllegalAccessException ignored) {
                return false;
            }
        }).reduce(Boolean::logicalOr);
        return logicalOrOptional.orElse(false);
    }

    public static <T> String whereSqlOfTypes(Collection<T> types, Class<T> idType, StyleType styleType) {
        return whereSqlOfTypes(null,types, idType, styleType);
    }

    public static <T> String whereSqlOfTypes(String prefix, Collection<T> types, Class<T> type, StyleType styleType) {
        List<Field> fieldList = fieldsOfType(type, Collections.emptyList(), Collections.emptyList());
        if (GeneralUtils.isEmpty(fieldList)) {
            return SqlBuilder.EMPTY;
        }
        List<T> typeList = types.stream().filter(typeValue -> valueOfType(typeValue, fieldList)).collect(Collectors.toList());
        Map<Integer, List<T>> typesOfMap = sliceOfType(typeList, fieldList);
        return whereSqlOfTypes(prefix,typesOfMap, fieldList, styleType);
    }

    @SuppressWarnings("Duplicates")
    public static <T> String whereSqlOfTypes(String prefix, Map<Integer, List<T>> typesOfMap, List<Field> fieldList, StyleType styleType) {
        if (GeneralUtils.isEmpty(typesOfMap)) {
            return SqlBuilder.EMPTY;
        }
        SqlBuilder sqlBuilder = new SqlBuilder();
        sqlBuilder.and().braceLt();
        for (Map.Entry<Integer, List<T>> entry : typesOfMap.entrySet()) {
            Integer key = entry.getKey();
            List<T> valueList = entry.getValue();
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
                            sqlBuilder.braceLt();
                        }
                        boolean isNotFirst = false;
                        T value = valueList.get(0);
                        for (Field field : denexFields) {
                            try {
                                Object fieldValue = field.get(value);
                                String columnName = tableStyle.columnName(field);
                                if (GeneralUtils.isNotEmpty(prefix)) {
                                    columnName = prefix + columnName;
                                }
                                sqlBuilder.eq(columnName, fieldValue, isNotFirst ? true : null);
                                isNotFirst = true;
                            } catch (IllegalAccessException ignored) {
                            }
                        }
                        if (isMultiColumns) {
                            sqlBuilder.braceGt();
                        }
                    } else {
                        String fieldSql = denexFields.stream().map(field -> {
                            String columnName = tableStyle.columnName(field);
                            if (GeneralUtils.isNotEmpty(prefix)) {
                                columnName = prefix + columnName;
                            }
                            return columnName;
                        }).collect(Collectors.joining(SQLConstants.COMMA));
                        if (isMultiColumns) {
                            sqlBuilder.braceLt().append(fieldSql).braceGt().in().braceLt();
                        } else {
                            sqlBuilder.append(fieldSql).in().braceLt();
                        }
                        valueList.forEach(value -> {
                            if (isMultiColumns) {
                                sqlBuilder.braceLt();
                            }
                            denexFields.forEach(field -> {
                                try {
                                    Object indexValue = field.get(value);
                                    sqlBuilder.value(indexValue).comma();
                                } catch (IllegalAccessException ignored) {
                                }
                            });
                            sqlBuilder.deleteLastChar();
                            if (isMultiColumns) {
                                sqlBuilder.braceGt().comma();
                            } else {
                                sqlBuilder.comma();
                            }
                        });
                        sqlBuilder.deleteLastChar().braceGt();
                    }

                }
                sqlBuilder.or();
            }
        }
        sqlBuilder.delete(sqlBuilder.length() - 4).braceGt();
        return sqlBuilder.toString();
    }
}
