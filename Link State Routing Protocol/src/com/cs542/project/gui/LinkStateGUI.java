package com.cs542.project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.cs542.project.graph.DijkstraShortestPathAlgorithm;

/*This is the GUI class. 
 * It is the User Interface of the project. 
 * All the design aspects are object oriented. Only one container is used to handle all the other GUI components.
 * Main component will be several time repainted to add and/or remove main GUI components.
 * 
 *  GUI also uses resource folder which contain images and icon.
 * */
public class LinkStateGUI {

	/* Main Container of UI */
	protected static JFrame mainFrame;
	/* Two JPanel to add header and functions in main container. */
	protected static JPanel headerPanel, jFunctionPanel;
	/*
	 * four function menu for changing functionpanel with function menu contents
	 */
	protected static LinkStateUIFunctionMenu1 topology;
	protected static LinkStateUIFunctionMenu2 connectionTable;
	protected static LinkStateUIFunctionMenu3 shortestPath;
	protected static LinkStateUIFunctionMenu4 modifyTopology;

	// Body Content

	/* Four actual Jpanel which will replace JFunctionPanel */
	protected static JPanel bodyFunctionMenu1, bodyFunctionMenu2, bodyFunctionMenu3, bodyFunctionMenu4;

	/* JButton which will be added in functionalMenu */
	protected static JButton btFun1, btFun2, btFun3, btFun4, btFun5, btFun6;

	/* this function will prepare window to display */
	public static void prepareWindow() {

		// Initialize Function Menu
		topology = new LinkStateUIFunctionMenu1();
		connectionTable = new LinkStateUIFunctionMenu2();
		shortestPath = new LinkStateUIFunctionMenu3();
		modifyTopology = new LinkStateUIFunctionMenu4();

		displayFrame();

		// add header Panel
		addHeaderPanel();

		// add button Frame
		addFunctionalPanel();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLocationByPlatform(false);
		mainFrame.show();

	}

	/* This function will add necessary buttons which will execute protocol. */
	private static void addFunctionalPanel() {
		// TODO Auto-generated method stub
		GridLayout gl = new GridLayout(6, 1);
		gl.setVgap(20);
		jFunctionPanel = new JPanel();
		jFunctionPanel.setSize(new Dimension(130, 130));
		jFunctionPanel.setBackground(Color.white);
		jFunctionPanel.setBorder(BorderFactory.createTitledBorder("Functions"));

		JPanel functionalPanel;
		functionalPanel = new JPanel(gl);
		functionalPanel.setOpaque(true);
		functionalPanel.setBackground(Color.white);

		functionalPanel.setSize(new Dimension(20, 200));

		Font font = new Font("Arial", Font.PLAIN, 40);

		// Create Buttons

		ImageIcon ic1 = new ImageIcon("../Link State Routing Protocol/res/network.png");
		btFun1 = new JButton("Create Network Topology", ic1);
		btFun1.setHorizontalTextPosition(SwingConstants.RIGHT);
		btFun1.setFont(font);

		ImageIcon ic2 = new ImageIcon("../Link State Routing Protocol/res/scheme.png");
		btFun2 = new JButton("Connection Table", ic2);
		btFun2.setHorizontalTextPosition(SwingConstants.RIGHT);
		btFun2.setFont(font);

		ImageIcon ic3 = new ImageIcon("../Link State Routing Protocol/res/path.png");
		btFun3 = new JButton("Shortest Path to Router", ic3);
		btFun3.setHorizontalTextPosition(SwingConstants.RIGHT);
		btFun3.setFont(font);

		ImageIcon ic4 = new ImageIcon("../Link State Routing Protocol/res/topology.png");
		btFun4 = new JButton("Modify Topology", ic4);
		btFun4.setHorizontalTextPosition(SwingConstants.RIGHT);
		btFun4.setFont(font);

		ImageIcon ic5 = new ImageIcon("../Link State Routing Protocol/res/router.png");
		btFun5 = new JButton("Broadcast Router", ic5);
		btFun5.setHorizontalTextPosition(SwingConstants.RIGHT);
		btFun5.setFont(font);

		ImageIcon ic6 = new ImageIcon("../Link State Routing Protocol/res/logout.png");
		btFun6 = new JButton("Exit", ic6);
		btFun6.setHorizontalTextPosition(SwingConstants.RIGHT);
		btFun6.setFont(font);

		setEventOnButton();

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;

		functionalPanel.add(btFun1, gc);
		functionalPanel.add(btFun2, gc);
		functionalPanel.add(btFun3, gc);
		functionalPanel.add(btFun4, gc);
		functionalPanel.add(btFun5, gc);
		functionalPanel.add(btFun6, gc);
		functionalPanel.setAutoscrolls(true);
		jFunctionPanel.add(functionalPanel, BorderLayout.CENTER);

		mainFrame.add(jFunctionPanel, BorderLayout.CENTER);

	}

