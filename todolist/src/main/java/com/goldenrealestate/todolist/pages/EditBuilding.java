package com.goldenrealestate.todolist.pages;


import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

import com.goldenrealestate.todolist.dao.BuildingDao;
import com.goldenrealestate.todolist.entity.Building;



public final class EditBuilding extends BasePage implements Serializable
{

	public EditBuilding(Building building) {
		
		super(null);
		add(new EditBuildingForm("editForm", building));		
	}
	
	public static Link<Void> link(final String name, final Integer id){
		
		return new Link<Void>(name){

			@Override
			public void onClick() {

				setResponsePage(new EditBuilding(new BuildingDao().find(id)));
			}		
			
		};	
		
	}
	
	static public final class EditBuildingForm extends Form<Building> {
		
		
		private static final long serialVersionUID = 5854800440399172115L;

		public EditBuildingForm(final String id, final Building building) {
			
			super(id, new CompoundPropertyModel<>(building));
			
			final TextField<String> title = new TextField<>("name");
			title.setRequired(true);
			title.add(new StringValidator(null, 30));
			
			add(title);

		}

		@Override
		public final void onSubmit() {
			
			IModel<Building> model = getModel();
			System.out.println(model.getObject());
			new BuildingDao().update(getModel().getObject().getId(), model.getObject().getName());
			setResponsePage(BuildingPage.class);
		}

	}
	

}
