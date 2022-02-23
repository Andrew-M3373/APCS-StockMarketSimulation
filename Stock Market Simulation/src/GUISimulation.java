import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
//import org.patriques.input.*;
import org.patriques.input.timeseries.Interval;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.*;
//import org.patriques.output.quote.*;
//import org.patriques.output.exchange.*;
import org.patriques.output.timeseries.*;
import org.patriques.output.timeseries.data.StockData;
import org.patriques.*;
//import java.math.BigDecimal;

import javax.swing.*;
import javax.swing.border.TitledBorder;

//import java.

public class GUISimulation {
	
	private JFrame frame;
	private JPanel contentPane;
	
	private JTextField ticker;
	private JTextField quantity;
	
	private JRadioButton buyAction;
	private JRadioButton sellAction;
	private JRadioButton sellShortAction;
	
	private JRadioButton marketType;
	private JRadioButton limitType;
	private JRadioButton stopType;
	private JRadioButton stopLimitType;
	
	private JRadioButton dayTiming;
	private JRadioButton goodUntilCanceledTiming;
	private JRadioButton extendedHoursTiming;
	

	public static void main(String[] args) {
		
		API();
		
//		GUISimulation gui = new GUISimulation();
//		gui.start();
		
	}
	
	public void start() {
		frame = new JFrame("Stock Market");
		contentPane = new JPanel();
//		frame.setSize(1000, 500);
		makeContent();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void makeMenus() {

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		// Set up menus
		menuBar.add(makeFileMenu());
		
		frame.pack();
		frame.setVisible(true);
		
//		frame.add(contentPanel);
////		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
//		
//
//		
//		
//		
//		
//		
//		TextField txtField = new TextField(20);
//		txtField.setColumns(0);
//		contentPanel.add(txtField);
//		
//		
//		contentPanel.add(selectAccount);
		
		
	}
	
	public JMenu makeFileMenu() {

		JMenu menu = new JMenu("Navigation");
		JMenuItem menuItem = new JMenuItem("View Investments");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					viewInvestments();
				} catch (FileNotFoundException e) {
					System.out.println("Error: File Not Found");
				}
			}
		});
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Trade");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				trade();
			}
		});
		menu.add(menuItem);
		
		return menu;
	}
	
	public void makeContent() {
		contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(new BorderLayout(6, 6));
		contentPane.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		
		
		JLabel txt = new JLabel("Welcome to Andrew's Brokerage Comapny!", JLabel.CENTER);
		contentPane.add(txt, BorderLayout.NORTH);
		contentPane.add(new JSeparator(),  BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder("Where to?"));
		
		ButtonGroup whereTo = new ButtonGroup();
		
		JButton viewInvestmentsButton = new JButton("View Investments");
		viewInvestmentsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					viewInvestments();
				} catch (FileNotFoundException e) {
					System.out.println("Error: File Not Found");
				}
			}
		});
		whereTo.add(viewInvestmentsButton);
		panel.add(viewInvestmentsButton);
		
		JButton tradeButton = new JButton ("Trade Positions");
		tradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				trade();
			}
		});
		whereTo.add(tradeButton);
		panel.add(tradeButton);
		
		contentPane.add(panel, BorderLayout.SOUTH);
	}
	
	public void trade() {
		makeMenus();
		
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
		JLabel txt = new JLabel("Trade Positions");
		header.add(txt);
		header.add(new JSeparator());
		
		JPanel largePanel = new JPanel();
		largePanel.setLayout(new BoxLayout(largePanel, BoxLayout.Y_AXIS));
		largePanel.setBorder(BorderFactory.createEmptyBorder());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder());
		
		JPanel smallPanel = new JPanel();
		smallPanel.setLayout(new BoxLayout(smallPanel, BoxLayout.Y_AXIS));
		smallPanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		smallPanel.add(new JLabel("Ticker"));
		smallPanel.add(new JLabel("Share Quantity"));
		panel.add(smallPanel);
		
		smallPanel = new JPanel();
		smallPanel.setLayout(new BoxLayout(smallPanel, BoxLayout.Y_AXIS));
		smallPanel.setBorder(BorderFactory.createEmptyBorder(3,6,3,3));
		ticker = new JTextField("");
