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
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.FidoSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.FidoSignature.Pattern;
import at.ac.ait.formatRegistry.gui.services.FileFormatDAO;

public class EditFidoPattern {
    @Inject
    private FileFormatDAO formatDAO;
    
    @InjectPage
    private EditFormat editFormat;

    @Property
	@Persist
	private FileFormat _format;
	
	@Property
	@Persist
	private FidoSignature _signature;
	
	@Property
	@Persist
	private List<PatternHolder> _patternHolders;

	@Property
	private PatternHolder _patternHolder;
	
	Object initialize(FileFormat format, FidoSignature signature) {
		_patternHolders = new ArrayList<PatternHolder>();
		Iterator<Pattern> it1 = signature.getPattern().iterator();
		while (it1.hasNext()) {
			_patternHolders.add(new PatternHolder(it1.next(), false, 0 - System.nanoTime()));
		}
		this._format = format;
		this._signature = signature;
		return this;
	}


    public Object onSuccess() {
		for (PatternHolder ph : _patternHolders) {
			Pattern theSequence = ph.getPattern();
			if (ph.isNew()) {
				_signature.getPattern().add(theSequence);
			} else if (ph.isDeleted()) {
				_signature.getPattern().remove(theSequence);
			}
		}   	
    	formatDAO.save(_format);
    	return editFormat.initialize(_format.getFormatID());
    }
    
    PatternHolder onAddRowFromPatterns() {
		// Create a skeleton Person and add it to the displayed list with a unique key
    	Pattern newP = new Pattern();
    	PatternHolder newBsHolder = new PatternHolder(newP, true, 0 - System.nanoTime());
    	_patternHolders.add(newBsHolder);
		return newBsHolder;
	}

	void onRemoveRowFromPatterns(PatternHolder pattern) {
		if (pattern.isNew()) {
			_patternHolders.remove(pattern);;
		}
		else {
			pattern.setDeleted(true);
		}
	}
	
	public ValueEncoder<PatternHolder> getPatternEncoder() {
		return new ValueEncoder<PatternHolder>() {

			public String toClient(PatternHolder value) {
				Long key = value.getKey();
				return key.toString();
			}

			public PatternHolder toValue(String keyAsString) {
				Long key = new Long(keyAsString);
				for (PatternHolder holder : _patternHolders) {
					if (holder.getKey().equals(key)) {
						return holder;
					}
				}
				throw new IllegalArgumentException("Received key \"" + key
						+ "\" which has no counterpart in this collection: " + _patternHolders);
			}
		};
	}

	public class PatternHolder {
		private Pattern _p;
		private Long _key;
		private boolean _new;
		private boolean _deleted;
		PatternHolder(Pattern p, boolean isNew, Long key) {
			_p = p;
			_key = key;
			_new = isNew;
		}
		public Pattern getPattern() {
			return _p;
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

}
