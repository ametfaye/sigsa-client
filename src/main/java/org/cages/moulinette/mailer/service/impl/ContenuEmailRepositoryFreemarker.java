package org.cages.moulinette.mailer.service.impl;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.cages.moulinette.exceptions.EmailTemplateException;
import org.cages.moulinette.mailer.model.ContenuEmail;
import org.cages.moulinette.mailer.service.ContenuEmailRepository;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

@Service("contenuEmailRepository")
public class ContenuEmailRepositoryFreemarker implements ContenuEmailRepository {
	
	private static final String EMAIL_TEMPLATE_EXCEPTION = "Impossible de charger le template";
    
	private Configuration configuration =  new Configuration(Configuration.VERSION_2_3_22);

    @Override
    public ContenuEmail ficheOdm(String objet, String templateName, String civilite, String nom, String prenom,
    					long odmId, String dest, String etat, String debutOdm, String finOdm) throws EmailTemplateException {
    	Map<String, String> valeurs = new HashMap<>();
    	valeurs.put("civilite", civilite);
    	valeurs.put("nom", nom);
    	valeurs.put("prenom", prenom);
    	valeurs.put("odmId", String.valueOf(odmId));
    	valeurs.put("dest", dest);
    	valeurs.put("etat", etat);
    	valeurs.put("debutOdm", debutOdm);
    	valeurs.put("finOdm", finOdm);
    	
        String corpsMail = processCorpsEmail(valeurs, templateName);
        return new ContenuEmail(objet, corpsMail);
    }

	@Override
	public ContenuEmail autorisationDeSortie(String objet, String templateName, String civilite, String nom, String prenom) throws EmailTemplateException {
		Map<String, String> valeurs = new HashMap<>();
    	valeurs.put("civilite", civilite);
    	valeurs.put("nom", nom);
    	valeurs.put("prenom", prenom);
    	
        String corpsMail = processCorpsEmail(valeurs, templateName);
        return new ContenuEmail(objet, corpsMail);
	}
    
    private String processCorpsEmail(Map<String, String> valeursDynamiques, String templateName) throws EmailTemplateException {
        try (StringWriter writer = new StringWriter()) {
        	configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setLogTemplateExceptions(false);
            configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
        	Template template = configuration.getTemplate(templateName);
            template.process(valeursDynamiques, writer);
            return writer.toString();

        } catch (Exception exception) {
            throw new EmailTemplateException(EMAIL_TEMPLATE_EXCEPTION);
        }
    }

}