//		ticker.setMinimumSize(new Dimension(300,300));
		quantity = new JTextField("");
//		quantity.setMinimumSize(new Dimension(300,300));
		smallPanel.add(ticker);
		smallPanel.add(quantity);
		panel.add(smallPanel);
		largePanel.add(panel);
		
		smallPanel = new JPanel();
		smallPanel.setLayout(new BoxLayout(smallPanel,BoxLayout.X_AXIS));
		smallPanel.setBorder(BorderFactory.createTitledBorder("Action"));
		ButtonGroup actionButtonGroup = new ButtonGroup();
		buyAction = new JRadioButton("Buy",true);
		sellAction = new JRadioButton("Sell", false);
		sellShortAction = new JRadioButton("Sell Short", false);
		smallPanel.add(buyAction);
		smallPanel.add(sellAction);
		smallPanel.add(sellShortAction);
		actionButtonGroup.add(buyAction);
		actionButtonGroup.add(sellAction);
		actionButtonGroup.add(sellShortAction);
		largePanel.add(smallPanel);
		
		smallPanel = new JPanel();
		smallPanel.setLayout(new BoxLayout(smallPanel,BoxLayout.X_AXIS));
		smallPanel.setBorder(BorderFactory.createTitledBorder("Order Type"));
		ButtonGroup orderTypeButtonGroup = new ButtonGroup();
		marketType = new JRadioButton("Market",true);
		limitType = new JRadioButton("Limit", false);
		stopType = new JRadioButton("Stop", false);
		stopLimitType = new JRadioButton("Stop Limit", false);
		smallPanel.add(marketType);
		smallPanel.add(limitType);
		smallPanel.add(stopType);
		smallPanel.add(stopLimitType);
		orderTypeButtonGroup.add(marketType);
		orderTypeButtonGroup.add(limitType);
		orderTypeButtonGroup.add(stopType);
		orderTypeButtonGroup.add(stopLimitType);
		largePanel.add(smallPanel);
		
		smallPanel = new JPanel();
		smallPanel.setLayout(new BoxLayout(smallPanel,BoxLayout.X_AXIS));
		smallPanel.setBorder(BorderFactory.createTitledBorder("Timing"));
		ButtonGroup timingButtonGroup = new ButtonGroup();
		dayTiming = new JRadioButton("Day",true);
		goodUntilCanceledTiming = new JRadioButton("Good Until Canceled", false);
		extendedHoursTiming = new JRadioButton("Extended Hours", false);
		smallPanel.add(dayTiming);
		smallPanel.add(goodUntilCanceledTiming);
		smallPanel.add(extendedHoursTiming);
		timingButtonGroup.add(dayTiming);
		timingButtonGroup.add(goodUntilCanceledTiming);
		timingButtonGroup.add(extendedHoursTiming);
		largePanel.add(smallPanel);
		
		JButton placeOrderButton = new JButton("Place Order");
		placeOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to place this order?" +
						"\n     Ticker: " + ticker.getText() +
						"\n     Quantity: " + quantity.getText() + 
						"\n     Action: " + (buyAction.isSelected() ? "Buy" : (sellAction.isSelected() ? "Sell" : "Sell Short")) +
						"\n     Order Type: " + (marketType.isSelected() ? "Market" : (limitType.isSelected() ? "Limit" : (stopType.isSelected() ? "Stop" : "Stop Limit"))) +
						"\n     Timing: " + (dayTiming.isSelected() ? "Day" : (goodUntilCanceledTiming.isSelected() ? "Good Until Canceled" : "Extended Hours"))) == 0) {
					try {
						placeOrder();
					} catch (IOException e) {
						System.out.println("File Not Found");
					}
				}
			}
		});
		
		
		contentPane.removeAll();
		contentPane.add(header, BorderLayout.NORTH);
		contentPane.add(largePanel, BorderLayout.WEST);
		contentPane.add(placeOrderButton, BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	public void placeOrder() throws IOException {
		String order = "";
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		order += ticker.getText() +
				"  " + quantity.getText() + 
				"  " + (buyAction.isSelected() ? 1 : (sellAction.isSelected() ? 2 : 3)) +
				"  " + (marketType.isSelected() ? 1 : (limitType.isSelected() ? 2 : (stopType.isSelected() ? 3 : 4))) +
				"  " + (dayTiming.isSelected() ? 1 : (goodUntilCanceledTiming.isSelected() ? 2 : 3)) +
				"  " + dtf.format(LocalDateTime.now()) +
				"\n";
		
		try {
			Files.write(Paths.get("Transaction History.txt"), order.getBytes(), StandardOpenOption.APPEND);
		} catch (FileNotFoundException e) {
			System.out.println("Error: File Not Found");
		}
		
		JOptionPane.showMessageDialog(frame, "Your order has been placed!");
	}
	
	public void viewInvestments() throws FileNotFoundException {
		makeMenus();
		
		ArrayList<String[]> investments = new ArrayList<String[]>();
		
		// Fill ArrayList
		Scanner input = new Scanner(new File("Transaction History.txt"));
		input.nextLine();
		String[] line = input.nextLine().split("  ");
		investments.add(line);
		while (input.hasNextLine()) {
			line = input.nextLine().split("  ");
			int i = 0;
			boolean listed = false;
			do {
				if (investments.get(i)[0].equals(line[0])) {
					investments.get(i)[1] = Integer.toString(Integer.parseInt(investments.get(i)[1]) + (line[2].equals("1") ? 1 : -1) * Integer.parseInt(line[1]));
					listed = true;
				}
				i++;
			} while (i < investments.size());
			if (!listed) investments.add(line);
		}
		
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Investments", TitledBorder.CENTER, TitledBorder.TOP));
		String[][] investmentsTable = new String[investments.size()][4];
		String[] header = {"Ticker", "Quantity", "Price", "Market Value"};
		
		for (int i = 0; i < investments.size(); i++) {
			investmentsTable[i][0] = investments.get(i)[0];
			investmentsTable[i][1] = investments.get(i)[1];
		}
		
		JTable table = new JTable(investmentsTable, header) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400,290));
		panel.add(scrollPane);
		
		
		contentPane.removeAll();
		contentPane.add(panel, BorderLayout.CENTER);
		frame.setSize(450,400);
		frame.setLocationRelativeTo(null);
		
	}
	
	public static void API() {
		String apiKey = "Z5IN54GEOX5Y96SX";
		int timeout = 3000;
		AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
		TimeSeries stockTimeSeries = new TimeSeries(apiConnector);
	    
	    try {
	      IntraDay response = stockTimeSeries.intraDay("MSFT", Interval.ONE_MIN, OutputSize.COMPACT);
	      Map<String, String> metaData = response.getMetaData();
	      System.out.println("Information: " + metaData.get("1. Information"));
	      System.out.println("Stock: " + metaData.get("2. Symbol"));
	      
	      List<StockData> stockData = response.getStockData();
	      stockData.forEach(stock -> {
	        System.out.println("date:   " + stock.getDateTime());
	        System.out.println("open:   " + stock.getOpen());
	        System.out.println("high:   " + stock.getHigh());
	        System.out.println("low:    " + stock.getLow());
	        System.out.println("close:  " + stock.getClose());
	        System.out.println("volume: " + stock.getVolume());
	      });
	    } catch (AlphaVantageException e) {
	      System.out.println("something went wrong");
	    }
	}
	

}
