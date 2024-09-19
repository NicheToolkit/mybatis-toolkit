package io.github.nichetoolkit.mybatis.controller;

import io.github.nichetoolkit.mybatis.service.SimpleService;
import io.github.nichetoolkit.mybatis.simple.SimpleFilter;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestResult;
import io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog;
import io.github.nichetoolkit.rice.RestPage;
import io.github.nichetoolkit.rice.stereotype.RestSkip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <code>SimpleController</code>
 * <p>The type simple controller class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.stereotype.RestSkip
 * @see org.springframework.web.bind.annotation.CrossOrigin
 * @see io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @since Jdk1.8
 */
@RestSkip
@CrossOrigin
@RestNotelog
@RestController
@RequestMapping("/simple")
public class SimpleController {

    /**
     * <code>simpleService</code>
     * {@link io.github.nichetoolkit.mybatis.service.SimpleService} <p>the <code>simpleService</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.service.SimpleService
     */
    private final SimpleService simpleService;

    /**
     * <code>SimpleController</code>
     * Instantiates a new simple controller.
     * @param simpleService {@link io.github.nichetoolkit.mybatis.service.SimpleService} <p>the simple service parameter is <code>SimpleService</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.service.SimpleService
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    public SimpleController(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    /**
     * <code>create</code>
     * <p>the method.</p>
     * @param simpleModel {@link io.github.nichetoolkit.mybatis.simple.SimpleModel} <p>the simple model parameter is <code>SimpleModel</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>the return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.SimpleModel
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/create")
    public RestResult<SimpleModel> create(@RequestBody SimpleModel simpleModel) throws RestException {
        return RestResult.success(simpleService.create(simpleModel));
    }

    /**
     * <code>update</code>
     * <p>the method.</p>
     * @param simpleModel {@link io.github.nichetoolkit.mybatis.simple.SimpleModel} <p>the simple model parameter is <code>SimpleModel</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>the return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.SimpleModel
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/update")
    public RestResult<SimpleModel> update(@RequestBody SimpleModel simpleModel) throws RestException {
        return RestResult.success(simpleService.update(simpleModel));
    }

    /**
     * <code>queryById</code>
     * <p>the by id method.</p>
     * @param id       {@link java.lang.String} <p>the id parameter is <code>String</code> type.</p>
     * @param tablekey {@link java.lang.String} <p>the tablekey parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>the by id return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.web.bind.annotation.PathVariable
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.GetMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @GetMapping("/query/{tablekey}")
    public RestResult<SimpleModel> queryById(@RequestParam("id") String id, @PathVariable("tablekey") String tablekey) throws RestException {
        SimpleModel simpleModel = simpleService.queryById(tablekey, id);
        return RestResult.success(simpleModel);
    }

    /**
     * <code>queryByFilter</code>
     * <p>the by filter method.</p>
     * @param filter {@link io.github.nichetoolkit.mybatis.simple.SimpleFilter} <p>the filter parameter is <code>SimpleFilter</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>the by filter return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.SimpleFilter
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/query/filter")
    public RestResult<RestPage<SimpleModel>> queryByFilter(@RequestBody SimpleFilter filter) throws RestException {
        RestPage<SimpleModel> restPage = simpleService.queryAllWithFilter(filter);
        return RestResult.success(restPage);
    }

    /**
     * <code>deleteById</code>
     * <p>the by id method.</p>
     * @param id       {@link java.lang.String} <p>the id parameter is <code>String</code> type.</p>
     * @param tablekey {@link java.lang.String} <p>the tablekey parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>the by id return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.web.bind.annotation.RequestParam
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.DeleteMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @DeleteMapping("/delete/{tablekey}")
    public RestResult<?> deleteById(@RequestParam("id") String id, @PathVariable("tablekey") String tablekey) throws RestException {
        simpleService.deleteById(tablekey, id);
        return RestResult.success();
    }

    /**
     * <code>deleteByFilter</code>
     * <p>the by filter method.</p>
     * @param filter {@link io.github.nichetoolkit.mybatis.simple.SimpleFilter} <p>the filter parameter is <code>SimpleFilter</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>the by filter return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.SimpleFilter
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/delete/filter")
    public RestResult<?> deleteByFilter(@RequestBody SimpleFilter filter) throws RestException {
        simpleService.deleteAllWithFilter(filter);
        return RestResult.success();
    }
}
