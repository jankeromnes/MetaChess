package metachess.library;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import metachess.dialog.ErrorDialog;
import metachess.exceptions.ExtractException;
import metachess.exceptions.FilesExtractException;
import metachess.exceptions.JarExtractException;
import metachess.exceptions.SourceExtractException;

/** 
 * Data extractor, installing MetaChess' resources on the Operating System
 * @author Jan (7DD)
 * @version 0.8.5
 */
public class DataExtractor {
	
	/** 
	 * The unique instance of the DataExtractor.
	 */
	private static DataExtractor instance = new DataExtractor();
	
	/** 
	 * Get the unique instance of the DataExtractor.
	 * @return the instance
	 */
	public static DataExtractor getInstance() {
		return instance;
	}
	
	/** 
	 * Check if the installation data folder exists and is up to date. If not, extract the data.
	 */
	public static void checkDataVersion() {
    	try {
	    	// If resource installation folder doesn't exist, create it. If this fails, we have encountered an error.
	    	File dataFolder = Resource.RESOURCES.getFile();
	    	if(!dataFolder.exists() && !dataFolder.mkdirs()) {
	    		throw new ExtractException("ERROR Resource folder could not be created!");
	    	}
	
	    	// If resources inexistent or out of date, copy from Jar.
	    	File dataVersionFile = new File(Resource.RESOURCES.getPath()+"version");
	    	if(!dataVersionFile.exists() || ( dataVersionFile.compareTo(new File(Resource.RESOURCES.getPath(true)+"version"))) == 0 ) {
	    		extract();
	    	}
    	}
	    catch (ExtractException e) {
	    	new ErrorDialog(e);
	    }
		
	}
	
	/**
	 * Generic data extraction (can be run either from .jar or .class)
	 */
	public static void extract() throws ExtractException {
			
			// Get the home folder of executed files
			String home = getInstance().getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
			JarFile jar = null;
			try {
				jar = new JarFile(home);
				// If we run from a Jar file, extract from the Jar file
				extractJar(jar);
			} catch (FileNotFoundException e) {
				// Else, extract from physical folder
				extractFiles(new File(home+Resource.RESOURCES.getPath(true)), Resource.RESOURCES.getFile());
			} catch (IOException e) { throw new JarExtractException(e.getMessage()); }
	}
	
	/**
	 * Data extraction from a Jar file
	 * @param the Jar file
	 * @throws JarExtractException
	 */
	private static void extractJar(JarFile jar) throws JarExtractException {
		System.out.println("JAR Extraction...");
		try {
			for (Enumeration<JarEntry> list = jar.entries(); list.hasMoreElements();) {
				ZipEntry source = list.nextElement();
				System.out.println(source.toString()+" must start with "+Resource.RESOURCES.getPath(true));
				if (source.toString().startsWith(Resource.RESOURCES.getPath(true))) {
					InputStream inputStream = new BufferedInputStream(jar.getInputStream(source));
					String destination = Resource.RESOURCES.getPath()
							+ source.toString().substring(
									Resource.RESOURCES.getPath(true).length());
					destination = destination.replace('/', File.separatorChar);
					System.out.println("JAR Extracting "+source.toString()+" to "+destination);
					File parentfolder = new File(destination.substring(0,
							destination.lastIndexOf(File.separator)));
					if (!parentfolder.exists()) parentfolder.mkdirs();
					OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destination));
					byte[] buffer = new byte[2048];
					for (;;) {
						int nBytes;
						nBytes = inputStream.read(buffer);
						if (nBytes <= 0)
							break;
						outputStream.write(buffer, 0, nBytes);
					}
					outputStream.flush();
					outputStream.close();
					inputStream.close();
				}
			}
			jar.close();
		} catch (IOException e) {
			throw new JarExtractException(e.getMessage());
		}
	}
	
	/**
	 * Data extraction from a source folder to a destination folder
	 * @param source the source file
	 * @param destination the destination file
	 * @throws JarExtractException if something goes wrong
	 */
	private static void extractFiles(File source, File destination) throws FilesExtractException {
		try {
			if (!source.exists() || !source.canRead()) {
				throw new FilesExtractException("from "+source.getAbsolutePath());
			}
			
			// Handling a Directory
			if (source.isDirectory()) 	{
				if (!destination.exists() && !destination.mkdirs()) {
					throw new FilesExtractException("to "+destination.getAbsolutePath() + ".");
				}
				String list[] = source.list();
				for (int i = 0; i < list.length; i++) {
					extractFiles(new File(source, list[i]) , new File(destination, list[i]));
				}
			}
			
			// Handling a File
			else { 
				FileInputStream fileInputStream = null;
				FileOutputStream fileOutputStream = null;
				byte[] buffer = new byte[1024];
				int readCounter;
				fileInputStream =  new FileInputStream(source);
				fileOutputStream = new FileOutputStream (destination);
				while ((readCounter = fileInputStream.read(buffer)) >= 0) {
					fileOutputStream.write(buffer,0,readCounter);
				}
				if (fileInputStream != null) { fileInputStream.close(); }
				if (fileOutputStream != null) { fileOutputStream.close(); } 
	
			}
		} catch (IOException e) {
			throw new FilesExtractException(e.getMessage());
		}
	}
}
