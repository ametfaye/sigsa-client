package sn.awi.pgca.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class DownloadTools {

	private static final Log													logger								= LogFactory.getLog(DownloadTools.class);


	public static String download(String nomFichier, String mime, byte[] flux) {
		try {

			FacesContext fc = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
			response.reset();
			int DEFAULT_BUFFER_SIZE = 1024;
			response.setBufferSize(DEFAULT_BUFFER_SIZE);
			response.setContentType(mime);
			response.setHeader("Content-Length", String.valueOf(flux.length));
			response.setHeader("Content-Disposition", "attachment; filename=\"" + nomFichier + "\"");

			// Prepare streams.
			BufferedInputStream input = null;
			BufferedOutputStream output = null;

			try {
				// Open streams.
				input = new BufferedInputStream(new ByteArrayInputStream(flux), DEFAULT_BUFFER_SIZE);
				output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

				// Write file contents to response.
				byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
				int length;
				while ((length = input.read(buffer)) > 0) {
					output.write(buffer, 0, length);
				}
				response.flushBuffer();
			} finally {
				// Gently close streams.
				close(output);
				close(input);
			}
			fc.responseComplete();
		} catch (IOException e) {
			logger.warn("Impossible d'envoyer le document GED à l'utilisateur.", e);
		}
		return null;
	}
	/**
	 * Méthode utilitaire de fermerture d'un flux.
	 * 
	 * @param resource
	 *          La ressource à refermer.
	 */
	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				logger.warn("Impossible de fermer un flux lors de l'envoi d'un fichier GED", e);
			}
		}
	}
}
