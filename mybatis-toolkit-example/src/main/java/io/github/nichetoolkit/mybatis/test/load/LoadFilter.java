package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.rice.DefaultFilter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <code>LoadFilter</code>
 * <p>The load filter class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultFilter
 * @since Jdk1.8
 */
@SuperBuilder
@NoArgsConstructor
public class LoadFilter extends DefaultFilter<LoadIdentity, String> {
}
