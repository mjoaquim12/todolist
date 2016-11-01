package com.goldenrealestate.todolist.pages;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.EnumeratedType;
import org.apache.wicket.validation.validator.StringValidator;

import com.goldenrealestate.todolist.dao.AgentDao;
import com.goldenrealestate.todolist.dao.BuildingDao;
import com.goldenrealestate.todolist.dao.ProjectDao;
import com.goldenrealestate.todolist.entity.Agent;
import com.goldenrealestate.todolist.entity.Building;
import com.goldenrealestate.todolist.entity.Project;



public final class EditAgent extends BasePage implements Serializable
{

	public EditAgent(Agent agent) {
		
		super(null);
		add(new EditAgentForm("editForm", agent));		
	}
	
	public static Link<Void> link(final String name, final Integer id){
		
		return new Link<Void>(name){

			@Override
			public void onClick() {

				setResponsePage(new EditAgent(new AgentDao().find(id)));
			}		
			
		};	
		
	}
	
	static public final class EditAgentForm extends Form<Agent> {
		
		
		private static final long serialVersionUID = 5854800440399172115L;

		public EditAgentForm(final String id, final Agent agent) {
			
			super(id, new CompoundPropertyModel<>(agent));
			
			final TextField<String> title = new TextField<>("name");
			title.setRequired(true);
			title.add(new StringValidator(null, 30));
			
			add(title);

		}

		@Override
		public final void onSubmit() {

			Agent modelAgent = getModel().getObject();
			new AgentDao().update(modelAgent);
			setResponsePage(AgentPage.class);
		}

	}
	

}
