package com.goldenrealestate.todolist.pages;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import com.goldenrealestate.todolist.dao.AgentDao;
import com.goldenrealestate.todolist.dao.BuildingDao;
import com.goldenrealestate.todolist.dao.ProjectDao;
import com.goldenrealestate.todolist.entity.Agent;
import com.goldenrealestate.todolist.entity.Building;
import com.goldenrealestate.todolist.entity.Project;
import com.goldenrealestate.todolist.entity.Status;

public final class CreateProject extends BasePage implements Serializable {

	private static final long serialVersionUID = -5680206352781263824L;	

	public CreateProject() {
		super(null);
		
		add(new CreateProjectForm("createForm", new Project()));

	}

	static public final class CreateProjectForm extends Form<Project> {

    private static final long serialVersionUID = 7807047414890169683L;

    public CreateProjectForm(final String id, final Project project) {

			super(id, new CompoundPropertyModel<>(project));

			final TextField<String> name = new TextField<>("project");
			name.setRequired(true);
			name.add(new StringValidator(null, 30));

			add(name);			
			
			DropDownChoice<Building> dropDownBuilding = new DropDownChoice<>(
					"dropDownBuilding", 
					new PropertyModel<>(project, "building"),
					new LoadableDetachableModel<List<Building>>() {

            private static final long serialVersionUID = -442380201071009896L;

            @Override
						protected List<Building> load() {

							return new BuildingDao().findAll();
						}

					});
		          
		    add(dropDownBuilding);
		    
		    DropDownChoice<Agent> dropDownAgent = new DropDownChoice<>(
					"dropDownAgent", 
					new PropertyModel<>(project, "agent"),
					new LoadableDetachableModel<List<Agent>>() {

            private static final long serialVersionUID = -4410570240834728673L;

            @Override
						protected List<Agent> load() {

							return new AgentDao().findAll();
						}

					});
		          
		    add(dropDownAgent);

		}

		/* (non-Javadoc)
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public final void onSubmit() {

			Project projectModel = getModel().getObject();
			
			Project newProject = new Project();			
			newProject.setProject(projectModel.getProject());
			newProject.setBuilding(projectModel.getBuilding());
			newProject.setAgent(projectModel.getAgent());			
			newProject.setStatus(Status.NOT_STARTED);
			ProjectDao projectDao = new ProjectDao();
			projectDao.create(newProject);
			setResponsePage(ProjectPage.class);
		}

	}

}
