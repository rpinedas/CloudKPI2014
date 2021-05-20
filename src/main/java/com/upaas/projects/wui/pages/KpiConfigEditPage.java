package com.upaas.projects.wui.pages;

import com.upaas.projects.domain.model.kpi.KpiConfig;
import com.upaas.projects.repository.kpi.KpiRepository;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class KpiConfigEditPage extends BasePage implements AuthenticatedWebPage {
	private static final long serialVersionUID = 1L;
        private Integer slaUpperLimit;
        private Integer slaLowerLimit;
        private String description;
        
        @SpringBean
        protected KpiRepository kpiRepository;
               
        public KpiConfigEditPage(final KpiConfig toEdit) {
            slaUpperLimit = toEdit.getSlaUpperLimit();
            slaLowerLimit = toEdit.getSlaLowerLimit();  
            description = toEdit.getDescription();
            
            Form<KpiConfigEditPage> form = new Form<KpiConfigEditPage>("form",new CompoundPropertyModel<KpiConfigEditPage>(this)) {
                
                 @Override
                 public final void onSubmit()
                 {
                    kpiRepository.updateKpi(toEdit.getId(),getSlaLowerLimit(),getSlaUpperLimit());
                    setResponsePage(KpiConfigPage.class);
                 }
                
            };
            
            form.add(new Label("description"));
            form.add(new TextField<Integer>("slaUpperLimit"));
            form.add(new TextField<Integer>("slaLowerLimit"));
            
            this.add(form);
            
        }
        
        public String getDescription() {
            return description;
        }
        
        public Integer getSlaLowerLimit() {
            return slaLowerLimit;
        }

        public void setSlaLowerLimit(Integer slaLowerLimit) {
            this.slaLowerLimit = slaLowerLimit;
        }


        public Integer getSlaUpperLimit() {
            return slaUpperLimit;
        }

        public void setSlaUpperLimit(Integer slaUpperLimit) {
            this.slaUpperLimit = slaUpperLimit;
        }
        
}
