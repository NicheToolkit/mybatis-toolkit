package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.identity.IdentityUtils;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.RestIdResolver;
import org.springframework.stereotype.Component;

/**
 * <code>DefaultStringIdResolver</code>
 * <p>The type default string id resolver class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see RestIdResolver
 * @see Component
 * @since Jdk1.8
 */
@Component
public class TemplateIdentityResolver implements RestIdResolver<TemplateIdentity> {

    @Override
    public <M extends RestId<TemplateIdentity>> TemplateIdentity resolveId(M model) throws RestException {
        return new TemplateIdentity(IdentityUtils.generateString(),IdentityUtils.generateString());
    }
}
