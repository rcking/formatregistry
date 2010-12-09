package at.ac.ait.formatRegistry.gui.pages;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
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
	 * Object onUploadException(FileUploadException ex) { message =
	 * "Upload exception: " + ex.getMessage(); return this; }
	 */

	public List<FileFormat> getResultsList() {
		return resultsList;
	}

	public void onSuccessFromUploadFile() {
		File copied = new File(formatDAO.getWorkingDirectoryPath() + "/" + file.getFileName());
		file.write(copied);
		formatDAO.importFromFido(copied);
		copied.delete();
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

	Object onSuccessFromExport() {
		return new StreamResponse() {
			String text = formatDAO.exportToFido();
			ByteArrayInputStream inputStream;

			@Override
			public String getContentType() {
				return "application/octet-stream";
			}

			@Override
			public InputStream getStream() throws IOException {
				return inputStream;
			}

			@Override
			public void prepareResponse(Response response) {
				try {
					inputStream = new ByteArrayInputStream(
							text.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control",
						"must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Content-Disposition",
						"attachment; filename=formats.xml");
				response.setHeader("Content-Length",
						"" + inputStream.available());
			}
		};

	}

}