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

@RestSkip
@CrossOrigin
@RestNotelog
@RestController
@RequestMapping("/simple")
public class SimpleController {

    private final SimpleService simpleService;

    @Autowired
    public SimpleController(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    @PostMapping("/create")
    public RestResult<SimpleModel> create(@RequestBody SimpleModel simpleModel) throws RestException {
        return RestResult.success(simpleService.create(simpleModel));
    }

    @PostMapping("/update")
    public RestResult<SimpleModel> update(@RequestBody SimpleModel simpleModel) throws RestException {
        return RestResult.success(simpleService.update(simpleModel));
    }

    @GetMapping("/query/{tablekey}")
    public RestResult<SimpleModel> queryById(@RequestParam("id") String id, @PathVariable("tablekey") String tablekey) throws RestException {
        SimpleModel simpleModel = simpleService.queryById(tablekey, id);
        return RestResult.success(simpleModel);
    }

    @PostMapping("/query/filter")
    public RestResult<RestPage<SimpleModel>> queryByFilter(@RequestBody SimpleFilter filter) throws RestException {
        RestPage<SimpleModel> restPage = simpleService.queryAllWithFilter(filter);
        return RestResult.success(restPage);
    }

    @DeleteMapping("/delete/{tablekey}")
    public RestResult<?> deleteById(@RequestParam("id") String id, @PathVariable("tablekey") String tablekey) throws RestException {
        simpleService.deleteById(tablekey, id);
        return RestResult.success();
    }

    @PostMapping("/delete/filter")
    public RestResult<?> deleteByFilter(@RequestBody SimpleFilter filter) throws RestException {
        simpleService.deleteAllWithFilter(filter);
        return RestResult.success();
    }
}
