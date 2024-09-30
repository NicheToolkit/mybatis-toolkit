package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;

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
                HANDLERS = ServiceLoaderHelper.instances(MybatisHandler.class);
            }
            return HANDLERS;
        }
    }
}
