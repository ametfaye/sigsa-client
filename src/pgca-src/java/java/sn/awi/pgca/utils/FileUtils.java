package sn.awi.pgca.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 
 * Classe contenant les outils permettant de manipuler les fichiers
 * 
 * @author Mounés Ronan
 * 
 */
public abstract class FileUtils {
	/**
	 * Copier un fichier
	 * 
	 * @param in
	 *            Fichier source
	 * @param out
	 *            Fichier destination
	 * @throws IOException
	 */
	public static void copyFile(File in, File out) throws IOException {
		FileInputStream fins = new FileInputStream(in);
		FileChannel inChannel = fins.getChannel();
		FileOutputStream fouts = new FileOutputStream(out);
		FileChannel outChannel = fouts.getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			throw e;
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (fins !=null)
				fins.close();
			if (outChannel != null)
				outChannel.close();
			if (fouts != null)
				fouts.close();
		}
	}

}