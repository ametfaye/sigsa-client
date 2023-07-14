package org.cages;

import javax.faces.application.Application;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.sun.faces.config.ConfigureListener;

@SpringBootApplication
@ComponentScan({ "org.cages.moulinette.service", "org.cages.moulinette.config",
				 "org.cages.moulinette.primefaces.managedBean", "org.cages.moulinette.scheduler.tasks",
				 "org.cages.moulinette.scheduler.tasks.config", "org.cages.moulinette.security",
				 "org.cages.moulinette.mailer.service"})
@EntityScan({"org.cages.moulinette.model","org.cages.moulinette.mailer.model"})
@EnableJpaRepositories( {"org.cages.moulinette.repository","org.cages.moulinette.mailer.repository"})
@EnableScheduling
public class AgricultureApplication extends SpringBootServletInitializer {

	private final static Logger LOGGER = Logger.getLogger(AgricultureApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(AgricultureApplication.class);
		ConfigurableApplicationContext ctx = springApplication.run(args);
		String appName = ctx.getEnvironment().getProperty("app.name");
		String appVersion = ctx.getEnvironment().getProperty("app.version");
		String port = ctx.getEnvironment().getProperty("server.port");
		LOGGER.info("**************** "+appName+" "+appVersion+" a démarré avec succès au port " + port + " --- MODULE DE GESTION DES STATISTIQUES AGRICOLES ");
	}

	@Bean
	public ServletContextInitializer initializer() {
		return new ServletContextInitializer() {

			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
				servletContext.setInitParameter("com.sun.faces.numberOfViewsInSession", "5");
				servletContext.setInitParameter("com.sun.faces.serializeServerState", "false");
				servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "client");
				servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
				servletContext.setInitParameter("javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", "true");
				servletContext.setInitParameter("primefaces.THEME", "excite-bike");
			}
		};
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		FacesServlet servlet = new FacesServlet();
		return new ServletRegistrationBean(servlet, "*.jsf");
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> servletListenerRegistrationBean() {
		ConfigureListener configureListener = new ConfigureListener();
		return new ServletListenerRegistrationBean<>(configureListener);
	}

}
