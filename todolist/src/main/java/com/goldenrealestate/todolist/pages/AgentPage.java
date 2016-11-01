package com.goldenrealestate.todolist.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.goldenrealestate.todolist.dao.AgentDao;
import com.goldenrealestate.todolist.entity.Agent;

public class AgentPage extends BasePage {

	private static final long serialVersionUID = -4989904740683757955L;

	public AgentPage(PageParameters parameters) {
		
		super(null);		
		
		final TextField<String> searchString = new TextField<String>("searchString",
				Model.of(""));		

		Form<?> form = new Form<Void>("filterForm") {

			private String searchStringValue;

			@Override
			protected void onSubmit() {

				if(searchString.getModelObject()!=null){
					
					searchStringValue = searchString.getModelObject();
	
					PageParameters pageParameters = new PageParameters();
					pageParameters.add("searchString", searchStringValue);
				}
			}

		};

		add(form);
		form.add(searchString);
		
		
		IModel agentModel = new LoadableDetachableModel() {
			

			protected Object load() {

				return new AgentDao().findBySearchString(searchString.getModelObject());
			}
		};

		// Add table of tasks
		final PageableListView<Agent> listView = new PageableListView<Agent>("agents", agentModel, 10) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(final ListItem<Agent> listItem) {

				final Agent agent = listItem.getModelObject();

				listItem.add(new Label("name", agent.getName()));
				listItem.add(EditAgent.link("edit", agent.getId()));
				listItem.add(link("delete-link", agent.getId()));
			}

		};

		add(listView);

	}

	public static Link<Void> link(final String name, final Integer id) {

		return new Link<Void>(name) {

			@Override
			public void onClick() {
				
				new AgentDao().delete(id);
			}

		};

	}
	
	

}
