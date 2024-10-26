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

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisInfoMapper;
import io.github.nichetoolkit.rice.InfoEntity;

public interface MybatisInfoRecord<M extends MybatisInfoMapper<E, I>, E extends InfoEntity<I>, I> extends MybatisMapper<E> {

    default M infoMapper() {
    return MybatisRecordProvider.<M,E, I>defaultInstance().superMapper(mapperType());
  }

}
