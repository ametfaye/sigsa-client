package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class ThemeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9005733466102851726L;
	/**
	 * 
	 */	
	
	
	Long idTheme;
	private String nomTheme; 
	
	private String headerTheme;

	private String colorTheme;

	public Long getIdTheme() {
		return idTheme;
	}

	public void setIdTheme(Long idTheme) {
		this.idTheme = idTheme;
	}

	public String getNomTheme() {
		return nomTheme;
	}

	public void setNomTheme(String nomTheme) {
		this.nomTheme = nomTheme;
	}

	public String getHeaderTheme() {
		return headerTheme;
	}

	public void setHeaderTheme(String headerTheme) {
		this.headerTheme = headerTheme;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getColorTheme() {
		return colorTheme;
	}

	public void setColorTheme(String colorTheme) {
		this.colorTheme = colorTheme;
	}
	

}
