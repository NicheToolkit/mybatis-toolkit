package io.github.nichetoolkit.mybatis;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

public interface MybatisHandler extends MybatisOrder {

    static void ofHandle(MybatisTable table, MappedStatement statement, ProviderContext context) {
        for (MybatisHandler handler : Instance.handlerChain()) {
            handler.handle(table, statement, context);
        }
    }

    void handle(MybatisTable table, MappedStatement statement, ProviderContext context);

    class Instance {

        private static List<MybatisHandler> HANDLERS;

        public static List<MybatisHandler> handlerChain() {
            if (HANDLERS == null) {
                HANDLERS = SpringFactoriesLoader.loadFactories(MybatisHandler.class, null);
            }
            return HANDLERS;
        }
    }
}
