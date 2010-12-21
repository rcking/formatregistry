package at.ac.ait.formatRegistry.gui.services;

import java.io.File;
import java.util.List;
import java.util.Set;

import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat;

public interface FileFormatDAO {
  	List<FileFormat> findAllFormats();
  	List<FileFormat> findFormatsByName(String fragment);
  	List<FileFormat> findFormatsByExtension(String fragment);
  	List<FileFormat> findFormatsByPronomId(String fragment);
  	FileFormat find(String id);
  	FileFormat getNewFormat();
  	void save(FileFormat format);
  	void delete(FileFormat format);
  	File exportToFido();
  	File exportToPronom();
  	String importFromFido(File file);
  	FileFormat findFormatByName(String name);
  	String getNewFormatID();
  	String getNewInternalSignatureID();
  	String getNewFidoSignatureID();
  	String getNewPronomID();
  	String getWorkingDirectoryPath();
  	Set<String> getFormatNamesSet();
}
