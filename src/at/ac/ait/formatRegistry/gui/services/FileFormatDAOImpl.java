package at.ac.ait.formatRegistry.gui.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        if (registryResources!=null) {
        	formatXMLpath = registryResources.getString("formatxmlpath");       	
        	outputXMLpath = registryResources.getString("outputxmlpath");   	
        } else {
			formatXMLpath = "C:\\development\\eclipse-workspace\\registry\\WebContent\\WEB-INF\\dat\\xml";
			outputXMLpath = "C:\\development\\eclipse-workspace\\registry\\WebContent\\WEB-INF\\dat\\test";
			System.out.println("Relying on default paths.");        	
		}
		pronomXMLDir = new File(formatXMLpath);
		if (!pronomXMLDir.exists()) {
			System.out.println("Improper property configuration, directory does not exist: " + formatXMLpath);
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
			marshaller
					.marshal(
							report,
							new FileWriter(outputXMLpath + "/" + "puid." + outputID + ".xml"));
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

	public void exportToFido() {
		Formats fidoFormats = new Formats();
		fidoFormats.setVersion("0.1");
		JAXBContext context;
		for (FileFormat fileFormat : formatHash.values()) {
			if (fileFormat.getFidoSignature().size()>0) {
				Format fidoFormat = new Format();
				fidoFormat.setName(fileFormat.getFormatName());
				fidoFormat.setPuid(fileFormat.getPronomID());
				fidoFormat.setPronomId(fileFormat.getFormatID());
				for (FileFormatIdentifier ffi : fileFormat.getFileFormatIdentifier()) {
					if (ffi.getIdentifierType()==IdentifierTypes.MIME) {
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
					if (rf.getRelationshipType()==RelationshipTypes.Has_priority_over) {
						String id = rf.getRelatedFormatID();
						FileFormat relatedFormat = this.find(id);
						if (relatedFormat!=null) {
							HasPriorityOver hasPriorityOver = new HasPriorityOver();
							hasPriorityOver.setValue(relatedFormat.getPronomID());
							fidoFormat.getHasPriorityOver().add(hasPriorityOver);
						}
					}
				}
				for (FidoSignature fs : fileFormat.getFidoSignature()) {
					Signature signature = new Signature();
					signature.setName(fs.getFidoSignatureName());
					signature.setNote(fs.getFidoSignatureNote());
					for (PRONOMReport.ReportFormatDetail.FileFormat.FidoSignature.Pattern p : fs.getPattern()) {
						Formats.Format.Signature.Pattern fPattern = new Formats.Format.Signature.Pattern();
						fPattern.setPosition(p.getPosition().toString());
						fPattern.setRegex(p.getRegex());
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
			marshaller
					.marshal(
							fidoFormats,
							new FileWriter(outputXMLpath + "/" + "formats.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
