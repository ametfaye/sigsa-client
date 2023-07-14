package sn.awi.pgca.dataModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userTheme")
public class UtilisateurTheme implements Serializable, GenericModel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long idTheme;

	@Column(length=100)
	private String themeName;  // couleur de base du theme
	

	@Column(length=100)
	private String themeHeader;
	

	@Column(length=100)
	private String themeColor;  // couleur de base du theme


	
	public Long getIdTheme() {
		return idTheme;
	}


	public void setIdTheme(Long idTheme) {
		this.idTheme = idTheme;
	}


	public String getThemeName() {
		return themeName;
	}


	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}


	public String getThemeHeader() {
		return themeHeader;
	}


	public void setThemeHeader(String themeHeader) {
		this.themeHeader = themeHeader;
	}


	public String getThemeColor() {
		return themeColor;
	}


	public void setThemeColor(String themeColor) {
		this.themeColor = themeColor;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}


}
