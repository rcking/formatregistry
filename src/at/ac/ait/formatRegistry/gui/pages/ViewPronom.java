package at.ac.ait.formatRegistry.gui.pages;

import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.CompressionType;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.Document;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.ExternalSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.FidoSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.FileFormatIdentifier;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.InternalSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.RelatedFormat;
import at.ac.ait.formatRegistry.gui.services.FileFormatDAO;

public class ViewPronom {
    @Inject
    private FileFormatDAO formatDAO;
    private FileFormat format = null;
	private RelatedFormat aRelatedFormat;
    private String formatID = "";
    
    @Property
    private InternalSignature signature;
    
    @Property
    private FidoSignature fSignature;
    
    void onActivate(String id) {
    	if (id!=null) {
    		if (!id.equals("")) {
    			String testId = "fmt/" + id;
    			List<FileFormat> testFormats = formatDAO.findFormatsByPronomId(testId);
    			Iterator<FileFormat> it = testFormats.iterator();
    			while (it.hasNext()) {
    				FileFormat testFormat = it.next();
    				if (testFormat.getPronomID().equals(testId)) {
			    		format = testFormat;
			    		formatID = format.getFormatID();
    				}
		    	}
    		}
    	}
    	if (format==null) {
    		format = new FileFormat();
    		formatID = "false";
    	}
    }
    
    String onPassivate() {
    	return formatID;
    }
    
    public String getFormatID() {
    	return formatID;
    }

    public FileFormat getFormat() {
    	return format;
    }
    
    public List<FileFormatIdentifier> getFormatIdentifier() {
    	return format.getFileFormatIdentifier();
    }
    
    public List<Document> getDocument() {
    	return format.getDocument();
    }
    
    public List<ExternalSignature> getExternalSignature() {
    	return format.getExternalSignature();
    }

    public List<InternalSignature> getInternalSignature() {
    	return format.getInternalSignature();
    }

    public List<FidoSignature> getFidoSignature() {
    	return format.getFidoSignature();
    }

    public List<RelatedFormat> getRelatedFormat() {
    	return format.getRelatedFormat();
    }
    
    public List<CompressionType> getCompressionType() {
    	return format.getCompressionType();
    }

	public RelatedFormat getARelatedFormat() {
		return aRelatedFormat;
	}

	public void setARelatedFormat(RelatedFormat format) {
		this.aRelatedFormat = format;
	}

}
