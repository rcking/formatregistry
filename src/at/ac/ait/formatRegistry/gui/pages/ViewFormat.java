package at.ac.ait.formatRegistry.gui.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Persist;
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

public class ViewFormat {
    @Inject
    private FileFormatDAO formatDAO;
	private RelatedFormat aRelatedFormat;
    private String formatID = "";
    
    @Property
    private InternalSignature signature;
    
    @Property
    private FidoSignature fSignature;

    @Property
	@Persist
    private FileFormat format;

    Object initialize(String id) {
		onActivate(id);
		return this;
	}

    void onActivate(String id) {
    	if (id!=null) {
    		if (!id.equals("")) {
    			FileFormat testFormat = formatDAO.find(id);
		    	if (testFormat!=null) {
		    		format = testFormat;
		    		formatID = id;
		    	}
    		}
    	}
    }
    
    public String getFormatID() {
    	return formatID;
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
