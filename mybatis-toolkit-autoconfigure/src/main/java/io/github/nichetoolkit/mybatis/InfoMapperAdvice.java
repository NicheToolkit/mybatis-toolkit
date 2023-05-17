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

package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.mapper.MybatisInfoMapper;
import io.github.nichetoolkit.mybatis.mapper.MybatisSuperMapper;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.InfoEntity;

/**
 * <p>InfoMapperAdvice</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface InfoMapperAdvice<M extends MybatisInfoMapper<E, I>, E extends InfoEntity<I>, I> extends MybatisEntityMapper<E> {

  default M infoMapper() {
    return MybatisMapperProvider.<M,E, I>defaultInstance().superMapper(clazz());
  }

}
