package io.github.nichetoolkit.mybatis.helper;

import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>MybatisHelper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Component
public class MybatisHelper implements InitializingBean {

    @Autowired
    private MybatisTableProperties tableProperties;

    private static MybatisHelper INSTANCE = null;

    public static MybatisHelper instance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }

    public static MybatisTableProperties getTableProperties() {
        return INSTANCE.tableProperties;
    }

}
