package io.github.nichetoolkit.mybatis.wrapper;

import io.github.nichetoolkit.mybatis.MybatisSqlScriptWrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.scripting.xmltags.DynamicContext;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <code>AnnotationSqlWrapper</code>
 * <p>The type annotation sql wrapper class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlScriptWrapper
 * @since Jdk1.8
 */
public abstract class AnnotationSqlWrapper implements MybatisSqlScriptWrapper {

    /**
     * <code>type</code>
     * {@link java.lang.annotation.ElementType} <p>the <code>type</code> field.</p>
     * @see java.lang.annotation.ElementType
     */
    protected final ElementType type;
    /**
     * <code>target</code>
     * {@link java.lang.Object} <p>the <code>target</code> field.</p>
     * @see java.lang.Object
     */
    protected final Object target;
    /**
     * <code>annotations</code>
     * {@link java.lang.annotation.Annotation} <p>the <code>annotations</code> field.</p>
     * @see java.lang.annotation.Annotation
     */
    protected final Annotation[] annotations;

    /**
     * <code>AnnotationSqlWrapper</code>
     * Instantiates a new annotation sql wrapper.
     * @param target      {@link java.lang.Object} <p>the target parameter is <code>Object</code> type.</p>
     * @param type        {@link java.lang.annotation.ElementType} <p>the type parameter is <code>ElementType</code> type.</p>
     * @param annotations {@link java.lang.annotation.Annotation} <p>the annotations parameter is <code>Annotation</code> type.</p>
     * @see java.lang.Object
     * @see java.lang.annotation.ElementType
     * @see java.lang.annotation.Annotation
     */
    public AnnotationSqlWrapper(Object target, ElementType type, Annotation[] annotations) {
        this.type = type;
        this.target = target;
        this.annotations = annotations;
    }

    /**
     * <code>elementType</code>
     * <p>the type method.</p>
     * @return {@link java.lang.annotation.ElementType} <p>the type return object is <code>ElementType</code> type.</p>
     * @see java.lang.annotation.ElementType
     */
    public ElementType elementType() {
        return type;
    }

    /**
     * <code>target</code>
     * <p>the method.</p>
     * @return {@link java.lang.Object} <p>the return object is <code>Object</code> type.</p>
     * @see java.lang.Object
     */
    public Object target() {
        return target;
    }

    /**
     * <code>annotations</code>
     * <p>the method.</p>
     * @return {@link java.lang.annotation.Annotation} <p>the return object is <code>Annotation</code> type.</p>
     * @see java.lang.annotation.Annotation
     */
    public Annotation[] annotations() {
        return annotations;
    }

    /**
     * <code>getParameterName</code>
     * <p>the parameter name getter method.</p>
     * @param parameter {@link java.lang.reflect.Parameter} <p>the parameter parameter is <code>Parameter</code> type.</p>
     * @return {@link java.lang.String} <p>the parameter name return object is <code>String</code> type.</p>
     * @see java.lang.reflect.Parameter
     * @see java.lang.String
     */
    public String getParameterName(Parameter parameter) {
        /** 优先使用 @Param 注解指定的值 */
        Optional<Annotation> paramOptional = Stream.of(annotations).filter(a -> a.annotationType() == Param.class).findFirst();
        if (paramOptional.isPresent()) {
            return ((Param) paramOptional.get()).value();
        }
        Executable executable = parameter.getDeclaringExecutable();
        /** 只有一个参数时，只能使用默认名称 */
        if (executable.getParameterCount() == 1) {
            return DynamicContext.PARAMETER_OBJECT_KEY;
        }
        /** 参数名 */
        String name = parameter.getName();
        if (!name.startsWith("arg")) {
            return name;
        }
        /** 获取参数顺序号 */
        int index = 0;
        Parameter[] parameters = executable.getParameters();
        for (; index < parameters.length; index++) {
            if (parameters[index] == parameter) {
                break;
            }
        }
        /** 如果方法不是默认名，就直接使用该名称 */
        if (!name.equals("arg" + index)) {
            return name;
        } else {
            return ParamNameResolver.GENERIC_NAME_PREFIX + (index + 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnotationSqlWrapper that = (AnnotationSqlWrapper) o;
        return type == that.type && target.equals(that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, target);
    }
}
