package com.goldenrealestate.todolist.pages;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import com.goldenrealestate.todolist.dao.AgentDao;
import com.goldenrealestate.todolist.dao.ProjectDao;
import com.goldenrealestate.todolist.entity.Agent;
import com.goldenrealestate.todolist.entity.Project;
import com.goldenrealestate.todolist.entity.Status;

/**
 * Edit Project Page contains all processing to edit a project
 * 
 * @author Joaquim
 *
 */
public final class EditProject extends BasePage implements Serializable {

  
  private static final long serialVersionUID = 1234106841418136667L;

  public EditProject(Project project) {
    
    super(null);
    add(new EditProjectForm("editForm", project));

  }

  /**
   * 
   * Edit project Form Class
   *
   */
  static public final class EditProjectForm extends Form<Project> {

    private static final long serialVersionUID = -8480404729048118733L;
    private static final ChoiceRenderer<Status> RENDERER = new ChoiceRenderer<Status>(
        "description");

    public EditProjectForm(final String id, final Project project) {

      super(id, new CompoundPropertyModel<>(project));

      final TextField<String> building = new TextField<>("building");
      building.setEnabled(false);
      add(building);

      final TextField<String> projectText = new TextField<>("project");
      projectText.setRequired(true);
      add(projectText);

      List<Agent> lstAgents = new AgentDao().findAll();

      DropDownChoice<Agent> dropDownAgent = new DropDownChoice<>("dropDownAgent",
          new PropertyModel<Agent>(project, "agent"), lstAgents);

      add(dropDownAgent);

      DropDownChoice<Status> dropDownStatus = new DropDownChoice<>("dropDownStatus",
          new PropertyModel<Status>(project, "status"), Arrays.asList(Status.values()), RENDERER);

      add(dropDownStatus);

    }

    @Override
    public final void onSubmit() {

      Project modelProject = getModel().getObject();
      new ProjectDao().update(modelProject);
      setResponsePage(ProjectPage.class);
    }

  }

  /**
   * 
   * Static link to redirect to EditProject Page
   * 
   * @param name  link name
   * @param id  primary key
   * @return Link to EditProject Page
   */
  public static Link<Void> link(final String name, final Integer id) {

    return new Link<Void>(name) {

      private static final long serialVersionUID = -2689147355986401003L;

      @Override
      public void onClick() {

        setResponsePage(new EditProject(new ProjectDao().find(id)));
      }

    };

  }

}
