package at.ac.ait.formatRegistry.gui.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.ExternalSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.FidoSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.FileFormatIdentifier;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.InternalSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.RelatedFormat;
import uk.gov.nationalarchives.pronom.PRONOMReport.SignatureTypes;
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
    private String formatID;

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
	private List<ExternalSignatureHolder> _externalSignatureHolders;

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
	private ExternalSignatureHolder _externalSignatureHolder;
	
	@Property
	private FidoSignatureHolder _fidoSignatureHolder;
	
	private boolean patternTrigger = false;
	private boolean sequenceTrigger = false;
	private FidoSignature theFidoSignature = null;
	private InternalSignature theInternalSignature = null;

	Object initialize(String id) {
		onActivate(id);
		return this;
	}
	
	void onActivate(String id) {
		this.formatID = id;
	}
    
    String onPassivate() {
    	return formatID;
    }

    void setupRender() {
    	_fileFormatIdentifierHolders = new ArrayList<FileFormatIdentifierHolder>();
    	_relatedFormatHolders = new ArrayList<RelatedFormatHolder>();
    	_internalSignatureHolders = new ArrayList<InternalSignatureHolder>();
    	_externalSignatureHolders = new ArrayList<ExternalSignatureHolder>();
    	_fidoSignatureHolders = new ArrayList<FidoSignatureHolder>();
    	if (formatID!=null) {
    		if (!formatID.equals("")) {
    			FileFormat testFormat = formatDAO.find(formatID);
		    	if (testFormat!=null) {
		    		format = testFormat;
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
		    		Iterator<ExternalSignature> it5 = format.getExternalSignature().iterator();
		    		while (it5.hasNext()) {
		    			ExternalSignature eS = it5.next();
		    			_externalSignatureHolders.add(new ExternalSignatureHolder(eS, false, 0 - System.nanoTime()));
		    		}
		    	}
    		}
    	}
    	if (format==null) {
    		format = formatDAO.getNewFormat();
    		Iterator<FileFormatIdentifier> it1 = format.getFileFormatIdentifier().iterator();
    		while (it1.hasNext()) {
    			_fileFormatIdentifierHolders.add(new FileFormatIdentifierHolder(it1.next(), false, 0 - System.nanoTime()));
    		}
    	}
    }

	public void onSelectedFromEditPattern(String id) {
		for (FidoSignatureHolder fsh : _fidoSignatureHolders) {
			FidoSignature fSignature = fsh.getFidoSignature();
			if (fsh.isNew()) {
				format.getFidoSignature().add(fSignature);
			} else if (fsh.isDeleted()) {
				format.getFidoSignature().remove(fSignature);
			}
		}
		_fidoSignatureHolders.clear();
		List<FidoSignature> fSignatures = format.getFidoSignature();
		for (FidoSignature fSignature : fSignatures) {
			if (fSignature.getFidoSignatureID().equals(id)) {
				theFidoSignature = fSignature;
				patternTrigger = true;
			}
		}
		
	}
	
	public void onSelectedFromEditSequence(String id) {
		for (InternalSignatureHolder ish : _internalSignatureHolders) {
			InternalSignature signature = ish.getInternalSignature();
			if (ish.isNew()) {
				format.getInternalSignature().add(signature);
			} else if (ish.isDeleted()) {
				format.getInternalSignature().remove(signature);
			}
		}
		_internalSignatureHolders.clear();
		List<InternalSignature> signatures = format.getInternalSignature();
		for (InternalSignature signature : signatures) {
			if (signature.getSignatureID().equals(id)) {
				theInternalSignature = signature;
				sequenceTrigger = true;
			}
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
				FileFormat rfl = formatDAO.findFormatByName(relatedFormat.getRelatedFormatName());
				if (rfl!=null) {
					relatedFormat.setRelatedFormatID(rfl.getFormatID());
					relatedFormat.setRelatedFormatVersion(rfl.getFormatVersion());
				}
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
		for (ExternalSignatureHolder esh : _externalSignatureHolders) {
			ExternalSignature eSignature = esh.getExternalSignature();
			if (esh.isNew()) {
				eSignature.setSignatureType(SignatureTypes.File_extension);
				format.getExternalSignature().add(eSignature);
			} else if (esh.isDeleted()) {
				format.getExternalSignature().remove(eSignature);
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
        if (patternTrigger) {
    		if (theFidoSignature!=null) {
    			editFidoPattern.initialize(format, theFidoSignature);
    			return editFidoPattern;
    		}
    		return this;        
        } else if (sequenceTrigger) {
    		if (theInternalSignature!=null) {
    			editByteSequence.initialize(format, theInternalSignature);
    			return editByteSequence;
    		}
    		return this;
        } else {
        	return viewFormat.initialize(format.getFormatID());
        }
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
						+ "\" which has no counterpart in this collection: " + _relatedFormatHolders);
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

    ExternalSignatureHolder onAddRowFromExternalSignatures() {
    	ExternalSignature newEs = new ExternalSignature();
    	ExternalSignatureHolder newEsHolder = new ExternalSignatureHolder(newEs, true, 0 - System.nanoTime());
    	_externalSignatureHolders.add(newEsHolder);
		return newEsHolder;
	}

	void onRemoveRowFromExternalSignatures(ExternalSignatureHolder externalSignature) {
		if (externalSignature.isNew()) {
			_externalSignatureHolders.remove(externalSignature);;
		}
		else {
			externalSignature.setDeleted(true);
		}
	}
	
	public ValueEncoder<ExternalSignatureHolder> getExternalSignatureEncoder() {
		return new ValueEncoder<ExternalSignatureHolder>() {

			public String toClient(ExternalSignatureHolder value) {
				Long key = value.getKey();
				return key.toString();
			}

			public ExternalSignatureHolder toValue(String keyAsString) {
				Long key = new Long(keyAsString);
				for (ExternalSignatureHolder holder : _externalSignatureHolders) {
					if (holder.getKey().equals(key)) {
						return holder;
					}
				}
				throw new IllegalArgumentException("Received key \"" + key
						+ "\" which has no counterpart in this collection: " + _externalSignatureHolders);
			}
		};
	}
	
	public class ExternalSignatureHolder {
		private ExternalSignature _es;
		private Long _key;
		private boolean _new;
		private boolean _deleted;
		ExternalSignatureHolder(ExternalSignature es, boolean isNew, Long key) {
			_es = es;
			_key = key;	    			
			_new = isNew;
		}
		public ExternalSignature getExternalSignature() {
			return _es;
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
    	newFs.setFidoPrioritize(true);
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
			String lowerFormatName = formatName.toLowerCase();
			if (lowerFormatName.startsWith(partial)) {
				matches.add(formatName);
			}
		}

		return matches;
	}

}
