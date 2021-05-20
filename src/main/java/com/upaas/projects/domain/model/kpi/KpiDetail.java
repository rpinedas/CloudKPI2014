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
package com.upaas.projects.domain.model.kpi;

import com.upaas.projects.domain.model.shared.NumericId;
import java.io.Serializable;

/**
 *
 * @author Román Pineda
 */
public class KpiDetail implements Serializable {
    private NumericId id;
    private double kpi;
    private int sla;
    private Employee employee;
    private KpiConfig kpiConfig;
    
    public NumericId getId() {
        return id;
    }

    public void setId(NumericId id) {
        this.id = id;
    }
    
    public double getKpi() {
        return kpi;
    }

    public void setKpi(double kpi) {
        this.kpi = kpi;
    }

    public int getSla() {
        return sla;
    }

    public void setSla(int sla) {
        this.sla = sla;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public KpiConfig getKpiConfig() {
        return kpiConfig;
    }

    public void setKpiConfig(KpiConfig kpiConfig) {
        this.kpiConfig = kpiConfig;
    }
    
    
    public int getValue() {
        //int slaLowerLimitValue = (kpiConfig.getSlaLowerLimit().getNumber() /100 * sla) - sla;
       // int slaUpperLimitValue = (kpiConfig.getSlaUpperLimit().getNumber() /100 * sla) + sla;
        
        if (kpi <= kpiConfig.getSlaLowerLimit())
            return -1;
        
        if (kpi >= kpiConfig.getSlaUpperLimit())
            return 1;
        
        return 0;
        
    }
    
    public static String getDbColumn(String property) {
        String dbColumn = "id";
    
        return dbColumn;
    }

    
    
}
