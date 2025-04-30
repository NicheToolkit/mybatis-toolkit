package io.github.nichetoolkit.mybatis.test.fickle;

import io.github.nichetoolkit.rice.service.FilterService;
import io.github.nichetoolkit.rice.service.QueryFickleService;
import io.github.nichetoolkit.rice.service.SingleService;

/**
 * <code>FickleEntryService</code>
 * <p>The fickleEntry service interface.</p>
 * @see  FilterService
 * @see  SingleService
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface FickleEntryService extends FilterService<FickleEntryModel, FickleEntryFilter, String, String>,
        SingleService<FickleEntryModel, String, String>,
        QueryFickleService<FickleEntryModel, String, String> {
}
