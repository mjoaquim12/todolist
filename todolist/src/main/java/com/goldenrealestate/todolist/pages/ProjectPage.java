package com.goldenrealestate.todolist.pages;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.goldenrealestate.todolist.dao.BuildingDao;
import com.goldenrealestate.todolist.dao.ProjectDao;
import com.goldenrealestate.todolist.entity.Building;
import com.goldenrealestate.todolist.entity.Project;

import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.BootstrapPagingNavigator;

public class ProjectPage extends BasePage {

	private static final long serialVersionUID = 8543593261945371215L;

	private Building building;

	public ProjectPage(final PageParameters parameters) {

		super(null);

		// add building cb
		DropDownChoice<Building> dropDownBuilding = 
				new DropDownChoice<>(
						"dropDownBuilding",
						new PropertyModel<>(this, "building"), 
						new LoadableDetachableModel<List<Building>>() {

							@Override
							protected List<Building> load() {

								BuildingDao buildingDao = new BuildingDao();
								return buildingDao.findAll();
							}

						});


		Form<?> form = new Form<Void>("filterForm") {

			@Override
			protected void onSubmit() {

				building = dropDownBuilding.getModelObject();
			}

		};

		add(new Link<Void>("refresh-link"){

			@Override
			public void onClick() {

				building = null;
				setResponsePage(getPage());
			}
			
		} );
		add(form);
		form.add(dropDownBuilding);

		IModel projectModel = new LoadableDetachableModel() {
			
      private static final long serialVersionUID = 4930426139133457178L;

      protected Object load() {

				ProjectDao dao = new ProjectDao();
				return dao.findBySearchParams(building);
			}
		};

		// Add table of tasks
		final PageableListView<Project> listView = 
				new PageableListView<Project>("projects", projectModel, 6) {
			
					private static final long serialVersionUID = 1L;

					@Override
					public void populateItem(final ListItem<Project> listItem) {

						final Project project = listItem.getModelObject();

						listItem.add(new Label("buildingName", project.getBuilding().getName()));
						listItem.add(new Label("project", project.getProject()));
						listItem.add(new Label("assigned", project.getAgent().getName()));
						listItem.add(new Label("status", project.getStatus().getDescription()));
						listItem.add(EditProject.link("edit", project.getId()));
						listItem.add(link("delete-link", project.getId()));

					}

				};

		add(listView);
		add(new BootstrapPagingNavigator("pager", listView));

	}

	public static Link<Void> link(final String name, final Integer id) {

		return new Link<Void>(name) {

			@Override
			public void onClick() {

				new ProjectDao().delete(id);
			}

		};

	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

}
