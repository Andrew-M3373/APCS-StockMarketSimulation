import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//import java.

public class RunSimulation {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Test JFrame");
		frame.setSize(1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		
		JLabel words = new JLabel("Test JLabel");
		
		
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		

		JButton button = new JButton("Test Button");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Code accessed");
				words.setText("JLabel has been changed");
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("A Menu");
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem("Just text here.");
		menu.add(menuItem);
		
		
		TextField txtField = new TextField(20);
		txtField.setColumns(0);
		panel.add(txtField);
		
		
		panel.add(words);
		
		frame.setJMenuBar(menuBar);
		
		
		frame.setVisible(true);
		
		
		
		
	}

}
