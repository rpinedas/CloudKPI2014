package com.upaas.projects.wui.pages;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;

public class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;

        
        public BasePage() {
            
            
            
            WebMarkupContainer homeMenu = new WebMarkupContainer("homeMenu");
            WebMarkupContainer slaMenu = new WebMarkupContainer("slaMenu");
            
            
            
            if (getClass().getName().equals("com.upaas.projects.wui.pages.KpiConfigPage"))
                slaMenu.add(new AttributeModifier("class", "dropdown active"));
            
            if (getClass().getName().equals("com.upaas.projects.wui.pages.KpiConfigEditPage"))
                slaMenu.add(new AttributeModifier("class", "dropdown active"));
            
            if (this.getClass().getName().equals("com.upaas.projects.wui.pages.HomePage"))  //homeMenu.add(new AttributeModifier("class", "active"));
                homeMenu.add(new AttributeModifier("class", "active"));
            
            
            //item.add(new SimpleAttributeModifier("class", "foo"));
            
             homeMenu.add(new Link("home") {

                @Override
                public void onClick() {
                    setResponsePage(HomePage.class);
                }
            });
             this.add(homeMenu);
            
            slaMenu.add(new Link("sla") {

                @Override
                public void onClick() {
                    setResponsePage(KpiConfigPage.class);
                }
            });
            this.add(slaMenu);
            
            add(new Link("logout") {

                @Override
                public void onClick() {
                     getSession().invalidate();
                     setResponsePage(SignIn.class);
                }
            });
            
            
            this.add(new Label("loggedUserName",new PropertyModel(((SignInSession)getSession()).getLoggedUser(), "employeeName")) {
                
            });
        }
}
