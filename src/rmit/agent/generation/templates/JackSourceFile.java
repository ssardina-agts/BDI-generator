package rmit.agent.generation.templates;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;


public abstract class JackSourceFile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected final ClassName className;
	protected final JackSourceFileType sourceFileType;
	
	public JackSourceFile(ClassName className, JackSourceFileType type) {
		this.className = className;
		this.sourceFileType = type;
	}
	
	public ClassName getClassName() {
		return className;
	}
	
	public JackSourceFileType getSourceFileType() {
		return sourceFileType;
	}
	
	public Path getSourceFile(Path rootdir) {
		return Paths.get(rootdir.toString(), className.getPackageName().replaceAll("\\.", "\\\\"), className.getClassName() + "." + sourceFileType.getFileExtension());
	}
	
	public abstract ImportSet getImports();
	
}
