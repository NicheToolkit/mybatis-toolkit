package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.SimpleFilter;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
import io.github.nichetoolkit.rice.service.FilterService;
import io.github.nichetoolkit.rice.service.extend.SingleService;

/**
 * <p>SimpleService</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface SimpleService extends FilterService<String, String, SimpleModel, SimpleFilter>, SingleService<String, String, SimpleModel> {
}
