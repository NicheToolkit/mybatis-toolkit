/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.nichetoolkit.mybatis.record;

import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.mapper.MybatisInfoMapper;
import io.github.nichetoolkit.rice.InfoEntity;

/**
 * <code>MybatisInfoRecord</code>
 * <p>The type mybatis info record interface.</p>
 * @param <M> {@link io.github.nichetoolkit.mybatis.mapper.MybatisInfoMapper} <p>the generic parameter is <code>MybatisInfoMapper</code> type.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.InfoEntity} <p>the generic parameter is <code>InfoEntity</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.mapper.MybatisInfoMapper
 * @see io.github.nichetoolkit.rice.InfoEntity
 * @see io.github.nichetoolkit.mybatis.MybatisEntityMapper
 * @since Jdk1.8
 */
public interface MybatisInfoRecord<M extends MybatisInfoMapper<E, I>, E extends InfoEntity<I>, I> extends MybatisEntityMapper<E> {

    /**
     * <code>infoMapper</code>
     * <p>the mapper method.</p>
     * @return M <p>the mapper return object is <code>M</code> type.</p>
     */
    default M infoMapper() {
    return MybatisRecordProvider.<M,E, I>defaultInstance().superMapper(clazz());
  }

}
