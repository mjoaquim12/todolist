package com.goldenrealestate.todolist.pages;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import com.goldenrealestate.todolist.dao.BuildingDao;
import com.goldenrealestate.todolist.entity.Building;

public final class CreateBuilding extends BasePage implements Serializable {

	private static final long serialVersionUID = -5680206352781263824L;
	

	public CreateBuilding() {
		super(null);
		add(new CreateBuildingForm("createForm", new Building()));

	}

	static public final class CreateBuildingForm extends Form<Building> {

		public CreateBuildingForm(final String id, final Building building) {

			super(id, new CompoundPropertyModel<>(building));

			final TextField<String> name = new TextField<>("name");
			name.setRequired(true);
			name.add(new StringValidator(null, 30));

			add(name);

		}

		@Override
		public final void onSubmit() {

			Building newBuilding = new Building();
			newBuilding.setName(getModelObject().getName());
			new BuildingDao().create(newBuilding);
			setResponsePage(BuildingPage.class);
		}

	}

}
