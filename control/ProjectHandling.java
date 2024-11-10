package control;

import java.io.File;

import gui.PackageCalculator;
import javafx.stage.DirectoryChooser;

/**
 * The ProjectHandling class provides methods to handle project operations such as opening projects
 * and creating new files. It interacts with the user interface and file system
 */
public class ProjectHandling {

	/**
	 * Opens an existing project by specifying the root path
	 * @param rootPath the root path of the project to be opened
	 */
	// TODO: Check if this can be deleted. It is not used in the code and there is no explorer area anymore.
	static public void openProject(String rootPath) {
		// update window title
		PackageCalculator.getInstance().getPrimaryStage().setTitle(PackageCalculator.APPNAME + " – " + rootPath);

		// remember open project
		PackageCalculator.getInstance().rootPath = rootPath;
	}

	/**
	 * Opens an existing project by letting the user select a directory
	 */
	static public void openProject() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		File projectDirectory;
		projectDirectory = directoryChooser.showDialog(PackageCalculator.getInstance().getPrimaryStage());
		if (projectDirectory != null) {
			projectDirectory.getAbsolutePath();
		}
		//openProject(projectDirectory.getAbsolutePath());
	}

	/**
	 * Creates a new file in the current project
	 * TODO: Implement this method
	 */
	static public void newFile() {
		// ... 
	}
}
