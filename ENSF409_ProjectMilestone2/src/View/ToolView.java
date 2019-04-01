package View;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.*;
import Controller.*;

public class ToolView extends JFrame {
	private JFrame myFrame;
	private JPanel southPanel, centrePanel, northPanel, westPanel, eastPanel, checkBoxPanel, searchPanel;
	private JButton idSearch, quantityCheck, buyItem, quit, listTool;
	private JLabel searchIDLabel, searchNameLabel, title, logo, SearchLabel, searchLogo, listLogo, quantityLogo,
			quitLogo, reduceLogo;
	private JTextField search;
	private Font bodyFont;
	private JCheckBox idSelect;
	private JCheckBox nameSelect;

	public ToolView() {
		myFrame.setDefaultLookAndFeelDecorated(true);
		bodyFont = new Font("Serif", Font.PLAIN, 30);
		idSearch = new JButton("Search");
		quantityCheck = new JButton("Check Tool Quantity");
		buyItem = new JButton("Reduce Quantity");
		quit = new JButton("Quit Program");
		listTool = new JButton("List All Tools");
		idSelect = new JCheckBox();
		nameSelect = new JCheckBox();
		title = new JLabel("Welcome To The Tool Inventory Manager");
		SearchLabel = new JLabel("Search");
		searchNameLabel = new JLabel(" Search by Name ");
		searchIDLabel = new JLabel("Search by ID ");
		logo = new JLabel();
		searchLogo = new JLabel();
		setTitleFont();
		search = new JTextField(15);
		southPanel = new JPanel();
		searchPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.X_AXIS));
		centrePanel = new JPanel();
		centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS));
		northPanel = new JPanel();
		eastPanel = new JPanel();
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		myFrame = new JFrame();
		setTextFieldSize();
		setBodyFont();
		setButtonSize();
		createSearchPanel();
		createCheckBox();
		addSouthComp();
		addCentreComp();
		addNorthComp();
		addEastComp();
		addWestComp();
		drawFrame();
	}

	public void setBodyFont() {

	}

	public void setTextFieldSize() {
		Font textFieldFont = new Font("Serif", Font.PLAIN, 60);
		search.setFont(textFieldFont);
	}

	public void setTitleFont() {
		Font titleFont = new Font("Serif", Font.PLAIN, 60);
		title.setFont(titleFont);
		quantityCheck.setFont(titleFont);
		buyItem.setFont(titleFont);
		listTool.setFont(titleFont);
		quit.setFont(titleFont);
		SearchLabel.setFont(titleFont);
		idSearch.setFont(titleFont);
		searchNameLabel.setFont(titleFont);
		searchIDLabel.setFont(titleFont);
	}

	public void setButtonSize() {
		idSearch.setPreferredSize(new Dimension(350, 80));
		quantityCheck.setSize(new Dimension(400, 100));
		buyItem.setSize(new Dimension(200, 100));
		listTool.setSize(new Dimension(200, 100));
		quit.setSize(new Dimension(200, 100));
	}

	public void createSearchPanel() {
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

	public void createCheckBox() {
		checkBoxPanel.add(searchIDLabel);
		checkBoxPanel.add(idSelect);
		checkBoxPanel.add(searchNameLabel);
		checkBoxPanel.add(nameSelect);
	}

	public void addSouthComp() {
		southPanel.add(searchPanel);
		southPanel.add(checkBoxPanel, BorderLayout.SOUTH);
	}

	public void addCentreComp() {
		centrePanel.add(Box.createRigidArea(new Dimension(0, 100)));
		centrePanel.add(quantityCheck);
		centrePanel.add(Box.createRigidArea(new Dimension(0, 100)));
		centrePanel.add(buyItem);
		centrePanel.add(Box.createRigidArea(new Dimension(0, 100)));
		centrePanel.add(listTool);
		centrePanel.add(Box.createRigidArea(new Dimension(0, 100)));
		centrePanel.add(quit);
		

	}

	public void addNorthComp() {
		try {
			BufferedImage logoPic = ImageIO.read(new FileInputStream("resources/toolimage.png"));
			logo = new JLabel(new ImageIcon(logoPic));
		} catch (IOException e) {
			e.printStackTrace();
		}
		northPanel.add(title);
		northPanel.add(logo);
	}

	public void addWestComp() {		
		try {
			BufferedImage quantityImage = ImageIO.read(new FileInputStream("resources/quantityimage.png"));
			quantityLogo = new JLabel(new ImageIcon(quantityImage));
			BufferedImage reduceImage = ImageIO.read(new FileInputStream("resources/reduceimage.png"));
			reduceLogo = new JLabel(new ImageIcon(reduceImage));
			BufferedImage listImage = ImageIO.read(new FileInputStream("resources/listimage.png"));
			listLogo = new JLabel(new ImageIcon(listImage));
			BufferedImage quitImage = ImageIO.read(new FileInputStream("resources/quitimage.png"));
			quitLogo = new JLabel(new ImageIcon(quitImage));
		} catch (IOException e) {
			e.printStackTrace();
		}
		westPanel.add(Box.createRigidArea(new Dimension(0, 100)));
		westPanel.add(quantityLogo);
		westPanel.add(Box.createRigidArea(new Dimension(0, 90)));
		westPanel.add(reduceLogo);
		westPanel.add(Box.createRigidArea(new Dimension(0, 70)));
		westPanel.add(listLogo);
		westPanel.add(Box.createRigidArea(new Dimension(0, 70)));
		westPanel.add(quitLogo);
	}

	public void addEastComp() {

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
	public void addListListener(GUIListener.ListListener listenList) {
		listTool.addActionListener(listenList);
	}
	public void addSearchListener(GUIListener.SearchListener listenSearch) {
		idSearch.addActionListener(listenSearch);
	}

	public void drawFrame() {
		myFrame.add(southPanel, BorderLayout.SOUTH);
		myFrame.add(centrePanel, BorderLayout.CENTER);
		myFrame.add(northPanel, BorderLayout.NORTH);
		myFrame.add(westPanel, BorderLayout.WEST);
		myFrame.add(eastPanel, BorderLayout.EAST);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(2000, 1500);
		myFrame.setVisible(true);
	}

	public int getSearch() {
		return Integer.parseInt(search.getText());
	}

	public static void main(String[] args) {
		ToolView GUI = new ToolView();
		GUIListener listeners = new GUIListener(GUI);
	}

	public JFrame getFrame() {
		return myFrame;
	}

}
