/*
 * Copyright 2013 Román Pineda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.upaas.projects.wui.models;

import com.upaas.projects.domain.model.kpi.KpiConfig;
import com.upaas.projects.domain.model.shared.NumericId;
import com.upaas.projects.repository.kpi.KpiConfigRepository;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 *
 * @author Román Pineda
 */
public class DetachableSlaModel extends LoadableDetachableModel<KpiConfig> {
    
    @SpringBean
    private KpiConfigRepository slaRepository;
    
    private NumericId id;
    
    public DetachableSlaModel(NumericId id)
    {
         Injector.get().inject(this);
        if (id.getId() == -1)
        {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }
    
    @Override
    protected KpiConfig load() {
        return slaRepository.getKpiConfigById(id);
    }
    
    @Override
    public int hashCode()
    {
        return id.hashCode();
    }
    
     @Override
    public boolean equals(final Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        else if (obj == null)
        {
            return false;
        }
        else if (obj instanceof DetachableSlaModel)
        {
            DetachableSlaModel other = (DetachableSlaModel)obj;
            return other.id.equals(id);
        }
        return false;
    }
    
}
