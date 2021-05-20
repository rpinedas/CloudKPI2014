package com.upaas.projects.wui.pages;

import com.upaas.projects.domain.model.kpi.KpiConfig;
import com.upaas.projects.repository.kpi.KpiRepository;
import com.upaas.projects.wui.providers.SortableKpiConfigDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class KpiConfigPage extends BasePage implements AuthenticatedWebPage {

    private static final long serialVersionUID = 1L;
    private KpiConfig selected;
   

    public KpiConfigPage() {
        super();

        List<IColumn<KpiConfig, String>> columns = new ArrayList<IColumn<KpiConfig, String>>();

        columns.add(new PropertyColumn<KpiConfig, String>(new Model<String>("#"), "id", "id"));
        columns.add(new PropertyColumn<KpiConfig, String>(new Model<String>("Description"), "description", "description"));
        columns.add(new PropertyColumn<KpiConfig, String>(new Model<String>("SLA ALL"), "slaLowerLimit", "slaLowerLimit"));
        columns.add(new PropertyColumn<KpiConfig, String>(new Model<String>("SLA AUL"), "slaUpperLimit", "slaUpperLimit"));
        
        columns.add(new AbstractColumn<KpiConfig, String>(new Model<String>("")) {
            @Override
            public void populateItem(Item<ICellPopulator<KpiConfig>> cellItem, String componentId,
                    IModel<KpiConfig> model) {
                EditPanel editPanel = new EditPanel(componentId, model);
                editPanel.setRenderBodyOnly(true);
                cellItem.add(editPanel);
            }
        });
        
        DataTable dataTable = new DefaultDataTable<KpiConfig, String>("table", columns,
                new SortableKpiConfigDataProvider(), 10);
        //dataTable.addBottomToolbar(new ExportToolbar(dataTable).addDataExporter(new CSVDataExporter()));

        add(dataTable);

    }

    class EditPanel extends Panel {

        public EditPanel(String id, IModel<KpiConfig> model) {
            super(id, model);
            add(new Link("edit") {
                @Override
                public void onClick() {
                    selected = (KpiConfig) getParent().getDefaultModelObject();
                    setResponsePage(new KpiConfigEditPage(selected));
                }
            });
        }
    }
}
