package org.cages.moulinette.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public FilterRegistrationBean FileUploadFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new org.primefaces.webapp.filter.FileUploadFilter());
		registration.setName("PrimeFaces FileUpload Filter");
		return registration;
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

	  return new EmbeddedServletContainerCustomizer() {
	    @Override
	    public void customize(ConfigurableEmbeddedServletContainer container)       
	    {
	      ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.xhtml");
	      ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.xhtml");
	      ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403.xhtml");
	      ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.xhtml");

	      container.addErrorPages(error401Page,error404Page,error403Page,error500Page);
	    }
	  };
	}

}