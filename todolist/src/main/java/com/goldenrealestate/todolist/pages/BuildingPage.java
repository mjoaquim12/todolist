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

import com.goldenrealestate.todolist.dao.BuildingDao;
import com.goldenrealestate.todolist.entity.Building;


public class BuildingPage extends BasePage {

	private static final long serialVersionUID = -4989904740683757955L;

	public BuildingPage(PageParameters parameters) {

		super(null);

		final TextField<String> txtSearch = new TextField<String>("searchString", Model.of(""));

		Form<?> form = new Form<Void>("filterForm") {

			private String searchStringValue;

			@Override
			protected void onSubmit() {

				if (txtSearch.getModelObject() != null) {

					searchStringValue = txtSearch.getModelObject();

					PageParameters pageParameters = new PageParameters();
					pageParameters.add("searchString", searchStringValue);
				}
			}

		};

		add(form);
		form.add(txtSearch);

		IModel buildingModel = new LoadableDetachableModel() {

			protected Object load() {

				BuildingDao buildingDao = new BuildingDao();				
				return buildingDao.findBySearchString(txtSearch.getModelObject());
			}
		};

		// Add table of buildings
		final PageableListView<Building> listView = new PageableListView<Building>("buildings", buildingModel, 10) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(final ListItem<Building> listItem) {

				final Building modelObject = listItem.getModelObject();

				listItem.add(new Label("name", modelObject.getName()));
				listItem.add(EditBuilding.link("edit", modelObject.getId()));
				listItem.add(link("delete-link", modelObject.getId()));
			}

		};

		add(listView);

	}
	
	public static Link<Void> link(final String name, final Integer id) {

		return new Link<Void>(name) {

			@Override
			public void onClick() {
				
				BuildingDao buildingDao = new BuildingDao();
				buildingDao.delete(id);				
			}

		};

	}

}
