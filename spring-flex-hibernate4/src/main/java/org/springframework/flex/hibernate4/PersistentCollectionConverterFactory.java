/*
 * Copyright 2002-2011 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.flex.hibernate4;

import org.hibernate.Hibernate;
import org.hibernate.collection.spi.PersistentCollection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * {@link ConverterFactory} implementation that supplies a {@link Converter} instance that can convert {@link PersistentCollection} 
 * instances from Hibernate.  Given a specific {@code PersistentCollection} instance, the converter will:
 * 
 * <ul>
 *     <li>Convert to null if the {@code PersistentCollection} instance is uninitialized</li>
 *     <li>Convert to the wrapped collection class if the {@code PersistentCollection} is initialized</li> 
 * </ul>
 *
 * @author Jeremy Grelle
 */
public class PersistentCollectionConverterFactory implements ConverterFactory<PersistentCollection, Object> {

    public <T> Converter<PersistentCollection, T> getConverter(Class<T> targetType) {
        return new Converter<PersistentCollection, T>() {
            @SuppressWarnings("unchecked")
            public T convert(PersistentCollection source) {
                if (!Hibernate.isInitialized(source)) {
                    return null;
                } else {
                    return (T) source.getValue();
                }
            }  
        };
    }

}
