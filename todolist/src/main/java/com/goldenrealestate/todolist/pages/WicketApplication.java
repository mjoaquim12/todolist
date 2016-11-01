package com.goldenrealestate.todolist.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;

/**
 * Application object for your web application.
 * 
 * @see com.goldenrealestate.todolist.pages.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {

		return WelcomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		
		super.init();
		
		mountPage("/Welcome", WelcomePage.class);
		mountPage("/Projects", ProjectPage.class);
		mountPage("/Agents", AgentPage.class);
		mountPage("/Buildings", BuildingPage.class);
		
		BootstrapSettings settings = new BootstrapSettings();		
		 
		//final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Flatly);
		//settings.setThemeProvider(themeProvider);
		
		Bootstrap.install(this, settings);
	}
}
