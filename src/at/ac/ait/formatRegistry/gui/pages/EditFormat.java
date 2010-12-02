package at.ac.ait.formatRegistry.gui.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.PageActivationContext;
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
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.InternalSignature.ByteSequence;
import at.ac.ait.formatRegistry.gui.pages.EditByteSequence.ByteSequenceHolder;
import at.ac.ait.formatRegistry.gui.services.FileFormatDAO;

public class EditFormat {
    @Inject
    private FileFormatDAO formatDAO;
	
	@InjectPage
	private EditByteSequence editByteSequence;
	
	@InjectPage
	private EditFidoPattern editFidoPattern;
	
	@InjectPage
	private ViewFormat viewFormat;

    @Property
	@Persist
    private FileFormat format;
    private String formatID = "";

	@Property
	@Persist
	private List<FileFormatIdentifierHolder> _fileFormatIdentifierHolders;

	@Property
	@Persist
	private List<RelatedFormatHolder> _relatedFormatHolders;

	@Property
	@Persist
	private List<InternalSignatureHolder> _internalSignatureHolders;

	@Property
	@Persist
	private List<FidoSignatureHolder> _fidoSignatureHolders;

	@Property
	private FileFormatIdentifierHolder _fileFormatIdentifierHolder;

	@Property
	private RelatedFormatHolder _relatedFormatHolder;
	
	@Property
	private InternalSignatureHolder _internalSignatureHolder;
	
	@Property
	private FidoSignatureHolder _fidoSignatureHolder;

	Object onActionFromEditSequence(String id) {
		List<InternalSignature> signatures = format.getInternalSignature();
		for (InternalSignature signature : signatures) {
			if (signature.getSignatureID().equals(id)) {
				editByteSequence.initialize(format, signature);
				return editByteSequence;
			}
		}
		return this;
	}
	
	Object onActionFromEditPattern(String id) {
		List<FidoSignature> fSignatures = format.getFidoSignature();
		for (FidoSignature fSignature : fSignatures) {
			if (fSignature.getFidoSignatureID().equals(id)) {
				editFidoPattern.initialize(format, fSignature);
				return editFidoPattern;
			}
		}
		return this;
	}
	
	Object initialize(String id) {
		onActivate(id);
		return this;
	}

    void onActivate(String id) {
    	_fileFormatIdentifierHolders = new ArrayList<FileFormatIdentifierHolder>();
    	_relatedFormatHolders = new ArrayList<RelatedFormatHolder>();
    	_internalSignatureHolders = new ArrayList<InternalSignatureHolder>();
    	_fidoSignatureHolders = new ArrayList<FidoSignatureHolder>();
    	if (id!=null) {
    		if (!id.equals("")) {
    			FileFormat testFormat = formatDAO.find(id);
		    	if (testFormat!=null) {
		    		format = testFormat;
		    		formatID = id;
		    		Iterator<FileFormatIdentifier> it1 = format.getFileFormatIdentifier().iterator();
		    		while (it1.hasNext()) {
		    			_fileFormatIdentifierHolders.add(new FileFormatIdentifierHolder(it1.next(), false, 0 - System.nanoTime()));
		    		}
		    		Iterator<RelatedFormat> it2 = format.getRelatedFormat().iterator();
		    		while (it2.hasNext()) {
		    			_relatedFormatHolders.add(new RelatedFormatHolder(it2.next(), false, 0 - System.nanoTime()));
		    		}
		    		Iterator<InternalSignature> it3 = format.getInternalSignature().iterator();
		    		while (it3.hasNext()) {
		    			InternalSignature iS = it3.next();
		    			_internalSignatureHolders.add(new InternalSignatureHolder(iS, false, 0 - System.nanoTime()));
		    		}
		    		Iterator<FidoSignature> it4 = format.getFidoSignature().iterator();
		    		while (it4.hasNext()) {
		    			FidoSignature fS = it4.next();
		    			_fidoSignatureHolders.add(new FidoSignatureHolder(fS, false, 0 - System.nanoTime()));
		    		}
		    	}
    		}
    	}
    	if (format==null) {
    		format = new FileFormat();
    		formatID = formatDAO.getNewFormatID();
    		format.setFormatID(formatID);
    	}
    }

