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
import com.upaas.projects.domain.model.kpi.User;
import com.upaas.projects.domain.model.shared.NumericId;
import com.upaas.projects.repository.shared.BaseRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
public class UserRepository extends BaseRepository {

    public User getUserByName(String username) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        
        this.getDataSource();

        List<User> users = getTemplate().query("SELECT username,hash,slt,id_employee,active,employees.name,"
                + " companies.id_company, companies.name as company_name "
                + "FROM users"
                + "   inner join " 
                + "     employees " 
                + "       on users.id_employee_role = employees.id_employee "
                + "   inner join "
                + "     companies"
                + "       on employees.id_employed_in = companies.id_company " 
                + "WHERE username = :username", params, new ParameterizedRowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int index) throws SQLException {
                User user = createBean("user");
                
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("hash"));
                user.setSalt(rs.getString("slt"));
                
                //for employee role user inner join
                
                Employee employee = createBean("employee");
                employee.setId(new NumericId(rs.getInt("id_employee")));
                employee.setName(rs.getString("name"));
                
                Company company = new Company();
                company.setId(new NumericId(rs.getInt("companies.id_company")));
                company.setCompanyName(rs.getString("companies.company_name"));
                employee.setEmployedIn(company);
                
                user.setEmployeeRole(employee);
                
                

                return user;
            }
        });
        
        return users.size() > 0 ? users.get(0) : null;
    }
    
}
