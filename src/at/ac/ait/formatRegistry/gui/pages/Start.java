package at.ac.ait.formatRegistry.gui.pages;

import java.util.List;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import at.ac.ait.formatRegistry.gui.services.FileFormatDAO;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat;

/**
 * Start page of application app.
 */
public class Start {
	@Inject
	private FileFormatDAO formatDAO;

	@InjectPage
	private ListFormats listFormats;

	@Property
	private String nameFragment;

	@Property
	private String extensionFragment;

	@Property
	private String idFragment;

	@Property
	private UploadedFile file;

	private List<FileFormat> resultsList;

	public List<FileFormat> getResultsList() {
		return resultsList;
	}

	Object onActionFromListAll() {
		resultsList = formatDAO.findAllFormats();
		return listFormats.initialize(resultsList);
	}

	Object onSuccessFromSearchNames() {
		resultsList = formatDAO.findFormatsByName(nameFragment);
		return listFormats.initialize(resultsList);
	}

	Object onSuccessFromSearchExtensions() {
		resultsList = formatDAO.findFormatsByExtension(extensionFragment);
		return listFormats.initialize(resultsList);
	}

	Object onSuccessFromSearchPronomIds() {
		resultsList = formatDAO.findFormatsByPronomId(idFragment);
		return listFormats.initialize(resultsList);
	}
	
}