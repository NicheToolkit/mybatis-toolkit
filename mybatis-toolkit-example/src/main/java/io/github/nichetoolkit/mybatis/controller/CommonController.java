package io.github.nichetoolkit.mybatis.controller;

import io.github.nichetoolkit.mybatis.service.CommonService;
import io.github.nichetoolkit.mybatis.simple.CommonFilter;
import io.github.nichetoolkit.mybatis.simple.CommonModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestResult;
import io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog;
import io.github.nichetoolkit.rice.RestFilter;
import io.github.nichetoolkit.rice.RestPage;
import io.github.nichetoolkit.rice.stereotype.RestSkip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <code>CommonController</code>
 * <p>The common controller class.</p>
 * @see  io.github.nichetoolkit.rice.stereotype.RestSkip
 * @see  org.springframework.web.bind.annotation.CrossOrigin
 * @see  io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog
 * @see  org.springframework.web.bind.annotation.RestController
 * @see  org.springframework.web.bind.annotation.RequestMapping
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@RestSkip
@CrossOrigin
@RestNotelog
@RestController
@RequestMapping("/common")
public class CommonController {

    /**
     * <code>commonService</code>
     * {@link io.github.nichetoolkit.mybatis.service.CommonService} <p>The <code>commonService</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.service.CommonService
     */
    private final CommonService commonService;

    /**
     * <code>CommonController</code>
     * <p>Instantiates a new common controller.</p>
     * @param commonService {@link io.github.nichetoolkit.mybatis.service.CommonService} <p>The common service parameter is <code>CommonService</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.service.CommonService
     * @see  org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * <code>create</code>
     * <p>The create method.</p>
     * @param commonModel {@link io.github.nichetoolkit.mybatis.simple.CommonModel} <p>The common model parameter is <code>CommonModel</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.simple.CommonModel
     * @see  org.springframework.web.bind.annotation.RequestBody
     * @see  io.github.nichetoolkit.rest.RestResult
     * @see  org.springframework.web.bind.annotation.PostMapping
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.RestResult} <p>The create return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @PostMapping("/create")
    public RestResult<CommonModel> create(@RequestBody CommonModel commonModel) throws RestException {
        return RestResult.success(commonService.create(commonModel));
    }

    /**
     * <code>update</code>
     * <p>The update method.</p>
     * @param commonModel {@link io.github.nichetoolkit.mybatis.simple.CommonModel} <p>The common model parameter is <code>CommonModel</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.simple.CommonModel
     * @see  org.springframework.web.bind.annotation.RequestBody
     * @see  io.github.nichetoolkit.rest.RestResult
     * @see  org.springframework.web.bind.annotation.PostMapping
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.RestResult} <p>The update return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @PostMapping("/update")
    public RestResult<CommonModel> update(@RequestBody CommonModel commonModel) throws RestException {
        return RestResult.success(commonService.update(commonModel));
    }

    /**
     * <code>queryById</code>
     * <p>The query by id method.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @see  org.springframework.web.bind.annotation.PathVariable
     * @see  io.github.nichetoolkit.rest.RestResult
     * @see  org.springframework.web.bind.annotation.GetMapping
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.RestResult} <p>The query by id return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @GetMapping("/query/{id}")
    public RestResult<CommonModel> queryById(@PathVariable("id") String id) throws RestException {
        CommonModel commonModel = commonService.queryById(id);
        return RestResult.success(commonModel);
    }

    /**
     * <code>queryByFilter</code>
     * <p>The query by filter method.</p>
     * @param filter {@link io.github.nichetoolkit.mybatis.simple.CommonFilter} <p>The filter parameter is <code>CommonFilter</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.simple.CommonFilter
     * @see  org.springframework.web.bind.annotation.RequestBody
     * @see  io.github.nichetoolkit.rest.RestResult
     * @see  org.springframework.web.bind.annotation.PostMapping
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.RestResult} <p>The query by filter return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @PostMapping("/query/filter")
    public RestResult<RestPage<CommonModel>> queryByFilter(@RequestBody CommonFilter filter) throws RestException {
        RestPage<CommonModel> restPage = commonService.queryAllWithFilter(filter);
        return RestResult.success(restPage);
    }

    /**
     * <code>deleteById</code>
     * <p>The delete by id method.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @see  org.springframework.web.bind.annotation.PathVariable
     * @see  io.github.nichetoolkit.rest.RestResult
     * @see  org.springframework.web.bind.annotation.DeleteMapping
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.RestResult} <p>The delete by id return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @DeleteMapping("/delete/{id}")
    public RestResult<?> deleteById(@PathVariable("id") String id) throws RestException {
        commonService.deleteById(id);
        return RestResult.success();
    }

    /**
     * <code>deleteByFilter</code>
     * <p>The delete by filter method.</p>
     * @param filter {@link io.github.nichetoolkit.mybatis.simple.CommonFilter} <p>The filter parameter is <code>CommonFilter</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.simple.CommonFilter
     * @see  org.springframework.web.bind.annotation.RequestBody
     * @see  io.github.nichetoolkit.rest.RestResult
     * @see  org.springframework.web.bind.annotation.PostMapping
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.RestResult} <p>The delete by filter return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @PostMapping("/delete/filter")
    public RestResult<?> deleteByFilter(@RequestBody CommonFilter filter) throws RestException {
        commonService.deleteAllWithFilter(filter);
        return RestResult.success();
    }
}
