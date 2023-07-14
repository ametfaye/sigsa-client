package sn.awi.pgca.web;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
 
public class EncodeFilter implements Filter , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2967746985703513734L;
	/**
	 * 
	 */

	public void init(final FilterConfig filterConfig) throws ServletException {
	}
	
	public void destroy() {
	}
 
	public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Setting the character set for the request
		request.setCharacterEncoding("UTF-8");
 
		// pass the request on
		chain.doFilter(request, response);
 
		// Setting the character set for the response
		response.setContentType("text/html; charset=UTF-8");
	}
}