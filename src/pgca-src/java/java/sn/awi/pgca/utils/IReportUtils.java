package sn.awi.pgca.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class IReportUtils {

	/**
	 * Cette méthode permet de générer un fichier pdf à partir d'une template
	 * JRXML et du fichier de données XML permettant d'alimenter le fichier JRXML.
	 * Cette génération se fait en 3 étapes : 
	 * 1. Compilation du fichier JRXMLpour avoir un JasperReport 
	 * 2. Création d'un JasperPrint à partir du fichier compilé et du fichier de données XML 
	 * 3. Export du JaspertPrint vers un fichier PDF
	 * 
	 * @param jrxmlFileName
	 *          : Nom complet du fichier contenu le template jasper. Example :
	 *          "C:\\Users\\awaconsulting\\workspace\\rccm-sen\\report\\Personne1.jrxml"
	 * @param xmlDataFileName
	 *          : Nom complet du fichier de données pour l'alimentation du
	 *          template jasper, example :
	 *          "C:\\Users\\awaconsulting\\workspace\\rccm-sen\\report\\Personne1Data.xml"
	 * @param recordPath
	 *          : Nom de l'entité à partir de la quelle lire les données XML,
	 *          données non obligatoire, example : "/Personne"
	 * @param pdfFileToCreate
	 *          : nom du fichier pdf à créer
	 * @param pdfDirectory
	 *          : nom du répertoire dans lequel créé le fichier PDF
	 * @throws Exception
	 */
	public static void generatePdfFile(String jrxmlFileName, String xmlDataFileName, String recordPath, String pdfFileToCreate, String pdfDirectory) throws Exception {
		try {
			// Controle de coh�rence des param�tres

			if (jrxmlFileName == null || jrxmlFileName.length() == 0)
				throw new Exception("JRXML File is mandatory");
			if (xmlDataFileName == null || xmlDataFileName.length() == 0)
				throw new Exception("XML Data File is mandatory");
			if (pdfDirectory == null || pdfDirectory.length() == 0)
				throw new Exception("PDF Directory is mandatory");
			if (pdfFileToCreate == null || pdfFileToCreate.length() == 0)
				throw new Exception("PDF File is mandatory");

			// - Chargement et compilation du rapport
			JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFileName);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			// G�n�ration du JasperPrint
			JRDataSource jrxmlds = null;
			if (recordPath == null || recordPath.trim().length() == 0)
				jrxmlds = new JRXmlDataSource(xmlDataFileName);
			else
				jrxmlds = new JRXmlDataSource(xmlDataFileName, recordPath);
			Map<String, Object> hm = new HashMap<String, Object>();
			JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, jrxmlds);

			// Conversion en fichier PDF
			JasperExportManager.exportReportToPdfFile(print, pdfDirectory + pdfFileToCreate);
		} catch (JRException jre) {
			throw new Exception("Impossible to generate pdf file " + jre.getMessage(), jre);
		}
	}

	/**
	 * Cette méthode permet de générer un fichier pdf à partir d'une template
	 * JRXML et du fichier de données XML permettant d'alimenter le fichier JRXML.
	 * Cette génération se fait en 3étapes : 
	 * 1. Compilation du fichier JRXML pour avoir un JasperReport 
	 * 2. Création d'un JasperPrint à partir du fichier compilé et du fichier de données XML 
	 * 3. Export du JaspertPrint vers un fichier PDF
	 * 
	 * @param jrxmlFileName
	 *          : Nom complet du fichier contenu le template jasper. Example :
	 *          "C:\\Users\\awaconsulting\\workspace\\rccm-sen\\report\\Personne1.jrxml"
	 * @param pdfFileToCreate
	 *          : nom du fichier pdf � cr�er
	 * @param pdfDirectory
	 *          : nom du répertoire dans lequel créé le fichier PDF
	 * @param elementCol
	 *          : liste des données pour la génération du rapport
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void generatePdfFile(String jrxmlFileName, String pdfFileToCreate, String pdfDirectory, Collection elementCol) throws Exception {
		try {
			// Controle de cohérence des paramètres

			if (jrxmlFileName == null || jrxmlFileName.length() == 0) {
				System.out.println("IReportUtils.generatePdfFile()" + "------------------- the file is " + jrxmlFileName);
				throw new Exception("JRXML File is mandatory");

			}
			if (pdfDirectory == null || pdfDirectory.length() == 0)
				throw new Exception("PDF Directory is mandatory");
			if (pdfFileToCreate == null || pdfFileToCreate.length() == 0)
				throw new Exception("PDF File is mandatory");

			JasperReport jasperReport = null;
			JasperPrint jasperPrint = null;
			JasperDesign jasperDesign = null;
			Map<String, Object> parameters = new HashMap<String, Object>();
			// - Chargement et compilation du rapport
			jasperDesign = JRXmlLoader.load(jrxmlFileName);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);

			// Génération du JasperPrint
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(elementCol));
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfDirectory + pdfFileToCreate);

		} catch (JRException jre) {
			throw new Exception("Impossible to generate pdf file " + jre.getMessage(), jre);
		}
	}

}