    public Object onSuccess() {
		for (FileFormatIdentifierHolder ffih : _fileFormatIdentifierHolders) {
			FileFormatIdentifier identifer = ffih.getFileFormatIdentifier();
			if (ffih.isNew()) {
				format.getFileFormatIdentifier().add(identifer);
			} else if (ffih.isDeleted()) {
				format.getFileFormatIdentifier().remove(identifer);
			}
		}   	
		for (RelatedFormatHolder rfh : _relatedFormatHolders) {
			RelatedFormat relatedFormat = rfh.getRelatedFormat();
			if (rfh.isNew()) {
				format.getRelatedFormat().add(relatedFormat);
			} else if (rfh.isDeleted()) {
				format.getRelatedFormat().remove(relatedFormat);
			}
		}   	
		for (InternalSignatureHolder ish : _internalSignatureHolders) {
			InternalSignature signature = ish.getInternalSignature();
			if (ish.isNew()) {
				format.getInternalSignature().add(signature);
			} else if (ish.isDeleted()) {
				format.getInternalSignature().remove(signature);
			}
		}   	
		for (FidoSignatureHolder fsh : _fidoSignatureHolders) {
			FidoSignature fSignature = fsh.getFidoSignature();
			if (fsh.isNew()) {
				format.getFidoSignature().add(fSignature);
			} else if (fsh.isDeleted()) {
				format.getFidoSignature().remove(fSignature);
			}
		}   	
        formatDAO.save(format);
        return viewFormat.initialize(format.getFormatID());
    }
    
    FileFormatIdentifierHolder onAddRowFromFileIdentifiers() {
    	FileFormatIdentifier newFfi = new FileFormatIdentifier();
    	FileFormatIdentifierHolder newFfiHolder = new FileFormatIdentifierHolder(newFfi, true, 0 - System.nanoTime());
		_fileFormatIdentifierHolders.add(newFfiHolder);
		return newFfiHolder;
	}

	void onRemoveRowFromFileIdentifiers(FileFormatIdentifierHolder fileFormatIdentifier) {
		if (fileFormatIdentifier.isNew()) {
			_fileFormatIdentifierHolders.remove(fileFormatIdentifier);;
		}
		else {
			fileFormatIdentifier.setDeleted(true);
		}
	}
	
	public ValueEncoder<FileFormatIdentifierHolder> getFileFormatIdentifierEncoder() {
		return new ValueEncoder<FileFormatIdentifierHolder>() {

			public String toClient(FileFormatIdentifierHolder value) {
				Long key = value.getKey();
				return key.toString();
			}

			public FileFormatIdentifierHolder toValue(String keyAsString) {
				Long key = new Long(keyAsString);
				for (FileFormatIdentifierHolder holder : _fileFormatIdentifierHolders) {
					if (holder.getKey().equals(key)) {
						return holder;
					}
				}
				throw new IllegalArgumentException("Received key \"" + key
						+ "\" which has no counterpart in this collection: " + _fileFormatIdentifierHolders);
			}
		};
	}

	public class FileFormatIdentifierHolder {
		private FileFormatIdentifier _ffi;
		private Long _key;
		private boolean _new;
		private boolean _deleted;
		FileFormatIdentifierHolder(FileFormatIdentifier ffi, boolean isNew, Long key) {
			_ffi = ffi;
			_key = key;
			_new = isNew;
		}
		public FileFormatIdentifier getFileFormatIdentifier() {
			return _ffi;
		}
		public Long getKey() {
			return _key;
		}
		public boolean isNew() {
			return _new;
		}

		public boolean setDeleted(boolean deleted) {
			return _deleted = deleted;
		}

		public boolean isDeleted() {
			return _deleted;
		}
	}

    RelatedFormatHolder onAddRowFromRelatedFormats() {
    	RelatedFormat newRf = new RelatedFormat();
    	RelatedFormatHolder newRfHolder = new RelatedFormatHolder(newRf, true, 0 - System.nanoTime());
    	_relatedFormatHolders.add(newRfHolder);
		return newRfHolder;
	}

	void onRemoveRowFromRelatedFormats(RelatedFormatHolder relatedFormat) {
		if (relatedFormat.isNew()) {
			_relatedFormatHolders.remove(relatedFormat);;
		}
		else {
			relatedFormat.setDeleted(true);
		}
	}
	
	public ValueEncoder<RelatedFormatHolder> getRelatedFormatEncoder() {
		return new ValueEncoder<RelatedFormatHolder>() {

			public String toClient(RelatedFormatHolder value) {
				Long key = value.getKey();
				return key.toString();
			}

			public RelatedFormatHolder toValue(String keyAsString) {
				Long key = new Long(keyAsString);
				for (RelatedFormatHolder holder : _relatedFormatHolders) {
					if (holder.getKey().equals(key)) {
						return holder;
					}
				}
				throw new IllegalArgumentException("Received key \"" + key
						+ "\" which has no counterpart in this collection: " + _fileFormatIdentifierHolders);
			}
		};
	}

