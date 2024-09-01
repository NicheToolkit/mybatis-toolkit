package io.github.nichetoolkit.mybatis.stereotype.table;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <code>RestUnionKeys</code>
 * <p>The type rest union keys interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Documented
 * @see org.springframework.stereotype.Indexed
 * @since Jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Indexed
public @interface RestUnionKeys {

    /**
     * <code>value</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("unionKeys")
    String[] value() default {};

    /**
     * <code>unionKeys</code>
     * <p>the keys method.</p>
     * @return {@link java.lang.String} <p>the keys return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("value")
    String[] unionKeys() default {};


    /**
     * <code>ignores</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    String[] ignores() default {};

    /**
     * <code>unionIdentity</code>
     * <p>the identity method.</p>
     * @return boolean <p>the identity return object is <code>boolean</code> type.</p>
     */
    boolean unionIdentity() default true;

}
