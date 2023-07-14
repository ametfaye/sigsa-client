package org.cages.moulinette.config;

import javax.sql.DataSource;

import org.cages.moulinette.enumeration.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	AuthenticationSuccessHandler successHandler;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// require all requests to be authenticated except for the resources
		http.authorizeRequests().antMatchers("/javax.faces.resource/**","/passageFrontiereOrder.xhtml").permitAll()
						/*
								.antMatchers("/orderEdition.xhtml").hasAnyAuthority(EnumRole.EDIODM.getCode(), EnumRole.UPODM.getCode())
								.antMatchers("/detailsOrder.xhtml").hasAnyAuthority(EnumRole.TBGS.getCode(), EnumRole.TBCHS.getCode(), EnumRole.TBSG.getCode(),EnumRole.TBSGG.getCode())
								
								.antMatchers("/orderList.xhtml").hasAnyAuthority(EnumRole.TBGS.getCode(), EnumRole.TBCHS.getCode(), EnumRole.TBSG.getCode(),EnumRole.TBSGG.getCode())
								.antMatchers("/orderListAtraiter.xhtml").hasAnyAuthority(EnumRole.TBCHS.getCode(),EnumRole.TBSG.getCode(),EnumRole.TBSGG.getCode())
								.antMatchers("/orderSearch.xhtml").hasAnyAuthority(EnumRole.TBCHS.getCode(), EnumRole.TBGS.getCode(),EnumRole.TBSG.getCode(),EnumRole.TBSGG.getCode())
								.antMatchers("/documents.xhtml").hasAnyAuthority(EnumRole.TBCHS.getCode(), EnumRole.TBGS.getCode(),EnumRole.TBSG.getCode(),EnumRole.TBSGG.getCode())
								
								.antMatchers("/adminRolesList.xhtml").hasAnyAuthority(EnumRole.ADMIN.getCode())
								.antMatchers("/adminRoleEdition.xhtml").hasAnyAuthority(EnumRole.ADMIN.getCode())
								.antMatchers("/adminUtilisateursList.xhtml").hasAnyAuthority(EnumRole.ADMIN.getCode())
								.antMatchers("/adminUtilisateurEdition.xhtml").hasAnyAuthority(EnumRole.ADMIN.getCode())
								.antMatchers("/adminUtilisateurUpdate.xhtml").hasAnyAuthority(EnumRole.ADMIN.getCode())
								.antMatchers("/adminAgentsList.xhtml").hasAnyAuthority(EnumRole.ADMIN.getCode())
								.antMatchers("/adminAgentEdition.xhtml").hasAnyAuthority(EnumRole.ADMIN.getCode())
								.antMatchers("/adminServicesList.xhtml").hasAnyAuthority(EnumRole.ADMIN.getCode())
								.antMatchers("/adminAmpliationsList.xhtml").hasAnyAuthority(EnumRole.ADMIN.getCode())
								.antMatchers("/adminEntitesPubliquesList.xhtml").hasAnyAuthority(EnumRole.ADMIN.getCode())
								
								.antMatchers("/dashboardAdmin.xhtml").hasAnyAuthority(EnumRole.TBADM.getCode())
								.antMatchers("/dashboardChs.xhtml").hasAnyAuthority(EnumRole.TBCHS.getCode())
								.antMatchers("/dashboardGs.xhtml").hasAnyAuthority(EnumRole.TBGS.getCode())
								.antMatchers("/dashboardSgg.xhtml").hasAnyAuthority(EnumRole.TBSGG.getCode())
								.antMatchers("/dashboardSg.xhtml").hasAnyAuthority(EnumRole.TBSG.getCode())  */
								
								.anyRequest().authenticated();
		// login
		http.formLogin().loginPage("/login.xhtml").successHandler(successHandler).permitAll().failureUrl("/login.xhtml?error=true");
		
		// logout
		http.logout().logoutSuccessUrl("/login.xhtml");

		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}

}