	public class RelatedFormatHolder {
		private RelatedFormat _rf;
		private Long _key;
		private boolean _new;
		private boolean _deleted;
		RelatedFormatHolder(RelatedFormat rf, boolean isNew, Long key) {
			_rf = rf;
			_key = key;
			_new = isNew;
		}
		public RelatedFormat getRelatedFormat() {
			return _rf;
		}
		public Long getKey() {
			return _key;
		}
		public boolean isNew() {
			return _new;
		}

		public boolean setDeleted(boolean deleted) {
			return _deleted = deleted;
		}

		public boolean isDeleted() {
			return _deleted;
		}
	}

    InternalSignatureHolder onAddRowFromInternalSignatures() {
    	InternalSignature newIs = new InternalSignature();
    	newIs.setSignatureID(formatDAO.getNewInternalSignatureID());
    	InternalSignatureHolder newIsHolder = new InternalSignatureHolder(newIs, true, 0 - System.nanoTime());
    	_internalSignatureHolders.add(newIsHolder);
		return newIsHolder;
	}

	void onRemoveRowFromInternalSignatures(InternalSignatureHolder internalSignature) {
		if (internalSignature.isNew()) {
			_internalSignatureHolders.remove(internalSignature);;
		}
		else {
			internalSignature.setDeleted(true);
		}
	}
	
	public ValueEncoder<InternalSignatureHolder> getInternalSignatureEncoder() {
		return new ValueEncoder<InternalSignatureHolder>() {

			public String toClient(InternalSignatureHolder value) {
				Long key = value.getKey();
				return key.toString();
			}

			public InternalSignatureHolder toValue(String keyAsString) {
				Long key = new Long(keyAsString);
				for (InternalSignatureHolder holder : _internalSignatureHolders) {
					if (holder.getKey().equals(key)) {
						return holder;
					}
				}
				throw new IllegalArgumentException("Received key \"" + key
						+ "\" which has no counterpart in this collection: " + _internalSignatureHolders);
			}
		};
	}
	
	public class InternalSignatureHolder {
		private InternalSignature _is;
		private Long _key;
		private boolean _new;
		private boolean _deleted;
		InternalSignatureHolder(InternalSignature is, boolean isNew, Long key) {
			_is = is;
			_key = key;	    			
			_new = isNew;
		}
		public InternalSignature getInternalSignature() {
			return _is;
		}
		public Long getKey() {
			return _key;
		}
		public boolean isNew() {
			return _new;
		}

		public boolean setDeleted(boolean deleted) {
			return _deleted = deleted;
		}

		public boolean isDeleted() {
			return _deleted;
		}

	}

    FidoSignatureHolder onAddRowFromFidoSignatures() {
    	FidoSignature newFs = new FidoSignature();
    	newFs.setFidoSignatureID(formatDAO.getNewFidoSignatureID());
    	FidoSignatureHolder newFsHolder = new FidoSignatureHolder(newFs, true, 0 - System.nanoTime());
    	_fidoSignatureHolders.add(newFsHolder);
		return newFsHolder;
	}

	void onRemoveRowFromFidoSignatures(FidoSignatureHolder fidoSignature) {
		if (fidoSignature.isNew()) {
			_fidoSignatureHolders.remove(fidoSignature);;
		}
		else {
			fidoSignature.setDeleted(true);
		}
	}

	public ValueEncoder<FidoSignatureHolder> getFidoSignatureEncoder() {
		return new ValueEncoder<FidoSignatureHolder>() {

			public String toClient(FidoSignatureHolder value) {
				Long key = value.getKey();
				return key.toString();
			}

			public FidoSignatureHolder toValue(String keyAsString) {
				Long key = new Long(keyAsString);
				for (FidoSignatureHolder holder : _fidoSignatureHolders) {
					if (holder.getKey().equals(key)) {
						return holder;
					}
				}
				throw new IllegalArgumentException("Received key \"" + key
						+ "\" which has no counterpart in this collection: " + _fidoSignatureHolders);
			}
		};
	}


	public class FidoSignatureHolder {
		private FidoSignature _fs;
		private Long _key;
		private boolean _new;
		private boolean _deleted;
		FidoSignatureHolder(FidoSignature fs, boolean isNew, Long key) {
			_fs = fs;
			_key = key;	    			
			_new = isNew;
		}
		public FidoSignature getFidoSignature() {
			return _fs;
		}
		public Long getKey() {
			return _key;
		}
		public boolean isNew() {
			return _new;
		}

		public boolean setDeleted(boolean deleted) {
			return _deleted = deleted;
		}

		public boolean isDeleted() {
			return _deleted;
		}

	}

	List<String> onProvideCompletionsFromRelFormName(String partial) {
		List<String> matches = new ArrayList<String>();

		for (String formatName : formatDAO.getFormatNamesSet()) {
			if (formatName.startsWith(partial)) {
				matches.add(formatName);
			}
		}

		return matches;
	}

}
