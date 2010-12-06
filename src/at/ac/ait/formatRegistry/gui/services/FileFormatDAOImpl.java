package at.ac.ait.formatRegistry.gui.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import uk.bl.dpt.fido.Formats;
import uk.bl.dpt.fido.Formats.Format;
import uk.bl.dpt.fido.Formats.Format.Extension;
import uk.bl.dpt.fido.Formats.Format.HasPriorityOver;
import uk.bl.dpt.fido.Formats.Format.Mime;
import uk.bl.dpt.fido.Formats.Format.Signature;
import uk.gov.nationalarchives.pronom.PRONOMReport.FidoPositions;
import uk.gov.nationalarchives.pronom.PRONOMReport.IdentifierTypes;
import uk.gov.nationalarchives.pronom.PRONOMReport.RelationshipTypes;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.ExternalSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.FidoSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.FidoSignature.Pattern;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.FileFormatIdentifier;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.InternalSignature;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.RelatedFormat;
import uk.gov.nationalarchives.pronom.PRONOMReport.ReportFormatDetail.FileFormat.InternalSignature.ByteSequence;

import javax.xml.bind.*;

import uk.gov.nationalarchives.pronom.*;

public class FileFormatDAOImpl implements FileFormatDAO {
	private boolean dataLoaded = false;
	private int highestFormatID;
	private int highestSignatureID;
	private int highestFidoSignatureID;
	private Hashtable<String, FileFormat> formatHash;
	private Hashtable<String, PRONOMReport> reportHash;
	private Set<String> _formatNames = new TreeSet<String>();
	private File pronomXMLDir;
	private String outputXMLpath;

	public FileFormatDAOImpl() {
		ResourceBundle registryResources = ResourceBundle.getBundle("registry");
		String formatXMLpath = "";
		if (registryResources != null) {
			formatXMLpath = registryResources.getString("formatxmlpath");
			outputXMLpath = registryResources.getString("outputxmlpath");
		} else {
			formatXMLpath = "C:\\development\\eclipse-workspace\\registry\\WebContent\\WEB-INF\\dat\\xml";
			outputXMLpath = "C:\\development\\eclipse-workspace\\registry\\WebContent\\WEB-INF\\dat\\test";
			System.out.println("Relying on default paths.");
		}
		pronomXMLDir = new File(formatXMLpath);
		if (!pronomXMLDir.exists()) {
			System.out
					.println("Improper property configuration, directory does not exist: "
							+ formatXMLpath);
		} else {
			loadFormatData();
		}
	}

