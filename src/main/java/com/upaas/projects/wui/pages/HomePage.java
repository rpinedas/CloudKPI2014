package com.upaas.projects.wui.pages;

import com.upaas.projects.domain.model.kpi.KpiDetail;
import com.upaas.projects.wui.providers.SortableKpiDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


public class HomePage extends BasePage implements AuthenticatedWebPage {
	private static final long serialVersionUID = 1L;

	public HomePage() {
		super();
                SignInSession session = getMySession();
        
                
                
              
                List<IColumn<KpiDetail, String>> columns = new ArrayList<IColumn<KpiDetail, String>>();

                //kpiConfig.description
                //kpi
                //sla
                //employee.name
                //value
                
        columns.add(new PropertyColumn<KpiDetail, String>(new Model<String>("Description"), "kpiConfig.description", "kpiConfig.description"));
        columns.add(new PropertyColumn<KpiDetail, String>(new Model<String>("KPI"), "kpi", "kpi"));
        //columns.add(new PropertyColumn<KpiDetail, String>(new Model<String>("SLA"), "sla", "sla"));
        //columns.add(new PropertyColumn<KpiDetail, String>(new Model<String>("Employee"), "employee.name", "employee.name"));
        //columns.add(new PropertyColumn<KpiDetail, String>(new Model<String>("Value"), "value", "value"));

        columns.add(new AbstractColumn<KpiDetail, String>(new Model<String>("")) {
            @Override
            public void populateItem(Item<ICellPopulator<KpiDetail>> cellItem, String componentId,
                    IModel<KpiDetail> model) {
                ImgPanel imgPanel = new ImgPanel(componentId, model);
                imgPanel.setRenderBodyOnly(true);
                cellItem.add(imgPanel);
            }

        });
        
        DataTable dataTable = new DefaultDataTable<KpiDetail, String>("table", columns,
                new SortableKpiDataProvider(getMySession().getLoggedUser().getEmployeeRole().getEmployedIn().getId()), 10);
        //dataTable.addBottomToolbar(new ExportToolbar(dataTable).addDataExporter(new CSVDataExporter()));

        add(dataTable);

    }
        
        
        class ImgPanel extends Panel {

        public ImgPanel(String id,  IModel<KpiDetail> model) {
            super(id, model);
            
            String path = "images/buttons/";
                    
             switch (model.getObject().getValue()) {
                case -1:  path += "red.png";
                     break;
                case 0:  path += "yellow.png";
                       break;
                case 1:  path += "green.png";
                     break;
                
             }
            
            WebMarkupContainer markup = new WebMarkupContainer( "img" );
            markup.add(AttributeModifier.append( "src",path) );    
            add(markup);
        }
    }
        
        private SignInSession getMySession()
        {
            return (SignInSession)getSession();
        }
}
