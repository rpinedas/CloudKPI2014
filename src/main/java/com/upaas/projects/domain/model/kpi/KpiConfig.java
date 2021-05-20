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
public class KpiConfig implements Serializable {
    private NumericId id;
    private String description;
    private Integer slaUpperLimit;
    private Integer slaLowerLimit;

    public NumericId getId() {
        return id;
    }

    public void setId(NumericId id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getSlaUpperLimit() {
        return slaUpperLimit;
    }

    public void setSlaUpperLimit(Integer slaUpperLimit) {
        this.slaUpperLimit = slaUpperLimit;
    }

    public Integer getSlaLowerLimit() {
        return slaLowerLimit;
    }

    public void setSlaLowerLimit(Integer slaLowerLimit) {
        this.slaLowerLimit = slaLowerLimit;
    }
    
    public static String getDbColumn(String property) {
        if (property.equals("slaUpperLimit"))
            return "sla_upper_limit";
        
        if (property.equals("slaLowerLimit"))
            return "sla_lower_limit";
        
        if (property.equals("id"))
            return "id_kpis";
        
        return property;
    }

}
