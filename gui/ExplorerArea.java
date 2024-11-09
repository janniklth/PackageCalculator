package gui;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * The ExplorerArea class provides a GUI component for displaying a file system
 * structure within a tab. It extends {@link TabPane} to allow browsing and
 * interacting with the file system. Users can perform actions like creating,
 * deleting, and copying files or folders directly from this interface.
 *
 * @see gui.ExplorerArea.TreeCellImpl
 * @see gui.ExplorerArea.FileItem
 */
public class ExplorerArea extends TabPane {

	// The tab that displays the file explorer
	private Tab explorerTab;

	// Tree view representing the file system
	private TreeView<FileItem> treeView;

	/**
	 * The FileItem class wraps a {@link File} object to be used in a TreeView,
	 * representing file paths in the application. The class provides a custom
	 * string representation for displaying the file name.
	 */
	class FileItem {
		public File file;

		/**
		 * Default constructor for FileItem
		 */
		@SuppressWarnings("unused")
		private FileItem() {
		}

		/**
		 * Constructor for FileItem that takes a File object.
		 *
		 * @param file the file to represent
		 */
		public FileItem(File file) {
			this.file = file;
		}

		/**
		 * Constructor for FileItem that takes a file path string.
		 *
		 * @param str the file path to represent
		 */
		public FileItem(String str) {
			this.file = new File(str);
		}

		/**
		 * Redefines the string representation of the node, showing the file name and hiding
		 * the root folder name.
		 *
		 * @return a formatted string representing the file name
		 */
		@Override
		public String toString() {
			if (file.getAbsolutePath().equals(PackageCalculator.getInstance().rootPath)) {
				return "..." + File.separator + file.getName();
			}
			return file.getName();
		}

		/**
		 * Generates a basic context menu for the file item.
		 *
		 * @return the context menu with a test item
		 */
		//@Override
		public ContextMenu getMenu() {
			return new ContextMenu(new MenuItem("test"));
		}
	}

	/**
	 * Builds a TreeView representing the file system starting from the given root path.
	 *
	 * @param rootPath the root directory path to display in the TreeView
	 * @return a TreeView representing the file system
	 */
	private TreeView<FileItem> buildFileSystemBrowser(String rootPath) {
		TreeItem<FileItem> root = createNode(new FileItem(new File(rootPath)));
		return new TreeView<FileItem>(root);
	}

	/**
	 * Creates a TreeItem to represent a FileItem, overriding the getChildren and isLeaf methods
	 * to optimize loading of file and folder structures dynamically.
	 *
	 * 	This method creates a TreeItem to represent the given File. It does this by overriding the TreeItem.getChildren()
	 * 	and TreeItem.isLeaf() methods anonymously, but this could be better abstracted by creating a 'FileTreeItem'
	 * 	subclass of TreeItem. However, this is left as an exercise for the reader. TODO: Create a FileTreeItem subclass
	 *
	 * @param fileItem the FileItem to represent in the tree
	 * @return a TreeItem representing the FileItem
	 */
	private TreeItem<FileItem> createNode(final FileItem fileItem) {
		return new TreeItem<FileItem>(fileItem) {
			private boolean isLeaf;
			private boolean isFirstTimeChildren = true;
			private boolean isFirstTimeLeaf = true;

			/**
			 * Overrides the getChildren method to build the children of the TreeItem dynamically
			 *
			 * @return the children of the TreeItem
			 */
			@Override
			public ObservableList<TreeItem<FileItem>> getChildren() {
				if (isFirstTimeChildren) {
					isFirstTimeChildren = false;
					super.getChildren().setAll(buildChildren(this));
				}
				return super.getChildren();
			}

			/**
			 * Overrides the isLeaf method to determine if the TreeItem is a leaf node
			 *
			 * @return true if the TreeItem is a leaf node, false otherwise
			 */
			@Override
			public boolean isLeaf() {
				if (isFirstTimeLeaf) {
					isFirstTimeLeaf = false;
					FileItem f = getValue();
					isLeaf = f.file.isFile();
				}
				return isLeaf;
			}

			/**
			 * Builds the children of the TreeItem based on the given FileItem
			 *
			 * @param treeItem the TreeItem to build children for
			 * @return the children of the TreeItem
			 */
			private ObservableList<TreeItem<FileItem>> buildChildren(TreeItem<FileItem> treeItem) {
				FileItem f = treeItem.getValue();
				if (f != null && f.file.isDirectory()) {
					File[] files = f.file.listFiles();
					if (files != null) {
						ObservableList<TreeItem<FileItem>> children = FXCollections.observableArrayList();
						for (File childFile : files) {
							if (childFile.isDirectory() || childFile.getName().toLowerCase().endsWith(".txt")) {
								children.add(createNode(new FileItem(childFile)));
							}
						}
						return children;
					}
				}
				return FXCollections.emptyObservableList();
			}
		};
	}

