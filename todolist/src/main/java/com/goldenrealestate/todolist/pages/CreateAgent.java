package com.goldenrealestate.todolist.pages;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import com.goldenrealestate.todolist.dao.AgentDao;
import com.goldenrealestate.todolist.entity.Agent;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.validation.SimpleMessageValidation;

public final class CreateAgent extends BasePage implements Serializable {

	private static final long serialVersionUID = -5680206352781263824L;

	public CreateAgent() {
		
		super(null);
		add(new CreateAgentForm("createAgentForm", new Agent()));
	}

	static public final class CreateAgentForm extends BootstrapForm<Agent>  {

		private static final long serialVersionUID = 1L;

		public CreateAgentForm(final String id, Agent agent) {

			super(id,new CompoundPropertyModel<>(agent));
			
	        RequiredTextField<String> agentTextField = new RequiredTextField<String>(
														"name");
			
			agentTextField.setLabel(Model.of("Agent name"));
			add(agentTextField);		
			
			SimpleMessageValidation validation = new SimpleMessageValidation();
	        validation.getConfig().appendToParent(true);
			add(validation);
			 
			agentTextField.add(validation);			
			
			add(new AjaxButton("submitBtn") {
               
				private static final long serialVersionUID = 3394534687279267901L;

				@Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                	
                    super.onSubmit(target, form);
                    CreateAgentForm.this.onSubmit(target);
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    super.onError(target, form);
                    target.add(form);
                }
            });
			
		}

		protected void onSubmit(AjaxRequestTarget target) {
			
		}

		@Override
		public final void onSubmit() {

			Agent newAgent = new Agent();
			newAgent.setName(getModelObject().getName());
			new AgentDao().create(newAgent);

			setResponsePage(AgentPage.class);
		}

	}

}
