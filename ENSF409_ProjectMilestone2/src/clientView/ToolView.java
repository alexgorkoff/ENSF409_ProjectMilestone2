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

import clientController.*;

public class ToolView extends JFrame {
	private JFrame myFrame;
	private JPanel southPanel, centrePanel, northPanel, westPanel, eastPanel, checkBoxPanel, searchPanel, buttonPanel,
			searchAndCheckBox;
	private JButton idSearch, quantityCheck, buyItem, quit, listTool;
	private JLabel searchIDLabel, searchNameLabel, title, logo, SearchLabel, searchLogo, listLogo, quantityLogo,
			quitLogo, reduceLogo;
	private JTextField search;
	private JCheckBox idSelect, nameSelect;
	private JScrollPane scrollPanel;
	private DefaultListModel<String> listModel;
	private JList<String> listArea;
	private JTextField selectedTextField;

	/**
	 * The constructor for the main GUI. Creates the main menu and all sub menus
	 * stemming from the main menu
	 */
	public ToolView() {
		addSouthComp();
		addCentreComp();
		addNorthComp();
		addEastComp();
		addWestComp();
		setTitleFont();
		// setBodyFont();
		// setTextFieldSize();
		// setButtonSize();
		drawFrame();

	}

	public void setBodyFont() {
		Font bodyFont = new Font("Serif", Font.PLAIN, 30);
	}

	public void setTextFieldSize() {
		Font textFieldFont = new Font("Serif", Font.PLAIN, 60);
		search.setFont(textFieldFont);
	}

	public void setTitleFont() {
		Font titleFont = new Font("Sans Serif", Font.PLAIN, 30);
		title.setFont(titleFont);
//		quantityCheck.setFont(titleFont);
//		buyItem.setFont(titleFont);
//		listTool.setFont(titleFont);
//		quit.setFont(titleFont);
//		SearchLabel.setFont(titleFont);
//		idSearch.setFont(titleFont);
//		searchNameLabel.setFont(titleFont);
//		searchIDLabel.setFont(titleFont);
	}

	public void setButtonSize() {
		idSearch.setPreferredSize(new Dimension(350, 80));
		quantityCheck.setSize(new Dimension(400, 100));
		buyItem.setSize(new Dimension(200, 100));
		listTool.setSize(new Dimension(200, 100));
		quit.setSize(new Dimension(200, 100));
	}

	public void createSearchPanel() {
		searchPanel = new JPanel();
		search = new JTextField(15);
		searchLogo = new JLabel();
		idSearch = new JButton("Search");
		SearchLabel = new JLabel("Search");
//		try {
//			BufferedImage searchPic = ImageIO.read(new FileInputStream("resources/searchimage.png"));
//			searchLogo = new JLabel(new ImageIcon(searchPic));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		searchPanel.add(searchLogo, BorderLayout.NORTH);
		searchPanel.add(search, BorderLayout.NORTH);
		searchPanel.add(idSearch, BorderLayout.NORTH);
	}

	public void searchAndCheckBox() {
		searchAndCheckBox = new JPanel();
		createCheckBox();
		createSearchPanel();
		searchAndCheckBox.setLayout(new BoxLayout(searchAndCheckBox, BoxLayout.Y_AXIS));
		searchAndCheckBox.add(searchPanel);
		searchAndCheckBox.add(checkBoxPanel);
	}

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

	public void addSouthComp() {
		southPanel = new JPanel();
		searchAndCheckBox();
		createButtonPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.add(searchAndCheckBox);
		southPanel.add(buttonPanel);

	}

	public void createButtonPanel() {
		buttonPanel = new JPanel();
		listTool = new JButton("List All Tools");
		quantityCheck = new JButton("Check Tool Quantity");
		buyItem = new JButton("Reduce Quantity");
		quit = new JButton("Quit Program");

		buttonPanel.setLayout(new FlowLayout());
//		buttonPanel.add(Box.createRigidArea(new Dimension(0, 0)));
		buttonPanel.add(quantityCheck);
//		buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		buttonPanel.add(buyItem);
//		buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		buttonPanel.add(listTool);
//		buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		buttonPanel.add(quit);
	}

	public void addCentreComp() {
		initList();
		centrePanel = new JPanel();
		scrollPanel = new JScrollPane(listArea);
		centrePanel.add(scrollPanel);
	}

