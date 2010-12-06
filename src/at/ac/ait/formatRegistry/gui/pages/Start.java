package at.ac.ait.formatRegistry.gui.pages;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.util.TextStreamResponse;

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

    @Persist(PersistenceConstants.FLASH)
    @Property
    private String message;

    /*
    Object onUploadException(FileUploadException ex)
    {
        message = "Upload exception: " + ex.getMessage();
        return this;
    }
    */
    
    public List<FileFormat> getResultsList() {
		return resultsList;
	}

    public void onSuccessFromUploadFile()
    {
        File copied = new File("C:/development/eclipse-workspace/registry/WebContent/WEB-INF/dat/test/" + file.getFileName());
        file.write(copied);
    }
	
	Object onActionFromListAll()
    {
		resultsList = formatDAO.findAllFormats();
        return listFormats.initialize(resultsList);
    }

	Object onSuccessFromSearchNames()
    {
		resultsList = formatDAO.findFormatsByName(nameFragment);
        return listFormats.initialize(resultsList);
    }

	Object onSuccessFromSearchExtensions()
    {
		resultsList = formatDAO.findFormatsByExtension(extensionFragment);
        return listFormats.initialize(resultsList);
    }

	Object onSuccessFromSearchPronomIds()
    {
		resultsList = formatDAO.findFormatsByPronomId(idFragment);
        return listFormats.initialize(resultsList);
    }

	Object onSuccessFromExport()
    {
		String text = formatDAO.exportToFido();
		return new TextStreamResponse("text/xml", text);
        //return this;
    }

}