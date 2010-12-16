package at.ac.ait.formatRegistry.gui.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.services.UploadedFile;

import at.ac.ait.formatRegistry.gui.services.FileFormatDAO;


/**
 * Start page of application app.
 */
public class ImportExport {
	@Inject
	private FileFormatDAO formatDAO;

	@Property
	private UploadedFile file;

	@Persist(PersistenceConstants.FLASH)
	@Property
	private String message;

	/*
	 * Object onUploadException(FileUploadException ex) { message =
	 * "Upload exception: " + ex.getMessage(); return this; }
	 */

	public void onSuccessFromUploadFile() {
		File copied = new File(formatDAO.getWorkingDirectoryPath() + "/" + file.getFileName());
		file.write(copied);
		formatDAO.importFromFido(copied);
		copied.delete();
	}

	Object onSuccessFromExport() {
		return new StreamResponse() {
			File file = formatDAO.exportToFido();
			FileInputStream inputStream;

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
					//inputStream = new ByteArrayInputStream(text.getBytes("UTF-8"));
					inputStream = new FileInputStream(file);
					response.setHeader("Expires", "0");
					response.setHeader("Cache-Control",
							"must-revalidate, post-check=0, pre-check=0");
					response.setHeader("Content-Disposition",
							"attachment; filename=formats.xml");
					response.setHeader("Content-Length",
							"" + inputStream.available());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

	}
	
	Object onSuccessFromPronomExport() {
		return new StreamResponse() {
			File file = formatDAO.exportToPronom();
			FileInputStream inputStream;

			@Override
			public String getContentType() {
				return "application/zip";
			}

			@Override
			public InputStream getStream() throws IOException {
				return inputStream;
			}

			@Override
			public void prepareResponse(Response response) {
				try {
					//inputStream = new ByteArrayInputStream(text.getBytes("UTF-8"));
					inputStream = new FileInputStream(file);
					response.setHeader("Expires", "0");
					response.setHeader("Cache-Control",
							"must-revalidate, post-check=0, pre-check=0");
					response.setHeader("Content-Disposition",
							"attachment; filename=pronom.zip");
					response.setHeader("Content-Length",
							"" + inputStream.available());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

	}
	
}
