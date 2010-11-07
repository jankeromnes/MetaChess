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

/** Data folder extraction, localizing MetaChess resources on the Operating System (cross-platform)
 * @author Jan (7DD)
 * @version 0.8.0 beta
 */
public class DataExtractor {
	
	/** The unique instance of the DataExtractor. */
	private static DataExtractor instance = new DataExtractor();
	
	/** Get the unique instance of the DataExtractor.
	 * @return the instance
	 */
	public static DataExtractor getInstance() {
		return instance;
	}
	
	/** Check if the local data folder exists and is up to date. If not, the data is extracted.
	 */
	public static void checkDataVersion() {
    	
    	// If resource installation folder doesn't exist, create it. If this fails, we have encountered an error.
    	File dataFolder = Resource.RESOURCES.getFile();
    	if(!dataFolder.exists() && !dataFolder.mkdirs()) {
    		System.err.println("ERROR Resource folder could not be created!");
    	}

    	// If resources inexistent or out of date, copy from Jar.
    	File dataVersionFile = new File(Resource.RESOURCES.getPath()+"version");
    	if(!dataVersionFile.exists() || ( dataVersionFile.compareTo(new File(Resource.RESOURCES.getPath(true)+"version"))) == 0 ) {
    		extract();
    	}
		
	}
	
	/**
	 * Generic extraction method (from Jar or pysical folder)
	 */
	public static void extract() {
		try {
			String home = getInstance().getClass().getProtectionDomain()
					.getCodeSource().getLocation().toString().substring(6);
			JarFile jar = null;
			try {
				jar = new JarFile(home);
				extractJar(jar);
			} catch (FileNotFoundException e) {
			    copyFiles(new File(home+Resource.RESOURCES.getPath(true)), Resource.RESOURCES.getFile());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void extractJar(JarFile jar) throws IOException {
	      for (Enumeration<JarEntry> list = jar.entries(); list.hasMoreElements(); ) {
	    	  ZipEntry source = list.nextElement();
	    	  if(source.toString().startsWith(Resource.RESOURCES.getPath(true))) {
		    	  InputStream in = new BufferedInputStream(jar.getInputStream(source));
		    	  String destination = Resource.RESOURCES.getPath()
		    			  +source.toString().substring(Resource.RESOURCES.getPath(true).length());
		    	  destination = destination.replace('/', File.separatorChar);
		    	  File parentfolder = new File(destination.substring(0,destination.lastIndexOf(File.separator)));
		    	  if (!parentfolder.exists()) parentfolder.mkdirs();
		    	  OutputStream out = new BufferedOutputStream(new FileOutputStream(destination));
			      byte[] buffer = new byte[2048];
			      for (;;)  {
			        int nBytes = in.read(buffer);
			        if (nBytes <= 0) break;
			        out.write(buffer, 0, nBytes);
			      }
			      out.flush();
			      out.close();
			      in.close();
	    	  }
	      }
	      jar.close();
	}
	
	/**
	 * This function will copy files or directories from one location to another.
	 * note that the source and the destination must be mutually exclusive. This 
	 * function can not be used to copy a directory to a sub directory of itself.
	 * The function will also have problems if the destination files already exist.
	 * @param source A File object that represents the source for the copy
	 * @param destination A File object that represents the destination for the copy.
	 * @throws IOException if unable to copy.
	 */
	private static void copyFiles(File source, File destination) throws IOException {
		//Check to ensure that the source is valid...
		if (!source.exists()) {
			throw new IOException("copyFiles: Can not find source: " + source.getAbsolutePath()+".");
		} else if (!source.canRead()) { //check to ensure we have rights to the source...
			throw new IOException("copyFiles: No right to source: " + source.getAbsolutePath()+".");
		}
		//is this a directory copy?
		if (source.isDirectory()) 	{
			if (!destination.exists()) { //does the destination already exist?
				//if not we need to make it exist if possible (note this is mkdirs not mkdir)
				if (!destination.mkdirs()) {
					throw new IOException("copyFiles: Could not create direcotry: " + destination.getAbsolutePath() + ".");
				}
			}
			//get a listing of files...
			String list[] = source.list();
			//copy all the files in the list.
			for (int i = 0; i < list.length; i++)
			{
				File destination1 = new File(destination, list[i]);
				File source1 = new File(source, list[i]);
				copyFiles(source1 , destination1);
			}
		} else { 
			//This was not a directory, so lets just copy the file
			FileInputStream fin = null;
			FileOutputStream fout = null;
			byte[] buffer = new byte[4096]; //Buffer 4K at a time (you can change this).
			int bytesRead;
			try {
				//open the files for input and output
				fin =  new FileInputStream(source);
				fout = new FileOutputStream (destination);
				//while bytesRead indicates a successful read, lets write...
				while ((bytesRead = fin.read(buffer)) >= 0) {
					fout.write(buffer,0,bytesRead);
				}
			} catch (IOException e) { //Error copying file... 
				IOException wrapper = new IOException("copyFiles: Unable to copy file: " + 
							source.getAbsolutePath() + "to" + destination.getAbsolutePath()+".");
				wrapper.initCause(e);
				wrapper.setStackTrace(e.getStackTrace());
				throw wrapper;
			} finally { //Ensure that the files are closed (if they were open).
				if (fin != null) { fin.close(); }
				if (fout != null) { fout.close(); }
			}
		}
	}
}
