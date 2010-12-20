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
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import metachess.dialog.ErrorBox;
import metachess.exception.ExtractException;
import metachess.exception.FilesExtractException;
import metachess.exception.JarExtractException;

/** Data extractor, installing resources on the Operating System
 * @author Jan (7DD)
 * @version 0.8.8
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
	
	    	File file1 = new File(Resource.RESOURCES.getPath(true)+"version");
	    	File file2 = new File(Resource.RESOURCES.getPath()+"version");
	    	
	    	if( !file2.exists() || !getContent(file1).equals(getContent(file2)) ) {
		    	// If resources inexistent or out of date, extract resources.
	    		extract();
	    	}
    	}
	    catch (ExtractException e) {
	    	new ErrorBox(e);
	    }
		
	}
	
	private static String getContent(File file) throws ExtractException {
		Scanner scanner;
		StringBuilder text = new StringBuilder();
		String newLine = System.getProperty("line.separator");
	    try {
	    	scanner = new Scanner(new FileInputStream(file));
		    while (scanner.hasNextLine()){
		        text.append(scanner.nextLine() + newLine);
		    }
		    scanner.close();
	    }
	    catch (FileNotFoundException e) { /* do nothing, the content will be void */ }
		return text.toString();
	}
	
	/**
	 * Generic data extraction (can be run either from .jar or .class)
	 */
	public static void extract() throws ExtractException {
		try {
			String className = instance.getClass().getName().replace('.', '/');
			String classJar = instance.getClass().getResource("/" + className + ".class").toString();
			String home = getInstance().getClass().getProtectionDomain().getCodeSource().getLocation().toString();
			// If we run from a Jar file, extract from the Jar file
   			if (classJar.startsWith("jar:")) {
				extractJar(new JarFile(home.substring(5)));
			}
			// If we run from a class file, extract from physical folder
			else {
				extractFiles(new File(Resource.RESOURCES.getPath(true)), Resource.RESOURCES.getFile());
			}
		
		
		} catch (IOException e) { throw new ExtractException(e.getMessage()); }
	}
	
	/**
	 * Data extraction from a Jar file
	 * @param the Jar file
	 * @throws JarExtractException
	 */
	private static void extractJar(JarFile jar) throws JarExtractException {
		String resourcePath = Resource.RESOURCES.getPath(true);
		try {
			for (Enumeration<JarEntry> list = jar.entries(); list.hasMoreElements();) {
				ZipEntry source = list.nextElement();
				if (source.toString().startsWith(resourcePath)) {
					InputStream inputStream = new BufferedInputStream(jar.getInputStream(source));
					String destination = Resource.RESOURCES.getPath()
							+ source.toString().substring(
									resourcePath.length());
					destination = destination.replace('/', File.separatorChar);
					File parentfolder = new File(destination.substring(0,
							destination.lastIndexOf(File.separator)));
					if (!parentfolder.exists()) parentfolder.mkdirs();

					if(! new File(destination).isDirectory()) {
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
