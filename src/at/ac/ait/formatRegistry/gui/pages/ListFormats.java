package at.ac.ait.formatRegistry.gui.pages;

import java.util.List;
import org.apache.tapestry5.annotations.Persist;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat;

public class ListFormats {
	private FileFormat format;
	
	@Persist
	private List<FileFormat> resultsList;

	Object initialize(List<FileFormat> results) {
		this.resultsList = results;
		return this;
	}

	public List<FileFormat> getFormats() {
		return resultsList;
	}

	public FileFormat getFormat() {
		return format;
	}

	public void setFormat(FileFormat format) {
		this.format = format;
	}

}