	/**
	 * The TreeCellImpl class defines custom behavior for cells in the TreeView.
	 * This includes displaying context menus for various file actions like create,
	 * delete, and copy. TreeCellImpl is used within {@link ExplorerArea#buildFileSystemBrowser(String)}
	 * to add specific interactions for files and folders.
	 */
	private final class TreeCellImpl extends TreeCell<FileItem> {

		/**
		 * Updates the item in the cell, setting the text and graphic based on the item's file type.
		 *
		 * @param item the FileItem to display in the cell
		 * @param empty true if the cell is empty, false otherwise
		 */
		@Override
		public void updateItem(FileItem item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
				return;
			}
			setText(getItem() == null ? "" : getItem().toString());
			setGraphic(getTreeItem().getGraphic());

			ContextMenu contextMenu = new ContextMenu();
			if (getItem().file.isDirectory()) {
				MenuItem newFileMenu = new MenuItem("New File");
				MenuItem newSubfolderMenu = new MenuItem("New Subfolder");
				contextMenu.getItems().addAll(newFileMenu, newSubfolderMenu);
			} else {
				MenuItem openMenu = new MenuItem("Open");
				MenuItem closeMenu = new MenuItem("Close");
				contextMenu.getItems().addAll(openMenu, closeMenu);
			}

			MenuItem copyMenu = new MenuItem("Copy");
			MenuItem pasteMenu = new MenuItem("Paste");
			MenuItem deleteMenu = new MenuItem("Delete");
			contextMenu.getItems().addAll(new SeparatorMenuItem(), copyMenu, pasteMenu, deleteMenu);

			setContextMenu(contextMenu);
		}
	}

	/**
	 * Loads a new file system tree based on the given project path, populating the TreeView.
	 *
	 * @param projectPath the root directory of the project to display
	 */
	@SuppressWarnings("unchecked")
	public void loadNewTree(String projectPath) {
		TreeItem<FileItem> root = new TreeItem<FileItem>();
		root.setExpanded(true);
		root.getChildren().addAll(
				new TreeItem<FileItem>(),
				new TreeItem<FileItem>(),
				new TreeItem<FileItem>()
		);
		treeView = new TreeView<FileItem>(root);
		this.treeView = buildFileSystemBrowser(projectPath);

		treeView.setCellFactory(new Callback<TreeView<FileItem>, TreeCell<FileItem>>() {
			@Override
			public TreeCell<FileItem> call(TreeView<FileItem> p) {
				return new TreeCellImpl();
			}
		});

		treeView.getRoot().setExpanded(true);
		explorerTab.setContent(treeView);
	}

	/**
	 * Constructor for ExplorerArea. Initializes the explorer with a fixed tab labeled
	 * "Explorer" that is not closable.
	 */
	public ExplorerArea() {
		explorerTab = new Tab("Explorer");
		explorerTab.setClosable(false);
		this.getTabs().add(explorerTab);
	}
}
