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
public class Employee implements Serializable {
    private NumericId id;
    private String name;
    private Company employedIn;

    public NumericId getId() {
        return id;
    }

    public void setId(NumericId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getEmployedIn() {
        return employedIn;
    }

    public void setEmployedIn(Company employedIn) {
        this.employedIn = employedIn;
    }
    
    
}
