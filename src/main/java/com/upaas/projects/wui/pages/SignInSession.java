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
package com.upaas.projects.wui.pages;

import com.upaas.projects.domain.model.kpi.User;
import com.upaas.projects.repository.kpi.UserRepository;
import com.upaas.projects.repository.shared.Utils;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;

import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

//import org.apache.wicket.injection.Injector;

/**
 *
 * @author Román Pineda
 */
public final class SignInSession extends AuthenticatedWebSession {
    
    @SpringBean
    UserRepository userRepository;

    private User loggedUser;
    
    protected SignInSession(Request request)
    {
        super(request);
        Injector.get().inject(this);
       // InjectorHolder.getInjector().inject(this);
    }
     
    @Override
    public boolean authenticate(String username, String password) {
        
        User user = userRepository.getUserByName(username);
        
        if (user != null) {
            if (user.getPassword().equals(Utils.getHash(password, user.getSalt()))) {
                this.loggedUser = user;
                return true;
            }
        }
        
        return false;
    }

    @Override
    public Roles getRoles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public User getLoggedUser() {
        return loggedUser;
    }
}
