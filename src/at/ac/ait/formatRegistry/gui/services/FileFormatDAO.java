package at.ac.ait.formatRegistry.gui.services;

import java.util.List;
import java.util.Set;

import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.InternalSignature.ByteSequence;

public interface FileFormatDAO {
  	List<FileFormat> findAllFormats();
  	List<FileFormat> findFormatsByName(String fragment);
  	List<FileFormat> findFormatsByExtension(String fragment);
  	List<FileFormat> findFormatsByPronomId(String fragment);
  	FileFormat find(String id);
  	void save(FileFormat format);
  	void delete(FileFormat format);
  	void exportToFido();
  	FileFormat findFormatByName(String name);
  	String getNewFormatID();
  	String getNewInternalSignatureID();
  	String getNewFidoSignatureID();
  	Set<String> getFormatNamesSet();
}
