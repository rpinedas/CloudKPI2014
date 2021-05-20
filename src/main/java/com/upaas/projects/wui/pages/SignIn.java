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

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author Román Pineda
 */
public class SignIn extends WebPage {
    
    public SignIn()
    {
        final SignIn.SignInForm form = new SignIn.SignInForm("signInForm");
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        
        WebMarkupContainer enclosure = new WebMarkupContainer("enclosure"){
            
            @Override
            public boolean isVisible() {
                return feedback.anyErrorMessage();
            }
            
        };
        enclosure.setOutputMarkupId(true);
        enclosure.add(feedback);
        add(enclosure);
        
        add(form);
    }
    
    
    public final class SignInForm extends Form<Void>
    {
        private String username;
        private String password;

        /**
         * Constructor
         * 
         * @param id
         *            id of the form component
         */
        public SignInForm(final String id)
        {
            super(id);
            // Attach textfield components that edit properties map model
            add(new TextField<String>("username", new PropertyModel<String>(this, "username")));
            add(new PasswordTextField("password", new PropertyModel<String>(this, "password")));
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit()
        {
            // Get session info
            SignInSession session = getMySession();

            // Sign the user in
            if (session.signIn(getUsername(), getPassword()))
            {
                continueToOriginalDestination();
                setResponsePage(getApplication().getHomePage());
            }
            else
            {
                // Get the error message from the properties file associated with the Component
                String errmsg = getString("loginError", null, "Unable to sign you in");

                // Register the error message with the feedback panel
                error(errmsg);
            }
        }
        
//        @Override
//        public final void onError()
//        {
//            super.onError();
//            this.updateFormComponentModels();
//        }
        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }
        
        public String getUsername()
        {
            return username;
        }
        
        public void setUsername(String username)
        {
            this.username = username;
        }

        private SignInSession getMySession()
        {
            return (SignInSession)getSession();
        }
    }
}


