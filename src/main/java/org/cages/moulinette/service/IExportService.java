package org.cages.moulinette.service;

import java.io.ByteArrayInputStream;

public interface IExportService {

	public ByteArrayInputStream exportOdmPdf(long odmId);

	public ByteArrayInputStream exportAstPdf(String matricule, long odmId);

	public ByteArrayInputStream exportTDRPdf(long odmId);

}