	public void loadFormatData() {
		JAXBContext context;
		Unmarshaller unmarshaller;
		if (dataLoaded)
			return;
		formatHash = new Hashtable<String, FileFormat>();
		reportHash = new Hashtable<String, PRONOMReport>();

		try {
			context = JAXBContext.newInstance("at.ac.ait.formatRegistry");
			unmarshaller = context.createUnmarshaller();
			File[] pronomXMLs = pronomXMLDir.listFiles();
			for (int i = 0; i < pronomXMLs.length; i++) {
				// for (int i = 0; i < 10; i++) {
				File theFile = pronomXMLs[i];
				// System.out.println("\n\nFile found: " + theFile +
				// "\nNumber: " + i);
				PRONOMReport report = (PRONOMReport) unmarshaller
						.unmarshal(new FileReader(theFile));
				List<PRONOMReport.ReportFormatDetail> listOfDetails = report
						.getReportFormatDetail();
				for (PRONOMReport.ReportFormatDetail item : listOfDetails) {
					List<PRONOMReport.ReportFormatDetail.FileFormat> listOfFormats = item
							.getFileFormat();
					for (PRONOMReport.ReportFormatDetail.FileFormat format : listOfFormats) {
						String formID = format.getFormatID();
						int intID = new Integer(formID).intValue();
						if (intID > highestFormatID)
							highestFormatID = intID;
						formatHash.put(formID, format);
						reportHash.put(formID, report);
						_formatNames.add(format.getFormatName());
						List<InternalSignature> internalSignatures = format
								.getInternalSignature();
						for (InternalSignature signature : internalSignatures) {
							int sigID = new Integer(signature.getSignatureID())
									.intValue();
							if (sigID > highestSignatureID)
								highestSignatureID = sigID;
						}
						List<FidoSignature> fidoSignatures = format
								.getFidoSignature();
						for (FidoSignature fSignature : fidoSignatures) {
							int fSigID = new Integer(
									fSignature.getFidoSignatureID()).intValue();
							if (fSigID > highestFidoSignatureID)
								highestFidoSignatureID = fSigID;
						}
					}
				}
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataLoaded = true;
		//importFromFido(new File ("C:/development/eclipse-workspace/registry/WebContent/WEB-INF/dat/test/formats.xml"));
	}

	public String getNewFormatID() {
		highestFormatID = highestFormatID + 1;
		return Integer.toString(highestFormatID);
	}

	@Override
	public void delete(FileFormat format) {
		// TODO Auto-generated method stub

	}

	@Override
	public FileFormat find(String id) {
		// TODO Auto-generated method stub
		return formatHash.get(id);
	}

	@Override
	public List<FileFormat> findAllFormats() {
		// TODO Auto-generated method stub
		return (List<FileFormat>) Collections
				.synchronizedList(new ArrayList<FileFormat>(formatHash.values()));
	}

	@Override
	public FileFormat findFormatByName(String name) {
		FileFormat retFormat = null;
		for (Enumeration<FileFormat> e = formatHash.elements(); e
				.hasMoreElements();) {
			FileFormat theFormat = e.nextElement();
			String formName = theFormat.getFormatName();
			if (formName.equals(name)) {
				retFormat = theFormat;
				break;
			}
		}
		return retFormat;
	}

	@Override
	public void save(FileFormat format) {
		String formID = format.getFormatID();
		String outputID = format.getPronomID();
		outputID = outputID.replaceAll("/", ".");
		PRONOMReport report = reportHash.get(formID);
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(PRONOMReport.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(report, new FileWriter(outputXMLpath + "/"
					+ "puid." + outputID + ".xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<String> getFormatNamesSet() {
		return Collections.unmodifiableSet(_formatNames);
	}

	public List<FileFormat> findFormatsByExtension(String fragment) {
		ArrayList<FileFormat> results = new ArrayList<FileFormat>();
		Iterator<FileFormat> it = formatHash.values().iterator();
		while (it.hasNext()) {
			FileFormat format = it.next();
			String extensions = format.getExtensionList();
			if (extensions.contains(fragment))
				results.add(format);
		}
		return results;
	}

	public List<FileFormat> findFormatsByName(String fragment) {
		ArrayList<FileFormat> results = new ArrayList<FileFormat>();
		Iterator<FileFormat> it = formatHash.values().iterator();
		while (it.hasNext()) {
			FileFormat format = it.next();
			String name = format.getFormatName();
			if (name.contains(fragment))
				results.add(format);
		}
		return results;
	}

	public List<FileFormat> findFormatsByPronomId(String fragment) {
		ArrayList<FileFormat> results = new ArrayList<FileFormat>();
		Iterator<FileFormat> it = formatHash.values().iterator();
		while (it.hasNext()) {
			FileFormat format = it.next();
			String id = format.getPronomID();
			if (id.contains(fragment))
				results.add(format);
		}
		return results;
	}

	public String getNewInternalSignatureID() {
		highestSignatureID = highestSignatureID + 1;
		return Integer.toString(highestSignatureID);
	}

	public String getNewFidoSignatureID() {
		highestFidoSignatureID = highestFidoSignatureID + 1;
		return Integer.toString(highestFidoSignatureID);
	}

	public String exportToFido() {
		StringWriter response = new StringWriter();
		Formats fidoFormats = new Formats();
		fidoFormats.setVersion("0.1");
		JAXBContext context;
		for (FileFormat fileFormat : formatHash.values()) {
			if (fileFormat.getFidoSignature().size() > 0) {
				Format fidoFormat = new Format();
				fidoFormat.setName(fileFormat.getFormatName());
				fidoFormat.setPuid(fileFormat.getPronomID());
				fidoFormat.setPronomId(fileFormat.getFormatID());
				for (FileFormatIdentifier ffi : fileFormat
						.getFileFormatIdentifier()) {
					if (ffi.getIdentifierType() == IdentifierTypes.MIME) {
						Mime mime = new Mime();
						mime.setValue(ffi.getIdentifier());
						fidoFormat.getMime().add(mime);
					}
				}
				for (ExternalSignature es : fileFormat.getExternalSignature()) {
					Extension extension = new Extension();
					extension.setValue(es.getSignature());
					fidoFormat.getExtension().add(extension);
				}
				for (RelatedFormat rf : fileFormat.getRelatedFormat()) {
					if (rf.getRelationshipType() == RelationshipTypes.Has_priority_over) {
						String id = rf.getRelatedFormatID();
						FileFormat relatedFormat = this.find(id);
						if (relatedFormat != null) {
							HasPriorityOver hasPriorityOver = new HasPriorityOver();
							hasPriorityOver.setValue(relatedFormat
									.getPronomID());
							fidoFormat.getHasPriorityOver()
									.add(hasPriorityOver);
						}
					}
				}
				for (FidoSignature fs : fileFormat.getFidoSignature()) {
					Signature signature = new Signature();
					String sName = fs.getFidoSignatureName();
					signature.setName(sName);
					signature.setNote(fs.getFidoSignatureNote());
					InternalSignature correspondingInternalSignature = null;
					for (InternalSignature is : fileFormat.getInternalSignature()) {
						if (is.getSignatureName().equals(sName)) {
							correspondingInternalSignature = is;
							break;
						}
					}
					if (correspondingInternalSignature==null) correspondingInternalSignature = new InternalSignature();
					for (PRONOMReport.ReportFormatDetail.FileFormat.FidoSignature.Pattern p : fs
							.getPattern()) {
						Formats.Format.Signature.Pattern fPattern = new Formats.Format.Signature.Pattern();
						fPattern.setPosition(p.getPosition().toString());
						fPattern.setRegex(p.getRegex());
						String fidoPatternId = p.getPatternID();
						for (ByteSequence bs : correspondingInternalSignature.getByteSequence()) {
							if (bs.getByteSequenceID().equals(fidoPatternId)) {
								fPattern.setPronomPattern(bs.getByteSequenceValue());
								break;
							}
						}
						signature.getPattern().add(fPattern);
					}
					fidoFormat.getSignature().add(signature);
				}
				fidoFormats.getFormat().add(fidoFormat);
			}
		}
		try {
			context = JAXBContext.newInstance(Formats.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			/*
			marshaller.marshal(fidoFormats, new FileWriter(outputXMLpath + "/"
					+ "formats.xml"));
			*/
			marshaller.marshal(fidoFormats, response);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		/*
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		*/
		}
		return response.toString();

	}

	public void importFromFido(File fidoFile) {
		JAXBContext context;
		Unmarshaller unmarshaller;
		try {
			context = JAXBContext.newInstance("uk.bl.dpt.fido");
			unmarshaller = context.createUnmarshaller();
			Formats fidoFormats = (Formats) unmarshaller
					.unmarshal(new FileReader(fidoFile));

			for (Format fidoFormat : fidoFormats.getFormat()) {
				String pId = fidoFormat.getPronomId();
				FileFormat format = this.find(pId);
				if (format!=null) {
					List<InternalSignature> iss = format.getInternalSignature();
					for (Signature fs : fidoFormat.getSignature()) {
						String sName = fs.getName();
						InternalSignature correspondingInternalSignature = null;
						for (InternalSignature is : iss) {
							if (is.getSignatureName().equals(sName)) {
								correspondingInternalSignature = is;
								break;
							}
						}
						if (correspondingInternalSignature==null) correspondingInternalSignature = new InternalSignature();
						FidoSignature signature = new FidoSignature();
						highestFidoSignatureID++;
						signature.setFidoSignatureID(new Integer(highestFidoSignatureID).toString());
						signature.setFidoSignatureName(sName);
						signature.setFidoSignatureNote(fs.getNote());
						for ( Formats.Format.Signature.Pattern p : fs.getPattern()) {
							PRONOMReport.ReportFormatDetail.FileFormat.FidoSignature.Pattern fPattern = new PRONOMReport.ReportFormatDetail.FileFormat.FidoSignature.Pattern();
							String pos = p.getPosition();
							if (pos.equalsIgnoreCase("BOF")) {
								fPattern.setPosition(FidoPositions.BOF);
							} else if (pos.equalsIgnoreCase("EOF")) {
								fPattern.setPosition(FidoPositions.EOF);
							} else if (pos.equalsIgnoreCase("VAR")) {
								fPattern.setPosition(FidoPositions.VAR);
							}
							String droidPattern = p.getPronomPattern();
							String fidoPatternId = "";
							for (ByteSequence bs : correspondingInternalSignature.getByteSequence()) {
								if (bs.getByteSequenceValue().equals(droidPattern)) {
									fidoPatternId = bs.getByteSequenceID();
								}
							}
							fPattern.setPatternID(fidoPatternId);
							fPattern.setRegex(p.getRegex());
							signature.getPattern().add(fPattern);
						}
						format.getFidoSignature().add(signature);
					}
					this.save(format);
				} else {
					System.out.println("Warning: could not find record with internal ID: " + pId);
				}
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
