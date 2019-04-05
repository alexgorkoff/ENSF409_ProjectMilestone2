package clientView;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import clientController.*;

/**
 * The view as part of a MVC architecture that will control the GUI and all of
 * the GUI functionality to the client
 * 
 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
 * @version 1.1.0
 * @since March 29, 2019
 */
public class ToolView extends JFrame {
	/**
	 * Main frame to contain individual panels
	 */
	private JFrame myFrame;
	/**
	 * Panels to bundle together components that are supposed to be together
	 */
	private JPanel southPanel, centrePanel, northPanel, westPanel, eastPanel, checkBoxPanel, searchPanel, buttonPanel,
			searchAndCheckBox;
	/**
	 * Buttons for the user to maneuver around the GUI
	 */
	private JButton idSearch, quantityCheck, buyItem, quit, listTool;
	/**
	 * Contain any text and images
	 */
	private JLabel searchIDLabel, searchNameLabel, title, searchLogo, SearchLabel, logo;

	/**
	 * Allow the user to enter input to search
	 */
	private JTextField search;
	/**
	 * Allow the user to select if they would like to search by name or ID
	 */
	private JCheckBox idSelect, nameSelect;
	/**
	 * Scrollable panel to contain the list of all tools
	 */
	private JScrollPane scrollPanel;
	/**
	 * Used to show the selected text when all tools are selected
	 */
	private DefaultListModel<String> listModel;
	/**
	 * Will contain a list of all the tools to display on the scroll panel
	 */
	private JList<String> listArea;
	/**
	 * Text Field to show the list of tools
	 */
	private JTextField selectedTextField;
	/**
	 * The table that will contain the information of items from the database
	 */
	private JTable toolTable;
	/**
	 * Scrollable panel to contain the list of all tools
	 */
	private JScrollPane toolScrollPane;
	/**
	 * The information that the column headers will contain
	 */
	final private Object[] toolTableHeaders = { "Item ID", "Item Name", "Item Quantity", "Item Price" };
	/**
	 * 2-D Array to contain the info of the tools
	 */
	private Object[][] toolTableData = {};
	/**
	 * will be used to contain the table
	 */
	private DefaultTableModel tableModel;

	/**
	 * The constructor for the main GUI. Creates the main menu and all sub menus
	 * stemming from the main menu
	 */
	public ToolView() {
		addSouthComp();
		addJTable();
		addNorthComp();
		addEastComp();
		addWestComp();
		setTitleFont();
		drawFrame();

	}

	/**
	 * Sets the font of of minor components to have a 'bodyFont'
	 */
	public void setBodyFont() {
		Font bodyFont = new Font("Serif", Font.PLAIN, 30);
	}

	/**
	 * Set the size of the TextField to be used for searching
	 */
	public void setTextFieldSize() {
		Font textFieldFont = new Font("Serif", Font.PLAIN, 60);
		search.setFont(textFieldFont);
	}

	/**
	 * Set the font of major components to be 'titleFont'
	 */
	public void setTitleFont() {
		Font titleFont = new Font("Sans Serif", Font.PLAIN, 30);
		title.setFont(titleFont);
	}

	/**
	 * Set the size of button to a size that fits all the text
	 */
	public void setButtonSize() {
		idSearch.setPreferredSize(new Dimension(350, 80));
		quantityCheck.setSize(new Dimension(400, 100));
		buyItem.setSize(new Dimension(200, 100));
		listTool.setSize(new Dimension(200, 100));
		quit.setSize(new Dimension(200, 100));
	}

	/**
	 * Creates the components necessary to constitute the searchPanel
	 */
	public void createSearchPanel() {
		searchPanel = new JPanel();
		search = new JTextField(15);
		searchLogo = new JLabel();
		idSearch = new JButton("Search");
		SearchLabel = new JLabel("Search");
		try {
			BufferedImage searchPic = ImageIO.read(new FileInputStream("resources/searchimage.png"));
			searchLogo = new JLabel(new ImageIcon(searchPic));
		} catch (IOException e) {
			e.printStackTrace();
		}
		searchPanel.add(searchLogo, BorderLayout.NORTH);
		searchPanel.add(search, BorderLayout.NORTH);
		searchPanel.add(idSearch, BorderLayout.NORTH);
	}

