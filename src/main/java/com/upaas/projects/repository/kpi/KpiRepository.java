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
package com.upaas.projects.repository.kpi;

import com.upaas.projects.domain.model.kpi.Company;
import com.upaas.projects.domain.model.kpi.Employee;
import com.upaas.projects.domain.model.kpi.KpiDetail;
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
public class KpiRepository extends BaseRepository {

    @Transactional(readOnly = true)
    public Iterator<? extends KpiDetail> getKpiIterator(NumericId id, long limit, long offset, String property, boolean ascending) {
        StringBuilder query = new StringBuilder();
        query.append(getBaseEagerSelect());
        query.append(" where employees.id_employed_in = :employed_in  ");
        //query.append(KpiDetail.getDbColumn(property));
        
        query.append(" limit ");
        query.append(limit);
        query.append(" offset ");
        query.append(offset);
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("employed_in", id.getId());
        
        return getTemplate().query(query.toString(), 
                                   params,
                                   new ParameterizedRowMapper<KpiDetail>() {

            @Override
            public KpiDetail mapRow(ResultSet rs, int index) throws SQLException {
                return getKpiEagerSelect(rs);
            }

        }).iterator();
    }

    @Transactional(readOnly = true)
    public long getKpiCount(NumericId id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("employed_in", id.getId());
        
        return getTemplate().queryForObject("SELECT count(*) FROM kpis_details "
                                          + "    inner join "
                                          + "       employees "
                                          + "    on  kpis_details.id_employee = employees.id_employee "
                                          + "where employees.id_employed_in = :employed_in",params,Integer.class);
    }

    @Transactional(readOnly = true)
    public KpiDetail getKpiById(NumericId id) {
         StringBuilder query = new StringBuilder();
         query.append(getBaseEagerSelect());
         query.append(" where id_kpis = :id ");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id.getId());

        List<KpiDetail> kpis =  getTemplate().query(query.toString(), params, new ParameterizedRowMapper<KpiDetail>() {
            @Override
            public KpiDetail mapRow(ResultSet rs, int index) throws SQLException {
                return getKpiEagerSelect(rs);
            }
        });
        
        return kpis.size() > 0 ? kpis.get(0) : null;
    }
    
    private String getBaseEagerSelect() {
        return " select kpis_details.id_kpis, kpis_details.kpi, kpis_details.sla, "  +
               "     employees.id_employee, employees.name as employee_name, " +
               "     companies.id_company, companies.name as company_name,  " +
               "     kpis_config.id_kpi, kpis_config.description, kpis_config.sla_upper_limit, kpis_config.sla_lower_limit " +
               " from kpis_details " +
               "   inner join " +
               "      employees " +
               "   on kpis_details.id_employee = employees.id_employee " +
               "   inner join " + 
               "     companies " +
                "       on employees.id_employed_in = companies.id_company "  +
               "   inner join " +
               "	 kpis_config " +
               "   on kpis_details.id_kpi = kpis_config.id_kpi " ;
               
    }
    
    private KpiDetail getKpiEagerSelect(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(new NumericId(rs.getInt("id_employee")));
        employee.setName(rs.getString("employee_name"));
        Company company = new Company();
        company.setId(new NumericId(rs.getInt("id_company")));
        company.setCompanyName("company_name");
        employee.setEmployedIn(company);
        
        KpiConfig kpiConfig = new KpiConfig();
        kpiConfig.setId(new NumericId(rs.getInt("id_kpi")));
        kpiConfig.setDescription(rs.getString("description"));
        kpiConfig.setSlaUpperLimit(rs.getInt("sla_upper_limit"));
        kpiConfig.setSlaLowerLimit(rs.getInt("sla_lower_limit"));
        
        KpiDetail kpi = new KpiDetail();
        kpi.setId(new NumericId(rs.getInt("id_kpis")));
        kpi.setKpi(rs.getDouble("kpi"));
        kpi.setSla(rs.getInt("sla"));
        
        kpi.setEmployee(employee);
        kpi.setKpiConfig(kpiConfig);
        return kpi;
    }

    @Transactional()
    public void updateKpi(NumericId id, Integer slaLowerLimit, Integer slaUpperLimit) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id_kpi", id.getId());
        params.put("sla_upper_limit", slaUpperLimit);
        params.put("sla_lower_limit", slaLowerLimit);
        
        getTemplate().update("update kpis_config set sla_upper_limit=:sla_upper_limit, sla_lower_limit=:sla_lower_limit where id_kpi=:id_kpi", params);
        
    }
    
}