	public void addNorthComp() {
		title = new JLabel("Welcome To The Tool Inventory Manager");
		northPanel = new JPanel();
		logo = new JLabel();
//		try {
//			BufferedImage logoPic = ImageIO.read(new FileInputStream("resources/toolimage.png"));
//			logo = new JLabel(new ImageIcon(logoPic));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		northPanel.add(title);
//		northPanel.add(logo);
	}

	public void addWestComp() {
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
//		try {
//			BufferedImage quantityImage = ImageIO.read(new FileInputStream("resources/quantityimage.png"));
//			quantityLogo = new JLabel(new ImageIcon(quantityImage));
//			BufferedImage reduceImage = ImageIO.read(new FileInputStream("resources/reduceimage.png"));
//			reduceLogo = new JLabel(new ImageIcon(reduceImage));
//			BufferedImage listImage = ImageIO.read(new FileInputStream("resources/listimage.png"));
//			listLogo = new JLabel(new ImageIcon(listImage));
//			BufferedImage quitImage = ImageIO.read(new FileInputStream("resources/quitimage.png"));
//			quitLogo = new JLabel(new ImageIcon(quitImage));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		westPanel.add(Box.createRigidArea(new Dimension(0, 100)));
//		westPanel.add(quantityLogo);
//		westPanel.add(Box.createRigidArea(new Dimension(0, 90)));
//		westPanel.add(reduceLogo);
//		westPanel.add(Box.createRigidArea(new Dimension(0, 70)));
//		westPanel.add(listLogo);
//		westPanel.add(Box.createRigidArea(new Dimension(0, 70)));
//		westPanel.add(quitLogo);
	}

	public void addEastComp() {
		eastPanel = new JPanel();
	}

	public void addQuitListener(GUIListener.QuitListener listenQuit) {
		quit.addActionListener(listenQuit);
	}

	public void addQuantityListener(GUIListener.QuantityListener listenQuantity) {
		quantityCheck.addActionListener(listenQuantity);
	}

	public void addReduceListener(GUIListener.ReduceListener listenReduce) {
		buyItem.addActionListener(listenReduce);
	}

	public void addListActionListener(GUIListener.ListActionListener listenList) {
		listTool.addActionListener(listenList);
	}

	public void addSearchListener(GUIListener.SearchListener listenSearch) {
		idSearch.addActionListener(listenSearch);
	}

	public void addListListener(GUIListener.ListListener listListen) {
		listArea.addListSelectionListener(listListen);
	}

	public void addIDSelectListener(GUIListener.idSearchListener IDListen) {
		idSelect.addActionListener(IDListen);
	}

	public void addNameSelectListener(GUIListener.nameSearchListener nameListen) {
		nameSelect.addActionListener(nameListen);
	}

	public void drawFrame() {
		myFrame = new JFrame("WELCOME");
		myFrame.add(southPanel, BorderLayout.SOUTH);
		myFrame.add(centrePanel, BorderLayout.CENTER);
		myFrame.add(northPanel, BorderLayout.NORTH);
		myFrame.add(westPanel, BorderLayout.WEST);
		myFrame.add(eastPanel, BorderLayout.EAST);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(650, 500);
		myFrame.setVisible(true);
	}

	public String getSearch() {
		return search.getText();
	}

	public int getCheckBox() {
		if (idSelect.isSelected()) {
			nameSelect.setSelected(false);
			if (nameSelect.isSelected()) {
				return -1;
			}
			return 0;
		} else if (nameSelect.isSelected()) {
			idSelect.setSelected(false);
			if (idSelect.isSelected()) {
				return -1;
			}
			return 1;
		} else
			return -1;
	}

	public JFrame getFrame() {
		return myFrame;
	}

	public void displayErrorMessage(String s) {
		JLabel error = new JLabel(s);
		// error.setFont(bodyFont);
		JOptionPane.showMessageDialog(this, error);
	}

	public void initList() {
		selectedTextField = new JTextField(40);
		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);
		String width = "1234567890123456789012345678901234567890";
		listArea.setPrototypeCellValue(width);
		listArea.setFont(new Font("Courier New", Font.BOLD, 20));
		listArea.setVisibleRowCount(35);
	}

	public JList<String> getListArea() {
		return listArea;
	}

	public DefaultListModel<String> getListModel() {
		return listModel;
	}

	public JTextField getSelectedTextField() {
		return selectedTextField;
	}

	public void setNameSelect(boolean mark) {
		nameSelect.setSelected(mark);
	}

	public void setIDSelect(boolean mark) {
		idSelect.setSelected(mark);
	}

	public static void main(String[] args) {
		ToolView GUI = new ToolView();
		GUIListener listeners = new GUIListener(GUI);
	}
}
