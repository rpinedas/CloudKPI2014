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
package com.upaas.projects.wui.providers;

import com.upaas.projects.domain.model.kpi.KpiConfig;
import com.upaas.projects.repository.kpi.KpiConfigRepository;
import com.upaas.projects.wui.models.DetachableSlaModel;
import java.util.Iterator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 *
 * @author Román Pineda
 */
public class SortableKpiConfigDataProvider extends SortableDataProvider<KpiConfig, String> {
    @SpringBean
    protected KpiConfigRepository slaRepository;
    
    public SortableKpiConfigDataProvider()
    {
        Injector.get().inject(this);
        setSort("id_kpi", SortOrder.ASCENDING);
    }
    
    @Override
    public Iterator<? extends KpiConfig> iterator(long first, long count) {
        //getSort()
        return slaRepository.getKpiConfigIterator(count, first,getSort().getProperty(),getSort().isAscending());
    }

    @Override
    public long size() {
        return slaRepository.getKpiConfigCount();
    }

    @Override
    public IModel<KpiConfig> model(KpiConfig t) {
        return new DetachableSlaModel(t.getId());
    }
    
}