	/**
	 * Creates the components that are going to be used for searching for items
	 */
	public void searchAndCheckBox() {
		searchAndCheckBox = new JPanel();
		createCheckBox();
		createSearchPanel();
		searchAndCheckBox.setLayout(new BoxLayout(searchAndCheckBox, BoxLayout.Y_AXIS));
		searchAndCheckBox.add(searchPanel);
		searchAndCheckBox.add(checkBoxPanel);
	}

	/**
	 * create the checkbox components
	 */
	public void createCheckBox() {
		checkBoxPanel = new JPanel();
		idSelect = new JCheckBox();
		nameSelect = new JCheckBox();
		searchNameLabel = new JLabel(" Search by Name ");
		searchIDLabel = new JLabel("Search by ID ");
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.X_AXIS));
		checkBoxPanel.add(searchIDLabel);
		checkBoxPanel.add(idSelect);
		checkBoxPanel.add(searchNameLabel);
		checkBoxPanel.add(nameSelect);
	}

	/**
	 * Add all the necessary components to the southPanel
	 */
	public void addSouthComp() {
		southPanel = new JPanel();
		southPanel.setMaximumSize(new Dimension(650, 100));
		searchAndCheckBox();
		createButtonPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.add(searchAndCheckBox);
		southPanel.add(buttonPanel);

	}

	/**
	 * create the panel of all the buttons
	 */
	public void createButtonPanel() {
		buttonPanel = new JPanel();
		listTool = new JButton("List All Tools");
		quantityCheck = new JButton("Check Tool Quantity");
		buyItem = new JButton("Reduce Quantity");
		quit = new JButton("Quit Program");

		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(quantityCheck);
		buttonPanel.add(buyItem);
		buttonPanel.add(listTool);
		buttonPanel.add(quit);
	}

	/**
	 * creates and adds a JTable to the scrollable panel
	 */
	public void addJTable() {
		tableModel = new DefaultTableModel();

		toolTable = new JTable(tableModel);
		tableModel.addColumn("Item ID");
		tableModel.addColumn("Item Name");
		tableModel.addColumn("Item Quantity");
		tableModel.addColumn("Item Price");

		toolTable.setFillsViewportHeight(true);

		toolScrollPane = new JScrollPane(toolTable);
		toolScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		toolScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}

	/**
	 * getter for the table model
	 * 
	 * @return tableModel of type DefaultTableModel
	 */
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * getter for the toolTable
	 * 
	 * @return toolTable of type JTable
	 */
	public JTable getTable() {
		return toolTable;
	}

	/**
	 * create and add components to the north panel
	 */
	public void addNorthComp() {
		title = new JLabel("Welcome To The Tool Inventory Manager");
		northPanel = new JPanel();
		northPanel.setMaximumSize(new Dimension(650, 100));
		logo = new JLabel();
		northPanel.add(title);
	}

	/**
	 * Create and add add components to the west panel
	 */
	public void addWestComp() {
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
	}

	/**
	 * Create and add components to the east panel
	 */
	public void addEastComp() {
		eastPanel = new JPanel();
	}

	/**
	 * Linking the View with the Controller by assigning a listener to the Quit
	 * button
	 * 
	 * @param listenQuit is the listener assigned to the Quit button
	 */
	public void addQuitListener(ClientController.QuitListener listenQuit) {
		quit.addActionListener(listenQuit);
	}

	/**
	 * Linking the View with the Controller by assigning a listener to the Quantity
	 * button
	 * 
	 * @param listenQuantity is the listener assigned to the Quantity button
	 */
	public void addQuantityListener(ClientController.QuantityListener listenQuantity) {
		quantityCheck.addActionListener(listenQuantity);
	}

	/**
	 * Linking the View with the Controller by assigning a listener to the Reduce
	 * button
	 * 
	 * @param listenReduce is the listener assigned to the Reduce button
	 */
	public void addReduceListener(ClientController.ReduceListener listenReduce) {
		buyItem.addActionListener(listenReduce);
	}

	/**
	 * Linking the View with the Controller by assigning a listener to the Quantity
	 * button
	 * 
	 * @param listenList is the listener assigned to the List button
	 */
	public void addListActionListener(ClientController.ListActionListener listenList) {
		listTool.addActionListener(listenList);
	}

	/**
	 * Linking the View with the Controller by assigning a listener to the Quantity
	 * button
	 * 
	 * @param listenSearch is the listener assigned to the Search button
	 */
	public void addSearchListener(ClientController.SearchListener listenSearch) {
		idSearch.addActionListener(listenSearch);
	}

	/**
	 * Linking the View with the Controller by assigning an Action Listener to a
	 * checkBox
	 * 
	 * @param IDListen listener of idSelect checkbox in Controller
	 */
	public void addIDSelectListener(ClientController.idSearchListener IDListen) {
		idSelect.addActionListener(IDListen);
	}

	/**
	 * Linking the View with the Controller by assigning an Action Listener to a
	 * checkBox
	 * 
	 * @param nameListen listener of nameSelect checkbox in Controller
	 */
	public void addNameSelectListener(ClientController.nameSearchListener nameListen) {
		nameSelect.addActionListener(nameListen);
	}

	/**
	 * Adds panels to the mainFrame and sets the orientation of it
	 */
	public void drawFrame() {
		myFrame = new JFrame("WELCOME");
		myFrame.setSize(650, 500);

		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));

		overallPanel.add(northPanel);
		overallPanel.add(toolScrollPane);
		overallPanel.add(southPanel);

		myFrame.setContentPane(overallPanel);

		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
	}

	/**
	 * Get the user input from the textbox
	 * 
	 * @return the input to the search textbox
	 */
	public String getSearch() {
		return search.getText();
	}

	/**
	 * Get the state of the checkbox selection
	 * 
	 * @return 1 for searchByName selected, 0 for searchByID, -1 otherwise
	 */
	public int getCheckBox() {
		if (idSelect.isSelected()) {
			return 0;
		} else if (nameSelect.isSelected()) {
			return 1;
		} else
			return -1;
	}

	/**
	 * Get for the overall Frame
	 * 
	 * @return the Frame
	 */
	public JFrame getFrame() {
		return myFrame;
	}

	/**
	 * Display error messages to the user
	 * 
	 * @param s the error message to be displayed
	 */
	public void displayErrorMessage(String s) {
		JLabel error = new JLabel(s);
		JOptionPane.showMessageDialog(this, error);
	}

	/**
	 * Gets the tool name from the user using a prompt
	 * 
	 * @return the response from the user to the JOptionPane prompt
	 */
	public String getToolName() {
		return JOptionPane.showInputDialog(this, "Please enter the tool name: ");
	}

	/**
	 * Gets the tool ID from the user using a prompt
	 * 
	 * @return the response from the user to the JOptionPane prompt
	 */
	public String getToolID() {
		return JOptionPane.showInputDialog(this, "Please enter the tool ID: ");
	}

	/**
	 * Used to initialize the scrollable Pane
	 */
	public void initList() {
		selectedTextField = new JTextField();

		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);

		String width = "1234567890123456789012345678901234567890";

		listArea.setPrototypeCellValue(width);
		listArea.setFont(new Font("Courier New", Font.BOLD, 15));
		listArea.setVisibleRowCount(12);
		selectedTextField.setSize(650, 400);
	}

	/**
	 * 
	 * Getter for the listArea
	 * 
	 * @return the JList
	 */
	public JList<String> getListArea() {
		return listArea;
	}

	/**
	 * Getter for the listModel
	 * 
	 * @return list Model
	 */
	public DefaultListModel<String> getListModel() {
		return listModel;
	}

	/**
	 * Getter for the selectedtextField
	 * 
	 * @return selectedTextField
	 */
	public JTextField getSelectedTextField() {
		return selectedTextField;
	}

	/**
	 * Set the state of the nameSelect checkbox
	 * 
	 * @param mark to set the checkbox to
	 */
	public void setNameSelect(boolean mark) {
		nameSelect.setSelected(mark);
	}

	/**
	 * Set the state of the idSelect checkbox
	 * 
	 * @param mark to set the checkbox to
	 */
	public void setIDSelect(boolean mark) {
		idSelect.setSelected(mark);
	}
}
