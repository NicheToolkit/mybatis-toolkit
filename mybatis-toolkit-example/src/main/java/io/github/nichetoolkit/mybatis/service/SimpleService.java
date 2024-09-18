package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.SimpleFilter;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
import io.github.nichetoolkit.rice.service.FilterService;
import io.github.nichetoolkit.rice.service.SingleService;

/**
 * <code>SimpleService</code>
 * <p>The type simple service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.service.FilterService
 * @see io.github.nichetoolkit.rice.service.SingleService
 * @since Jdk1.8
 */
public interface SimpleService extends FilterService<SimpleModel, SimpleFilter,String, String>, SingleService<SimpleModel,String, String> {
}
