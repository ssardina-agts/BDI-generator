package rmit.agent.generation.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FileUtils {

	private FileUtils() { }

	public static Path findFile(String name) {
		//first check user dir
		Path path = findFileInDirectory(new File(System.getProperty("user.dir")),  name);
		if (path != null)
			return path;
		//now check classpath
		URL url = ClassLoader.getSystemResource(name);
		if (url == null)
			return null;
		else
			try {
				return resourceToPath(url.toURI());
			} catch (URISyntaxException e) {
				Utils.throwAsUnchecked(e);
				return null;
			} catch (IOException e) {
				Utils.throwAsUnchecked(e);
				return null;
			}
	}

	public static Path getFileInJar(Path jarPath, String fileName) throws IOException, URISyntaxException {
		URI jarUri = jarPath.toUri();
		return resourceToPath(URI.create("jar:" + jarUri.toString() + "!/" + fileName));
	}
	
	public static Path resourceToPath(URI uri) throws IOException, URISyntaxException {
		//URI uri = resource.toURI();

		String scheme = uri.getScheme();
		if (scheme.equals("file"))
			return Paths.get(uri);
		if (!scheme.equals("jar"))
			throw new IllegalArgumentException("Cannot convert to Path: " + uri);

		String s = uri.toString();
		int separator = s.indexOf("!/");
		String entryName = s.substring(separator + 2);
		URI fileURI = URI.create(s.substring(0, separator));

		try {
			FileSystem fs = FileSystems.newFileSystem(fileURI, Collections.<String, Object>emptyMap());
			Path p = fs.getPath(entryName);
			fs.close();
			return p;
		}
		catch (FileSystemAlreadyExistsException e) {
			FileSystem fs =  FileSystems.getFileSystem(fileURI);
			Path p = fs.getPath(entryName);
			fs.close();
			return p;
			
		}
	}


	
	public static Path findFileInDirectory(File dir, String fileName) {
		for (File child : dir.listFiles()) {
			if (child.isFile() && child.getName().equals(fileName))
				return Paths.get(child.getAbsolutePath());
			else if (child.isDirectory()) {
				Path pathToFile = findFileInDirectory(child, fileName);
				if (pathToFile != null)
					return pathToFile;
			}
		}
		return null;
	}
	
	public static void writeFile(Path path, String data) {

		try {
			if (!Files.exists(path.getParent()))
				Files.createDirectories(path.getParent());
		} catch (IOException ex) {
			Utils.throwAsUnchecked(ex);
		}

		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())) {
			writer.write(data);
		} catch (IOException ex) {
			Utils.throwAsUnchecked(ex);
		}
	}

	public static List<Path> getAllFiles(File directory) {
		List<Path> files = new ArrayList<Path>();
		recursiveGetAllFiles(directory, files);
		return files;
	}

	private static void recursiveGetAllFiles(File f, List<Path> files) {
		if (f.isFile())
			files.add(Paths.get(f.toString()));
		else if (f.isDirectory()) {
			for (File file : f.listFiles())
				recursiveGetAllFiles(file, files);
		}
	}

	public static void recursivelyDelete(File f) throws IOException {
		if (!f.exists())
			return;
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				recursivelyDelete(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}

	public static void addJarToClassPath(Path jarFile) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {

		//load
		URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<?> sysClass = URLClassLoader.class;
		Method sysMethod = sysClass.getDeclaredMethod("addURL", new Class[] {URL.class});
		sysMethod.setAccessible(true);
		sysMethod.invoke(sysLoader, new Object[]{ jarFile.toUri().toURL()});
	}
	
	public static void serialize(Object o, Path file) {
		try {
			String fileName = file.toString();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(o);
			out.close();
		}
		catch(IOException i) {
			i.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(Path file) {
		try
		{
			String fileName = file.toString();
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			T t = (T) in.readObject();
			in.close();
			return t;
		}
		catch(IOException | ClassNotFoundException | ClassCastException e) {
			e.printStackTrace();
			return null;
		} 
	}

	public static Path getRandomTempDirectory() {
		return Paths.get(System.getProperty("user.dir"), "temp_" + Math.abs(new Random().nextInt()));
	}
	
}
