/*
 * Copyright 2013 Román Pineda
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
package com.upaas.projects.repository.kpi;

import com.upaas.projects.domain.model.kpi.KpiConfig;
import com.upaas.projects.domain.model.shared.NumericId;
import com.upaas.projects.repository.shared.BaseRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Román Pineda
 */
@Transactional(propagation = Propagation.REQUIRED)
public class KpiConfigRepository extends BaseRepository {
    
    @Transactional(readOnly = true)
    public Integer getKpiConfigCount() {
        return getTemplate().queryForObject("SELECT count(*) FROM kpis_config", new HashMap<String, Object>(),Integer.class);
    }
    
    @Transactional(readOnly = true)
    public Iterator<KpiConfig> getKpiConfigIterator(long limit, long offset,String column, boolean ascending) {
        StringBuilder query = new StringBuilder();
        
        query.append(getBaseEagerSelect());
                
        query.append(" order by "); 
        
        query.append(KpiConfig.getDbColumn(column));
        
        if (ascending)
            query.append(" asc ");
        else
            query.append(" desc ");
    
        query.append(" limit ");
        query.append(limit);
        query.append(" offset ");
        query.append(offset);
        
        Map<String, Object> params = new HashMap<String, Object>();

        return getTemplate().query(query.toString(), params, new ParameterizedRowMapper<KpiConfig>() {
            @Override
            public KpiConfig mapRow(ResultSet rs, int index) throws SQLException {
                return getKpiConfigEagerSelect(rs);
            }
        }).iterator();
        
    }
    
    @Transactional(readOnly = true)
    public KpiConfig getKpiConfigById(NumericId id) {
        StringBuilder query = new StringBuilder();
        
        query.append(getBaseEagerSelect());
        query.append(" where id_kpi = :id");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id.getId());

        List<KpiConfig> slas =  getTemplate().query(query.toString(), params, new ParameterizedRowMapper<KpiConfig>() {
            @Override
            public KpiConfig mapRow(ResultSet rs, int index) throws SQLException {
                return getKpiConfigEagerSelect(rs);
            }
        });
        
        return slas.size() > 0 ? slas.get(0) : null;
        
    }
    
    private String getBaseEagerSelect() {
        return "SELECT id_kpi, description,sla_upper_limit,sla_lower_limit  "
             + "from kpis_config " ;
               
    }
    
    private KpiConfig getKpiConfigEagerSelect(ResultSet rs) throws SQLException {
         KpiConfig kpiConfig = createBean("kpiConfig");

         kpiConfig.setId(new NumericId(rs.getInt("id_kpi")));
         kpiConfig.setDescription(rs.getString("description"));
         kpiConfig.setSlaUpperLimit(rs.getInt("sla_upper_limit"));
         kpiConfig.setSlaLowerLimit(rs.getInt("sla_lower_limit"));
         
         return kpiConfig;
    }
    
    
}
