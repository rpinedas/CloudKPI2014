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

import com.upaas.projects.domain.model.kpi.KpiDetail;
import com.upaas.projects.domain.model.shared.NumericId;
import com.upaas.projects.repository.kpi.KpiRepository;
import com.upaas.projects.wui.models.DetachableKpiModel;
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
public class SortableKpiDataProvider extends SortableDataProvider<KpiDetail, String> {
    @SpringBean
    protected KpiRepository kpiRepository;
    protected NumericId companyId;
    
    public SortableKpiDataProvider(NumericId companyId)
    {
        Injector.get().inject(this);
        setSort("kpis_details.id_kpis", SortOrder.ASCENDING);
        this.companyId = companyId;
    }
    
    @Override
    public Iterator<? extends KpiDetail> iterator(long first, long count) {
        return kpiRepository.getKpiIterator(companyId, count, first,getSort().getProperty(),getSort().isAscending());
    }

    @Override
    public long size() {
        return kpiRepository.getKpiCount(companyId);
    }

    @Override
    public IModel<KpiDetail> model(KpiDetail k) {
        return new DetachableKpiModel(k.getId());
    }
    
}