	/*
	 * When a button is clicked events will be executed with respect to button.
	 */
	private static void setEventOnButton() {
		// TODO Auto-generated method stub

		btFun1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				mainFrame.remove(jFunctionPanel);
				bodyFunctionMenu1 = topology.createTopology();
				mainFrame.add(bodyFunctionMenu1, BorderLayout.CENTER);
				mainFrame.revalidate();
				mainFrame.repaint();

			}
		});

		btFun2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (LinkStateUIFunctionMenu1.topology.getG().getVertexs() == null
						|| LinkStateUIFunctionMenu1.topology.getG().getEdges() == null) {
					JLabel errorMessage = new JLabel("Topology is not properly created.");
					errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
					showMessage(errorMessage, "Connection Table", JOptionPane.ERROR_MESSAGE);

				} else {

					mainFrame.remove(jFunctionPanel);
					bodyFunctionMenu2 = connectionTable.getConnectionTable();
					mainFrame.add(bodyFunctionMenu2, BorderLayout.CENTER);
					mainFrame.revalidate();
					mainFrame.repaint();
				}
			}
		});

		btFun3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (LinkStateUIFunctionMenu1.topology.getG().getVertexs() == null
						|| LinkStateUIFunctionMenu1.topology.getG().getEdges() == null) {
					JLabel errorMessage = new JLabel("Topology is not properly created.");
					errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
					showMessage(errorMessage, "Shortest Path", JOptionPane.ERROR_MESSAGE);

				} else {

					mainFrame.remove(jFunctionPanel);
					bodyFunctionMenu3 = shortestPath.getContent();
					mainFrame.add(bodyFunctionMenu3, BorderLayout.CENTER);
					mainFrame.revalidate();
					mainFrame.repaint();
				}
			}
		});

		btFun4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (LinkStateUIFunctionMenu1.topology.getG().getVertexs() == null
						|| LinkStateUIFunctionMenu1.topology.getG().getEdges() == null) {
					JLabel errorMessage = new JLabel("Topology is not properly created.");
					errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
					showMessage(errorMessage, "Modify Topology", JOptionPane.ERROR_MESSAGE);

				} else {

					mainFrame.remove(jFunctionPanel);
					bodyFunctionMenu4 = modifyTopology.getContent();
					mainFrame.add(bodyFunctionMenu4, BorderLayout.CENTER);
					mainFrame.revalidate();
					mainFrame.repaint();
				}
			}
		});

		btFun5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (LinkStateUIFunctionMenu1.topology.getG().getVertexs() == null
						|| LinkStateUIFunctionMenu1.topology.getG().getEdges() == null) {
					JLabel errorMessage = new JLabel("Topology is not properly created.");
					errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
					showMessage(errorMessage, "Broadcast Router", JOptionPane.ERROR_MESSAGE);

				} else {

					try {
						DijkstraShortestPathAlgorithm dij = new DijkstraShortestPathAlgorithm(
								LinkStateUIFunctionMenu1.topology.getG());

						String str = dij.broadCastRouter(LinkStateUIFunctionMenu1.topology.getG());

						if (str == null) {
							// Show Error Dialog Box-- Topology is not properly
							// created.
							JLabel errorMessage = new JLabel("Topology is not properly created.");
							errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
							showMessage(errorMessage, "Broadcast Router", JOptionPane.ERROR_MESSAGE);

						} else {
							JLabel label = new JLabel(str);
							label.setFont(new Font("Arial", Font.BOLD, 26));

							showMessage(label, "Broadcast Router", JOptionPane.INFORMATION_MESSAGE);

						}
					} catch (Exception e1) {

						JLabel errorMessage = new JLabel("Topology is not properly created.");
						errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
						showMessage(errorMessage, "Broadcast Router", JOptionPane.ERROR_MESSAGE);

					}
				}
			}
		});

		btFun6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JLabel label = new JLabel("Exit CS542-04  2016  Fall Project. Good Bye!");
				label.setFont(new Font("Arial", Font.BOLD, 26));
				showMessage(label, "Exit", JOptionPane.INFORMATION_MESSAGE);
				mainFrame.setVisible(false);

			}
		});

	}

	/* This function will add header to the mainFrame. */
	private static void addHeaderPanel() {
		// TODO Auto-generated method stub
		headerPanel = new JPanel();
		headerPanel.setOpaque(true);
		headerPanel.setBackground(Color.white);
		headerPanel.setBorder(BorderFactory.createTitledBorder("Project Title"));

		JLabel headerLabel = new JLabel("Link State Routing Protocol", JLabel.CENTER);
		headerLabel.setFont(new Font("Courier", Font.BOLD, 36));

		// add to jpanel
		headerPanel.add(headerLabel);
		// Add to Frame
		mainFrame.add(headerPanel, BorderLayout.NORTH);
	}

	/* This function will set mainframe properties. */
	public static void displayFrame() {
		mainFrame = new JFrame("Link State Routing Protocol");
		// mainFrame.setSize(1200, 800);
		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();

		mainFrame.setMinimumSize(new Dimension(1200, 800));

		mainFrame.setMaximumSize(DimMax);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		mainFrame.setLayout(new BorderLayout());

		// mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setDefaultLookAndFeelDecorated(true);
		mainFrame.setLocationByPlatform(true);

	}

	/*
	 * This function is used to display messages depending on messageTypes and
	 * Message
	 */
	private static void showMessage(Object message, String title, int messageType) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}

}
