package clientController;

import java.awt.event.*;

import javax.swing.JOptionPane;

import clientView.*;

public class GUIListener {
	private ToolView myView;

	public GUIListener(ToolView theView) {
		myView = theView;
		myView.addQuitListener(new QuitListener());
		myView.addQuantityListener(new QuantityListener());
		myView.addReduceListener(new ReduceListener());
		myView.addListListener(new ListListener());
		myView.addSearchListener(new SearchListener());

	}

	public class QuantityListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String toolName = JOptionPane.showInputDialog("Enter The Tool Name:");
			// searchDB(toolName);
		}

	}

	public class ReduceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// reduceQuantity();
			JOptionPane.showMessageDialog(null, "Quantity Reduced by 1");
		}

	}

	public class ListListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// getAllTools();
		}

	}

	public class QuitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			myView.getFrame().setVisible(false); // you can't see me!
			myView.getFrame().dispose(); // Destroy the JFrame object
		}
	}

	public class SearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (myView.getCheckBox() == 0) {
				try {
					int toSearch = Integer.parseInt(myView.getSearch());
					System.out.println("YEEE");
				}catch(Exception notNum) {
					myView.displayErrorMessage("Please enter a numeric value for the ID");
				}
				//SearchID(toSearch);
			} else if (myView.getCheckBox() == 1) {
				String toSearch = myView.getSearch();
				if(!toSearch.equals("")) {
					//SearchName(toSearch);
				}
				else {
					myView.displayErrorMessage("Please enter a tool name");
				}
			} else {
				myView.displayErrorMessage("Please select a method to search by");
			}
		}
	}
}
