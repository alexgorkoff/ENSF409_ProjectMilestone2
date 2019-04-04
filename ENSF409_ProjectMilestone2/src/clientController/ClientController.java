package clientController;

import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import clientView.*;
import serverModel.SocketPack;

/**
 * The Controller as part of a MVC architecture that will control the GUI and
 * all of the GUI Listeners to the client
 * 
 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
 * @version 1.2.0
 * @since March 29, 2019
 */
public class ClientController {
	
	/**
	 * View that is part of the GUI Controller
	 */
	private ToolView myView;
	
	/**
	 * Client SocketPack
	 */
	private SocketPack clientSockets;

	/**
	 * Constructor to initialize all of the listeners
	 * 
	 * @param theView View which holds all of the panel componenets
	 */
	public ClientController(SocketPack theSockets) {
		
		myView = new ToolView();
		
		myView.addQuitListener(new QuitListener());
		myView.addQuantityListener(new QuantityListener());
		myView.addReduceListener(new ReduceListener());
		myView.addListActionListener(new ListActionListener());
		myView.addSearchListener(new SearchListener());
		myView.addListListener(new ListListener());
		myView.addIDSelectListener(new idSearchListener());
		myView.addNameSelectListener(new nameSearchListener());
		
		clientSockets = theSockets;
		
	}
	
	public void outputClientGUI(String theOutput) {
		JOptionPane.showMessageDialog(myView, theOutput);
	}
	
	public ToolView getView() {
		return myView;
	}
	public String getToolNameUser() {
		return myView.getToolName();
	}
	public String getToolIDUser() {
		return myView.getToolID();
	}
	public void displayList(String s) {
		
//		String itemInfo[] = s.split("\n");
//		System.out.println("{"+s+"}");
//		for(int i = 0; i < itemInfo.length; i++) {
//			myView.getListModel().addElement(itemInfo[i]);
//			//System.out.println("/"+itemInfo[i]+"/");
//		}
		
		for(String line: s.split("\n")) {
			myView.getListModel().addElement(line);
		}
		
	}
	
	/**
	 * The ListListener class to set text to be selected if clicked on
	 * 
	 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
	 * @version 1.0.1
	 * @since March 29, 2019
	 */
	public class ListListener implements ListSelectionListener {
		/**
		 * Pre-defined method to handle a list item being selected
		 */
		public void valueChanged(ListSelectionEvent e) {
			int index = myView.getListArea().getSelectedIndex();
			if (index >= 0) {
				String line = (String) myView.getListModel().get(index);
				myView.getSelectedTextField().setText(line);
			}
		}
	}

	/**
	 * The ListActionsListener class to respond to List button clicks
	 * 
	 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
	 * @version 1.0.1
	 * @since March 29, 2019
	 */
	public class ListActionListener implements ActionListener {
		/**
		 * Pre-defined method to handle a button being clicked on
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			clientSockets.sendString("1");
		}

	}

	/**
	 * The QuantityListener class to respond to quantity button clicks
	 * 
	 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
	 * @version 1.0.1
	 * @since March 29, 2019
	 */
	public class QuantityListener implements ActionListener {

		/**
		 * Pre-defined method to handle the Quantity button being pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			clientSockets.sendString("4");
		}

	}

	/**
	 * The ReduceListener class to respond to Reduce button clicks
	 * 
	 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
	 * @version 1.0.1
	 * @since March 29, 2019
	 */
	public class ReduceListener implements ActionListener {
		/**
		 * Pre-defined method to handle the reduce button being pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			clientSockets.sendString("5");
		}

	}

	/**
	 * The QuitListener class to respond to Quit button clicks
	 * 
	 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
	 * @version 1.0.1
	 * @since March 29, 2019
	 */
	public class QuitListener implements ActionListener {
		/**
		 * method to handle the quit button being pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			clientSockets.sendString("7");
			myView.getFrame().setVisible(false); // you can't see me!
			myView.getFrame().dispose(); // Destroy the JFrame object
		}
	}

	/**
	 * The idSearchListener class to respond to idSearchCheckBox button clicks
	 * 
	 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
	 * @version 1.0.1
	 * @since March 29, 2019
	 */
	public class idSearchListener implements ActionListener {
		/**
		 * handles the idSelect checkbox being pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			myView.setNameSelect(false);
		}

	}

	/**
	 * The nameSearchListener class to respond to nameSearchCheckBox button clicks
	 * 
	 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
	 * @version 1.0.1
	 * @since March 29, 2019
	 */
	public class nameSearchListener implements ActionListener {
		/**
		 * handles the nameSelect checkbox being pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			myView.setIDSelect(false);
		}

	}

	/**
	 * The SearchListener class to respond to Search button clicks
	 * 
	 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
	 * @version 1.0.1
	 * @since March 29, 2019
	 */
	public class SearchListener implements ActionListener {
		/**
		 * method to handle the search button clicks
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (myView.getCheckBox() == 0) {
				try {
					int toSearch = Integer.parseInt(myView.getSearch());
					clientSockets.sendString("3");
				} catch (Exception notNum) {
					myView.displayErrorMessage("Please enter a numeric value for the ID");
				}
				// SearchID(toSearch);
			} else if (myView.getCheckBox() == 1) {
				String toSearch = myView.getSearch();
				if (!toSearch.equals("")) {
					clientSockets.sendString("2");
					// SearchName(toSearch);
				} else {
					myView.displayErrorMessage("Please enter a tool name");
				}
			} else {
				myView.displayErrorMessage("Please select a method to search by");
			}
		}
	}
}
