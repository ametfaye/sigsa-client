package sn.awi.pgca.utils;

import javax.faces.context.FacesContext;

public class SpringJSFUtil {
	public static Object getBean(String beanName) {
		if (beanName == null) {
			return null;
		}
		return getValue("#{" + beanName + "}");
	}

	private static Object getValue(String expression) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, expression, Object.class);
	}
}
