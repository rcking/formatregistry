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
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.InternalSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.InternalSignature.ByteSequence;
import at.ac.ait.formatRegistry.gui.services.FileFormatDAO;

public class EditByteSequence {
    @Inject
    private FileFormatDAO formatDAO;
    
    @InjectPage
    private EditFormat editFormat;

    @Property
	@Persist
	private FileFormat _format;
	
	@Property
	@Persist
	private InternalSignature _signature;
	
	@Property
	@Persist
	private List<ByteSequenceHolder> _byteSequenceHolders;

	@Property
	private ByteSequenceHolder _byteSequenceHolder;
	
	
	Object initialize(FileFormat format, InternalSignature signature) {
		_byteSequenceHolders = new ArrayList<ByteSequenceHolder>();
		Iterator<ByteSequence> it1 = signature.getByteSequence().iterator();
		while (it1.hasNext()) {
			_byteSequenceHolders.add(new ByteSequenceHolder(it1.next(), false, 0 - System.nanoTime()));
		}
		this._format = format;
		this._signature = signature;
		return this;
	}


    public Object onSuccess() {
		for (ByteSequenceHolder bsh : _byteSequenceHolders) {
			ByteSequence theSequence = bsh.getByteSequence();
			if (bsh.isNew()) {
				_signature.getByteSequence().add(theSequence);
			} else if (bsh.isDeleted()) {
				_signature.getByteSequence().remove(theSequence);
			}
		}   	
    	formatDAO.save(_format);
    	return editFormat.initialize(_format.getFormatID());
    }
    
    ByteSequenceHolder onAddRowFromByteSequences() {
		// Create a skeleton Person and add it to the displayed list with a unique key
    	ByteSequence newBs = new ByteSequence();
    	ByteSequenceHolder newBsHolder = new ByteSequenceHolder(newBs, true, 0 - System.nanoTime());
    	_byteSequenceHolders.add(newBsHolder);
		return newBsHolder;
	}

	void onRemoveRowFromByteSequences(ByteSequenceHolder byteSequence) {
		if (byteSequence.isNew()) {
			_byteSequenceHolders.remove(byteSequence);;
		}
		else {
			byteSequence.setDeleted(true);
		}
	}
	
	public ValueEncoder<ByteSequenceHolder> getByteSequenceEncoder() {
		return new ValueEncoder<ByteSequenceHolder>() {

			public String toClient(ByteSequenceHolder value) {
				Long key = value.getKey();
				return key.toString();
			}

			public ByteSequenceHolder toValue(String keyAsString) {
				Long key = new Long(keyAsString);
				for (ByteSequenceHolder holder : _byteSequenceHolders) {
					if (holder.getKey().equals(key)) {
						return holder;
					}
				}
				throw new IllegalArgumentException("Received key \"" + key
						+ "\" which has no counterpart in this collection: " + _byteSequenceHolders);
			}
		};
	}

	public class ByteSequenceHolder {
		private ByteSequence _bs;
		private Long _key;
		private boolean _new;
		private boolean _deleted;
		ByteSequenceHolder(ByteSequence bs, boolean isNew, Long key) {
			_bs = bs;
			_key = key;
			_new = isNew;
		}
		public ByteSequence getByteSequence() {
			return _bs;
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
